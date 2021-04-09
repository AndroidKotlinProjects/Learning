package Channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/* Fan in is when you have multiple coro's sending to the same channel i.e. multiple producers for the one channel. */

fun main() = runBlocking {
    val channel = Channel<String>() // create a channel of strings
    launch { sendString(channel, "foo", 200L) } // producer 1
    launch { sendString(channel, "BAR!", 500L) } // producer 2
    repeat(6) { // receive first six
        println(channel.receive())
    }
    coroutineContext.cancelChildren() // cancel all children to let main finish
}

suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
    while (true) {
        delay(time)
        channel.send(s)
    }
}