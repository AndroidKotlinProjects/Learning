package Channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking<Unit> {
    val channel = Channel<Int>(4) // create buffered channel
    val sender = launch { // launch sender coroutine
        repeat(10) {
            println("Sending $it") // print before sending each element
            channel.send(it) // will suspend when buffer is full
            println("$it successfully sent!")
        }
    }
    // don't receive anything... just wait....
    delay(1000)
    sender.cancel() // cancel sender coroutine
}

/* In sample 1_Basics, we were unsure of the behaviour between sending and receiving on a channel. Unbuffered channels
* transfer elements when sender and receiver meet each other (aka rendezvous). If send is invoked first, then it is
* suspended until receive is invoked, if receive is invoked first, it is suspended until send is invoked. Go back and
* look at 1_Basics, it is now clear that the first send suspends and cannot continue until the first receive, which
* occurs after the 3 second sleep. */

/* We can create buffered channels however, which will not suspend so long as they are not full. As you can see,
* because the capacity of the channel is 4 and we never consume from it, 0-3 get sent successfully but on 4 the .send
* suspends (and never resumes because we never consume). Both the Channel factory function and the producer {} builder
* take an optional capacity parameter which enables this buffering. */