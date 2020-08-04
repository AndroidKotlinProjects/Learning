package Channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        for (x in 1..5) channel.send(x * x)
        channel.close() // we're done sending
    }
    // here we print received values using `for` loop (until the channel is closed)
    for (y in channel) println(y)
    println("Done!")
}

/* Notice that the receiver does not need to check if the channel has been closed or not. As long as the sender calls
* channel.close(), the channel will indeed be closed after all values have been received. You can think of the close
* as being a special value sent along the channel and when the receiver sees it they close. */