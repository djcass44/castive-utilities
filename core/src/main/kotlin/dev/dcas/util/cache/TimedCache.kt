
/*
 *  Copyright 2019 Django Cass
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package dev.dcas.util.cache

import dev.castive.log2.logd
import dev.castive.log2.logi
import dev.castive.log2.logv
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.timer

/**
 * @param ageLimit: the number of ticks before an item is removed
 * @param listener: listener fired when an event is removed
 * @param tickDelay: number of milliseconds between each tick
 * @param allowReset: if an item is accessed, its age is reset to 0. Disable this if items MUST age off even if they're regularly accessed
 */
class TimedCache<K, V>(
	private val ageLimit: Int = 30,
	private val listener: TimedCacheListener<K, V>? = null,
	tickDelay: Long = 10_000L,
	private val allowReset: Boolean = true
) {
	interface TimedCacheListener<K, V> {
		suspend fun onAgeLimitReached(key: K, value: V)
	}

	private val cache = ConcurrentHashMap<K, Pair<Int, V>>()

	operator fun get(key: K): V? {
		val item = cache[key]
		// reset the clock when accessed
		if(item != null && allowReset)
			set(key, item.second)
		return item?.second
	}

	operator fun set(key: K, value: V) {
		cache[key] = 0 to value
	}

	/**
	 * Gets the current size of the cache
	 */
	fun size(): Int = cache.size

	init {
		// start the task
		"Starting timer: ${TimedCache::class.java.name} @ ${System.currentTimeMillis()}".logi(TimedCache::class.java)
		timer(TimedCache::class.java.name, true, 0, tickDelay) {
			GlobalScope.launch {
				onTick()
			}
		}
		Runtime.getRuntime().addShutdownHook(Thread {
			runBlocking {
				// tell the cache to move all items to disc
				onClose()
			}
		})
	}

	/**
	 * Increment the cache age by 1
	 * Items which are considered 'too old' are removed
	 */
	private suspend fun onTick() = withContext(Dispatchers.Default) {
		"Tick for ${TimedCache::class.java.name} @ ${System.currentTimeMillis()}".logd(TimedCache::class.java)
		val staleItems = arrayListOf<K>()
		cache.forEach { (t: K, u: Pair<Int, V>) ->
			cache[t] = (u.first + 1) to u.second
			// check the age
			if(u.first >= ageLimit) {
				// tell the listener that the item is too old
				launch {
					listener?.onAgeLimitReached(t, u.second)
				}
				staleItems.add(t) // track old items for later so we don't run into concurrency issues
			}
		}
		"Scanned ${cache.size} items, found ${staleItems.size} stale item(s)".logv(TimedCache::class.java)
		// remove the old entries
		staleItems.forEach {
			cache.remove(it)
		}
		if(staleItems.size > 0)
			"Removed ${staleItems.size} items".logi(TimedCache::class.java)
	}

	private suspend fun onClose() = withContext(Dispatchers.Default) {
		"Purging TimedCache, this may take a moment".logi(TimedCache::class.java)
		"Purging ${cache.size} items [reason: SHUTDOWN]".logv(TimedCache::class.java)
		cache.forEach { (t: K, u: Pair<Int, V>) ->
			listener?.onAgeLimitReached(t, u.second)
		}
	}
}