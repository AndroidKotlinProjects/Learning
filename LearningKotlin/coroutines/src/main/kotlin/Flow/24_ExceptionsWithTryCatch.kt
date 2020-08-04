package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i) // emit next value
    }
}

fun main() = runBlocking<Unit> {
    try {
        simple().collect { value ->
            println(value)
            check(value <= 1) { "Collected $value" }
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}

/* Flow collection can complete with an exception when an emitter or code inside the operators throw an exception. This
* example successfully catches an exception in collect terminal operator, after which no more emissions are made. This
* sort of try catch will catch everything i.e. an exception in both emitter or operator. */