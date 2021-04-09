package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private fun simple(): Flow<Int> = flow {
    emit(1)
    throw RuntimeException()
}

fun main() = runBlocking<Unit> {
    simple()
        .onCompletion { cause -> if (cause != null) println("Flow completed exceptionally") }
        .catch { cause -> println("Caught exception") }
        .collect { value -> println(value) }
}

/* The onCompletion lambda has a nullable parameter that is null in the case of no exception, and holds the exception
* object in the case of an exception. Hence checking if it is null or not lets you know if the flow completed normally
* or exceptionally. */

/* The onCompletion operator, unlike catch, does not handle the exception. As we can see from the above example code,
* the exception still flows downstream. It will be delivered to further onCompletion operators and can be handled with
* a catch operator. */