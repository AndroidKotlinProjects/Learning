package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception

fun foo(): Flow<Int> = flow {
    for (i in 1..5) {
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    /* cancellable automatically because it uses the flow builder which checks for isActive when doing each emission */
    try {
        foo().collect { value ->
            if (value == 3) cancel()
            println(value)
        }
    } catch (e: Exception) {
        println("catching $e so that execution can continue")
    }

    /* not cancellable automatically. Most flow builders aren't for performance reasons. */
    (1..5).asFlow().collect { value ->
        if (value == 3) cancel() // wont stop at 3, will continue all the way through to 5 and then realise cancelled
        println(value)
    }

}

/* For convenience, the flow {} builder performs additional ensureActive checks for cancellation on each emitted value.
* It means that a busy loop emitting from a flow { ... } is cancellable. Note that other flow builders do not do this. */