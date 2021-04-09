package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.*

private fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(400) // pretend we are asynchronously waiting 400 ms
        emit(i) // emit next value
    }
}

fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        simple().collect { value ->
            delay(600) // pretend we are processing it for 600 ms
            println(value)
        }
    }
    println("Collected in $time ms")
    val time2 = measureTimeMillis {
        simple()
            .buffer() // buffer emissions, don't wait
            .collect { value ->
                delay(600) // pretend we are processing it for 600 ms
                println(value)
            }
    }
    println("Collected in $time2 ms")
}

/* In the first collection we don't buffer and so it takes 3*(400+600)ms roughly i.e. 3 seconds. In the second
* collection we do buffer, which means the emissions are now happening concurrently with the collecting. Now because it
* only takes 400ms to emit but 600ms to process, we aren't done processing the first emission when the second arrives.
* It is therefore buffered into a pipeline, hence the name buffer. It therefore takes 400+3*600 roughly i.e. 2.2 secs */

/* Note that the flowOn operator uses the same buffering mechanism internally when it has to change a
* CoroutineDispatcher, but here we explicitly request buffering without changing the execution context. It doesn't
* matter in this case that it is running on the same context because the emissions take less time (400) than the
* processing (600). */