package Channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/* A fan out is when you have multiple consumers on a single channel. */

fun main() = runBlocking<Unit> {
    val producer = produceNumbersPeriodically()
    repeat(5) { launchProcessor(it, producer) }
    delay(950) // we let them work for less than a second
    producer.cancel() // cancel producer coroutine and thus kill them all
}

fun CoroutineScope.produceNumbersPeriodically() = produce<Int> {
    var x = 1 // start from 1
    while (true) {
        send(x++) // produce next
        delay(100) // wait 0.1s
    }
}

/* Also, pay attention to how we explicitly iterate over channel with for loop to perform fan-out in launchProcessor
* code. Unlike consumeEach, this for loop pattern is perfectly safe to use from multiple coroutines. If one of the
* processor coroutines fails, then others would still be processing the channel, while a processor that is written
* via consumeEach always consumes (cancels) the underlying channel on its normal or abnormal completion.
* TLDR: use foreach instead of consume when receiving from multiple coro's unless you want a "if one fails we all fail"
* situation. */
fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    for (msg in channel) {
        println("Processor #$id received $msg")
    }
}

/* Note that cancelling a producer coroutine closes its channel, thus eventually terminating iteration over the channel
* that processor coroutines are doing. This is because the special cancel token will be sent down the channel and so
* the for loops in the processors will terminate. */