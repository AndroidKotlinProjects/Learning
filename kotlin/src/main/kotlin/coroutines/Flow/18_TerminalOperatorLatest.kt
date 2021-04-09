package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.*

private fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(400) // pretend we are asynchronously waiting 100 ms
        emit(i) // emit next value
    }
}

fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        simple()
            .collectLatest { value -> // cancel & restart on the latest value
                println("Collecting $value")
                delay(1000) // pretend we are processing it for 300 ms
                println("Done $value")
            }
    }
    println("Collected in $time ms")
}

/* For some terminal operators there exists a xLatest version of the operator i.e. collect -> collectLatest. This
* variant cancels the collecting coroutine and restarts it whenever a new value comes in. This is similar to
* conflation except that conflation cannot stop once it starts processing an emission, even if a new emission comes in
* 0.1ms after starting processing the current emission.
*
* Notice that all 3 values will make it into the collectLatest operator, but only number 3 will make it to done. This
* is because the processing takes 1000ms but a new value is available every 400ms.
*
* Use conflate when both the emitter and collector are slow, prefer xLatest when just the collector is slow. */