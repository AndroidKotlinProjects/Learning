package SuspendFunctions

import kotlinx.coroutines.*
import kotlin.system.*

/* This time, because there is no dependency between the two functions, we can run them in parallel. To do this, we
* use the async coroutine builder. These return a deferred, which is a subclass of job that also holds a value. The
* deferred can be await()ed on to get its value when it is ready. It can also be cancelled as it is a job. Remember,
* coroutines are sequential (within themselves) by default, concurrency will always be explicit. The reason i say
* "within themselves" is because if you launch 2 coro's then they will obvs run in parallel, but the code within
* those individual coro's will run sequential by default. */
fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
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