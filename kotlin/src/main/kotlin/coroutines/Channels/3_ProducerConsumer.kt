package Channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/* produceSquares is an extension function of CoroutineScope. All functions that create coroutines are defined as
* extensions on CoroutineScope, so that we can rely on structured concurrency to make sure that we don't have lingering
* global coroutines in our application. As a result, when produceSquares is being called in the below code, it is
* analogous to calling this.produce {} i.e. launching the produce coroutine in the same scope. This keeps structured
* concurrency by making the launched coro a child of the main coro. It returns a ReceiveChannel (a channel to be
* consumed), and uses the produce {} coroutine builder to make a correct producer in the producer/consumer pattern. */
fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x)
}

fun main() = runBlocking {
    val squares = produceSquares()
    /* consumeEach is an extension function on ReceiveChannel<T> which handles correctly consuming */
    squares.consumeEach { println(it) }
    println("Done!")
}

/* Here we use the produce {} coroutine builder over using launch {}. */