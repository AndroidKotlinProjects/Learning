package SuspendFunctions

import kotlinx.coroutines.*
import kotlin.system.*

/* When you set the async builders start parameter to lazy, the coro will only be started when either await() or start()
* is called on it. Notice that when you call await(), the coro is started and the main code blocks at the await() call
* until the result comes back i.e. it awaits the result. This leads to sequential code. On the other hand, start()ing
* the two coro's doesn't cause main to wait on them, instead main proceeds to the print statement with the await() calls
* in it and it waits there (but at this point both coros are started and runnning in the background so we get
* concurrency) */
fun main() = runBlocking<Unit> {
    val time1 = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // some computation
        one.start() // start the first one
        two.start() // start the second one
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time1 ms")

    val time2 = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time2 ms")
}

private suspend fun doSomethingUsefulOne(): Int {
    println("doing something useful 1")
    delay(1000L) // pretend we are doing something useful here
    return 13
}

private suspend fun doSomethingUsefulTwo(): Int {
    println("doing something useful 2")
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}