package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500) // wait 500 ms
    emit("$i: Second")
}

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis() // remember the start time
    (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
        .flatMapLatest { requestFlow(it) }
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
}

/* In a similar way to the collectLatest operator, there is the corresponding "Latest" flattening mode where a
* collection of the previous flow is cancelled as soon as new flow is emitted. Running the above, you will observe that
* only Second will print for number 3 because for 1 and 2 it gets cancelled due to a new number emission. */