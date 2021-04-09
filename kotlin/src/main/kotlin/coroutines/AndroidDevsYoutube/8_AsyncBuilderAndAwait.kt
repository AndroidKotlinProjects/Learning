package AndroidDevsYoutube

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {

    /* We've already seen that by launching multiple coroutines you can get parallelism. However, you still have to
    * manually join the jobs and do a bunch of variable juggling. */
    GlobalScope.launch(Dispatchers.IO) {
        var res1: String? = null
        var res2: String? = null
        var time = measureTimeMillis {
            val job1 = launch { res1 = networkRequest1() }
            val job2 = launch { res2 = networkRequest2() }
            job1.join()
            job2.join()
            println("received responses: {$res1, $res2}")
        }
        println("Requests took $time ms in total.")
    }

    /* Here we can get the same benefits as above (parallel requests), except with lesser and simpler code. We use
    * the async coroutine builder which returns a Deferred<T> (which is a job so it can be cancelled). This deferred
    * is basically a promise that it will return a value in the future. To wait on this value, use Deferred<T>.await().
    * Note that using await straight away, i.e. at (1) and (2) below, will make them run sequential instead of
    * concurrent. I believe this is because when you await(), you are stopping and waiting for the value to be there.*/
    GlobalScope.launch {
        var time = measureTimeMillis {
            val res1 = async { networkRequest1() } //.await() (1), this will make it run concurrent
            val res2 = async { networkRequest2() } //.await() (2)
            println("received responses: {${res1?.await()}, ${res2?.await()}}") // we stop and wait here!!
        }
        println("Requests took $time ms in total.")
    }

    readLine()
}