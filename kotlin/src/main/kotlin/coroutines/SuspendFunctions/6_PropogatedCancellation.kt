package SuspendFunctions

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    try {
        failedConcurrentSum()
    } catch(e: ArithmeticException) { // cancellation propagates to here (the outer coro defined below)
        println("Outer coro cancelled by propagation: Computation failed with ArithmeticException")
    }
}

/* We use the outer coro scope again as a way of making all coro's fail if one fails. */
suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE) // Emulates very long computation, this is just to show this is cancelled by propagation
            42
        } finally {
            println("Other async coro cancelled by propagation: aync one inner coro was cancelled during computation")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException() // exception thrown, the other async coro will be cancelled, then the outer coro cancelled as well
    }
    one.await() + two.await()
}