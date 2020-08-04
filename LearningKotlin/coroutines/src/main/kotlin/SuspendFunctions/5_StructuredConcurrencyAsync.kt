package SuspendFunctions

import kotlinx.coroutines.*
import kotlin.system.*

fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}

/* We use the coroutineScope coroutine builder to make an outer coro that holds the two inner coro's made with the
* aync coro builders. This way, if any exception is thrown inside the outer scope (say one of the aync coro's fail or
* a logic error between the ayncs's and awaits occurs), then all coro's inside the outer scope are cancelled.
* One fails, all fail. */
suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
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