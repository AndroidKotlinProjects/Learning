package basics

import kotlinx.coroutines.*
import kotlin.random.Random

fun main() {
    var helloTime: Long = 0
    var worldTime: Long = 0
    var waitTime = Random.nextLong(1000,2000)
    println("Greeting should take around ${waitTime} ms")

    /* CoroutineScope's have a Context which has a Job. When a coro is launched it returns a reference to its job.
     * You can use this job for things like cancelling or joining. */
    val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
        delay(waitTime)
        println("World")
        worldTime = System.currentTimeMillis()
    }
    println("Hello")
    helloTime = System.currentTimeMillis()
    /* job.join() is a suspend function, so we must call it from a coroutine, so we use the runBlocking builder */
    runBlocking {
        job.join() // wait until child coroutine completes (coro is child of job)
        println("greeting took ${worldTime - helloTime}")
    }
}

/* This is a much nicer way of waiting for our GlobalScope coroutine to finish as it is not a hard coded coupled
 * approach. As you can see, the job.join() allows us to sync back up with the GS coro regardless of how long it
 * takes to execute. Much better!!! */