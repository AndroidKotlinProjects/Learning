package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        println("simple: Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    withTimeoutOrNull(250) { // Timeout after 250ms, meaning we won't get the third value
        simple().collect { value -> println("main: got $value") }
    }
    println("Done")
}

/* flow collection can be cancelled when the flow is suspended in a cancellable suspending function (like delay).*/