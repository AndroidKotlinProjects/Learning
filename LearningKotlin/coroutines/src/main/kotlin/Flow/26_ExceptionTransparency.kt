package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private fun simple(): Flow<String> =
    flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i) // emit next value
        }
    }
        .map { value ->
            check(value <= 1) { "Crashed on $value" }
            "string $value"
        }

fun main() = runBlocking<Unit> {
    simple()
        .catch { e -> emit("Caught $e") } // emit on exception
        .collect { value -> println("collected: $value") }
}

/* Flows must be transparent to exceptions and it is a violation of the exception transparency to emit values in the
* flow { ... } builder from inside of a try/catch block. The output of the example is the same, even though we do not
* have try/catch around the code anymore. Also notice that the exception is emitted and collected. */