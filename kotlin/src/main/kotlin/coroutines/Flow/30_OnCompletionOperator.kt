package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private fun simple(): Flow<Int> = (1..3).asFlow()

fun main() = runBlocking<Unit> {
    simple()
        .onCompletion { println("Done") }
        .collect { value -> println(value) }
}

/* produces the same behaviour as using a try {} final {} but looks nicer as it's in operator form. The onCompletion
* operator also has an advantage over finally, whic is that a nullable Throwable parameter of the lambda can be used to
* determine whether the flow collection was completed normally or exceptionally. See the next sample for an example. */