package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/* Note that we have two flows here and we have a Flow<Flow<String>> at one stage. We need to flatten this down into
* just one Flow so that it can be further processed. You can picture it like this;
*
*        flow(1: first, 1: second)  flow(2: first, 2: second)  flow(3: first, 3: second)
*       /                          /                          /
* flow(1,                         2,                         3)
*
* but we need to turn it into this;
*
* flow(1: first, 1: second, 2: first, 2: second, 3: first, 3: second)
*
* we can achieve this with flatMapConcat {}
* */

private fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500) // wait 500 ms
    emit("$i: Second")
}

fun main() = runBlocking<Unit> {

    val startTime = System.currentTimeMillis() // remember the start time
    (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
        .flatMapConcat { requestFlow(it) }
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
    // for explanation sake, i repeat the above without the conciseness
    val originalFlow: Flow<Int> = (4..6).asFlow().onEach { delay(100L) }
    val flowOfFlow: Flow<String> = originalFlow.flatMapConcat { requestFlow(it) }
}

/* The key thing to notice here is that the flatMapConcat will wait for the inner flow to complete before moving onto
* the next outer flow. For example, each number should be able to flow off 100ms one after another, however, because
* requestFlow's flow takes 500ms to run, we actually have to wait 600ms between nums (100ms outer emission time and
* 500ms inner emission time). This is why it is called flatMapConcat, because the inner flow time is concatenated onto
* the outer flow time. So the we get the resultant flow that we wanted above, but it takes the sum of the time of the
* two flows to happen. */