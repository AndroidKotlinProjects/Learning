package SuspendFunctions

import kotlinx.coroutines.*
import kotlin.system.*

/* In practice we do this if we use the result of the first function to make a decision on whether we need to invoke
the second one or to decide on how to invoke it. */

/* We have made main itself a coroutine via the runBlocking coroutine builder. Now, the code in a coroutine is run
* sequentially by default, just like regular code. This means doSomethingUsefulOne will run and complete before Two
* runs. It also means doSomethingUsefulTwo will run and complete before the print statement completes. This is what we
* wanted here - sequential logic inside a coroutine i.e. code that can be ran asynchronously (e.g. if we launched it
* in another coroutine so it could run in the background) but whose logic is written synchronously (normal). */
fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
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

/* In practice, we have one function run and complete before another function if we use the result of the first
* function to make a decision on whether we need to invoke the second one or to decide on how to invoke it. Otherwise
* it would just be better to run both in parallel */