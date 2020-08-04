package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun numbers(): Flow<Int> = flow {
    try {
        emit(1)
        emit(2)
        println("This line will not execute")
        emit(3)
    } finally {
        println("Finally in numbers")
    }
}

fun main() = runBlocking<Unit> {
    numbers()
        .take(2) // take only the first two emissions from the flow
        .collect { value -> println(value) } // collect the flow! (but only the first two)
}

/* Again, intermediate operators can be thought of as being tacked onto the end of the flow definition. You could
* imagine the take operator is adding something to the flow definition that tracks the number of emissions and cancels
* itself when it hits the specified limit.
*
* Cancellation in coroutines is always performed by throwing an exception, so that all the resource-management
* functions (like try { ... } finally { ... } blocks) operate normally in case of cancellation. This is reflected above
* in that the finally block runs when the flow (coro) is cancelled. */