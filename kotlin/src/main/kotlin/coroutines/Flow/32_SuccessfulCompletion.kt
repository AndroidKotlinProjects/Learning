package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private fun simple(): Flow<Int> = (1..3).asFlow()

fun main() = runBlocking<Unit> {
    simple()
        .onCompletion { cause -> println("Flow completed with $cause") }
        .collect { value ->
            check(value <= 1) { "Collected $value" }
            println(value)
        }
}

/* This showcases how the onCompletion operator is different to the catch operator as it sees all exceptions and
* receives a null exception only on successful completion of the upstream flow (where successful completion means
* without cancellation or exception - remember that cancellation throws a coro cancellation exception). */

/* The important point here is that the onCompletion operator sees the java.lang.IllegalStateException even though
* it is downstream from it */