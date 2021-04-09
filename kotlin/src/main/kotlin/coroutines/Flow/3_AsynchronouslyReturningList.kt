package Flow

import kotlinx.coroutines.*

private suspend fun simple(): List<Int> {
    delay(2000) // pretend we are doing something asynchronous here
    return listOf(1, 2, 3)
}

fun main() = runBlocking<Unit> {
    println("Getting list...")
    simple().forEach { value ->
        println(value)
    }
}

/* Using coroutines we can make simple() a suspending function, that way it can be done in the background if needed and
* won't block the main thread (like using a Sequence and Thread.sleep() would). Hmm but now the values are returned
* all at once again... we need a mixture of the two which gives us a stream of values that are asynchronously
* computed... could this be flow??? */