package AndroidDevsYoutube

import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch(Dispatchers.IO) {
        delay(2000L)
        println("1: thread ${Thread.currentThread().name}")
    }
    GlobalScope.launch(Dispatchers.IO) {
        delay(2000L)
        println("2: thread ${Thread.currentThread().name}")
    }
    readLine()
}

/* 1: thread DefaultDispatcher-worker-1
   2: thread DefaultDispatcher-worker-2
 * as you can see, the coroutines run on different threads even though they are launched with the same context,
 * Dispatchers.IO. It would seem that dispatchers are not threads themselves, but instead groupings of threads
  which fall under that responsibilty. Therefore launching multiple coroutines under the same dispatcher doesn't
   meant they can't run parallel! */