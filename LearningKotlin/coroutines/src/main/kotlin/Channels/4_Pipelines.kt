package Channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val numbers = produceNumbers() // produces integers from 1 and on
    val squares = square(numbers) // squares integers
    repeat(5) {
        println(squares.receive()) // print first five
    }
    println("Done!") // we are done
    coroutineContext.cancelChildren() // cancel children coroutines, without cancelling yourself!!! cool func
}

/* This is a producer that produces infinitely */
fun CoroutineScope.produceNumbers() = produce<Int> {
    var x = 1
    while (true) {
        println("numbers: sending ${x++}")
        send(x)
    } // infinite stream of integers starting from 1
}

/* This is a producer that takes in a channel of values and also returns a channel of augmented values */
fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) {
        println("squares: sending ${x*x}")
        send(x * x)
    }
}

/* A pipeline is a pattern where one coroutine is producing, possibly infinite, stream of values whilst another
* coroutine or coroutines are consuming that stream, doing some processing, and producing some other results. */

/* Again, all functions that create coroutines are defined as extensions on CoroutineScope, so that we can rely on
* structured concurrency to make sure that we don't have lingering global coroutines in our application. */