package CancellationAndTimeouts

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally { // finally block is called when the coroutine cancels
            /* using context nonCancellable allows you to suspend even though you have been cancelled. See playground
            * TrySuspendInFinally for an example of what happens if you don't use NonCancellable context (hint: an
            * exception is thrown and there is no delay) */
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(3000L)
                println("job: And I've just delayed for 3 secs because I'm non-cancellable")
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}