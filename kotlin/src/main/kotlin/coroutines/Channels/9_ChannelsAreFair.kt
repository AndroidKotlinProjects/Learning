package Channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

data class Ball(var hits: Int)

fun main() = runBlocking {
    val table = Channel<Ball>() // a shared table
    launch { player("ping", table) } // ping launched first, thus waiting to receive first
    launch { player("pong", table) } // pong launched second, thus waiting whilst ping is hitting first ball
    table.send(Ball(0)) // serve the ball
    delay(1000) // delay 1 second
    coroutineContext.cancelChildren() // game over, cancel them
}

suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) { // receive the ball in a loop
        ball.hits++
        println("$name $ball")
        delay(300) // wait a bit
        table.send(ball) // send the ball back
    }
}

/* Send and receive operations to channels are fair with respect to the order of their invocation from multiple
* coroutines. They are served in first-in first-out order, e.g. the first coroutine to invoke receive gets the element. */