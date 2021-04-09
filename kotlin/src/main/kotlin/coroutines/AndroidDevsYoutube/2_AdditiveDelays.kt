package AndroidDevsYoutube

import kotlinx.coroutines.*

fun main() {
    var startTime = System.currentTimeMillis()
    var endTime: Long = 0L
    val job = GlobalScope.launch {
        /* This is our coroutine!!! */
        val r1 = networkRequest1() // coroutine starts first network request and waits for its result
        val r2 = networkRequest2() // first request done, now do the same for request 2
        endTime = System.currentTimeMillis()
        println("The results were: {$r1, $r2}, and took ${endTime-startTime} to get.")
    }
    runBlocking { job.join() }

    /* The results take around 6000ms to get. This is because both network requests are running on the same coroutine.
     * This means that the second call cannot start and be working in the background whilst the first one is. Instead,
     * they must run one after another. */

    startTime = System.currentTimeMillis()
    val job1 = GlobalScope.launch {
        networkRequest1()
    }
    val job2 = GlobalScope.launch {
        networkRequest2()
    }
    runBlocking {
        job1.join()
        job2.join()
        endTime = System.currentTimeMillis()
        println("The results took ${endTime-startTime} to get.")
    }

    /* This time they only take roughly 3000ms to get because they are running on separate coroutines, and therefore
    * can run in parallel */
}

suspend fun networkRequest1(): String {
    delay(3000L)
    return "result1"
}

suspend fun networkRequest2(): String {
    delay(3000L)
    return "result2"
}

