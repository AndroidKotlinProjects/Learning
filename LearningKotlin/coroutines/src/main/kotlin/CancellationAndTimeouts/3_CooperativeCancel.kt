package CancellationAndTimeouts

import kotlinx.coroutines.*

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5 && isActive) { // cancellable computation loop
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

/* This time we observe that the coroutine does indeed stop because each loop of its calculation it checks to see
* whether it has been requested to cancel. When it sees that it has been requested to cancel, i.e. isActive is false,
* it then cancels. Success! this is cooperative and this is good */

/* NOTE: isActive is an extension property of the CoroutineScope class */