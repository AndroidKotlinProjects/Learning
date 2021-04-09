package playground

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            /*  */
            println("job: I'm running finally")
            try {
                println("delaying inside finally")
                /* This won't actually do a delay, instead it will immediately throw a cancellation exception. Note
                * that there is no 3 second wait before seeing the above and below print messages. */
                delay(3000L)
            } catch (e: CancellationException) {
                println("got a cancellation exception!")
            }

        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}