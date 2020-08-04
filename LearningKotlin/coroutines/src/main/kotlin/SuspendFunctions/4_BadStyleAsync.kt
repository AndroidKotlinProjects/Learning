package SuspendFunctions

import kotlinx.coroutines.*
import kotlin.system.*

// WARNING: THIS IS BAD STYLE, DO NOT DO
/* e.g. what if an error occurred between the Async() calls and the await() calls and the program throws an exception
* and so leaves this function. Both the async function are still running in the background when they don't need to be,
* what if they are super intensive? what if they continually use up memory etc. This is not structured concurrency. */

// note that we don't have `runBlocking` to the right of `main` in this example, MAIN IS NORMAL CODE, NOT A CORO
fun main() {
    val time = measureTimeMillis {
        // we can initiate async actions outside of a coroutine
        val one = somethingUsefulOneAsync() // calling the Aync() style functions from normal non-coro code
        val two = somethingUsefulTwoAsync()
        // but waiting for a result must involve either suspending or blocking.
        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
        runBlocking {
            println("The answer is ${one.await() + two.await()}") // can only  call await() in a coro or suspend func
        }
    }
    println("Completed in $time ms")
}

// notice this is not a suspend fun, it can be called from outside of a coroutine
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

// notice this is not a suspend fun, it can be called from outside of a coroutine
fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

private suspend fun doSomethingUsefulOne(): Int {
    println("doing something useful 1")
    delay(1000L)
    return 13
}

private suspend fun doSomethingUsefulTwo(): Int {
    println("doing something useful 2")
    delay(1000L)
    return 29
}