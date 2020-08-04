package CancellationAndTimeouts

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {

    GlobalScope.launch(Dispatchers.IO) {
        withTimeout(500L) {
            repeat(1000) { i ->
                println("(1) I'm sleeping $i ...")
                delay(100L)
            }
        }
    }

    withTimeout(1300L) {
        repeat(1000) { i ->
            println("(2) I'm sleeping $i ...")
            delay(500L)
        }
    }
    readLine()
}

/* Notice how (1) times out but the exception seems to be handled automatically by the outer scope. On the other hand,
* when (2) times out, the exception is not caught by anything. The TimeoutCancellationException that is thrown by
* withTimeout is a subclass of CancellationException. We have not seen its stack trace printed on the console before.
* That is because inside a cancelled coroutine (1) CancellationException is considered to be a normal reason for
* coroutine completion. However, in this example we have used withTimeout right inside the main function (2). */