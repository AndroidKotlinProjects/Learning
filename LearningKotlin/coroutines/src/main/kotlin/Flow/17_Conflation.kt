package Flow

/* Conflation is basically a queue. It is similar to buffer() except you only take the most recent value. For example,
* operation status updates, it may not be necessary to process each value, but instead, only most recent ones. The
* conflate operator will skip intermediate values if newer ones are available. We add to the previous sample; */

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.*

private fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(400) // pretend we are asynchronously waiting 600 ms
        emit(i) // emit next value
    }
}

fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        simple().collect { value ->
            delay(1000) // pretend we are processing it for 400 ms
            println(value)
        }
    }
    println("Collected in $time ms")
    val time2 = measureTimeMillis {
        simple()
            .buffer() // buffer emissions, don't wait
            .collect { value ->
                delay(1000) // pretend we are processing it for 400 ms
                println(value)
            }
    }
    println("Collected in $time2 ms")
    val time3 = measureTimeMillis {
        simple()
            .conflate() // buffer emissions, don't wait
            .collect { value ->
                delay(1000) // pretend we are processing it for 400 ms
                println(value)
            }
    }
    println("Collected in $time3 ms")
}

/* Notice how in the third one where we use conflate we only get 1 and 3. We didn't get 2 because 3 was already ready,
* so 2 was dropped. Use conflate if you need this sort of behaviour (i.e. only most recent status is needed). */