package Channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
        for (x in 1..5) {
            println("sending ${x*x} to the channel")
            channel.send(x * x)
        }
    }
    println("main is delaying for 3 seconds...")
    delay(3000)
    // here we print five received integers:
    repeat(5) {
        println("channel just received ${channel.receive()}")
    }
    println("Done!")
}

/* So it seems like channels are queues. The channel.send and channel.receive methods are both suspend functions.
* It would also seem like the channel cannot have values sent to it if it is not being received???
*
* ANSWER: this is an unbuffered channel and so a send will suspend until a receive and a receive will suspend until a
* send. Thus the first send suspends until the first receive which occurs after the 3 second sleep. */