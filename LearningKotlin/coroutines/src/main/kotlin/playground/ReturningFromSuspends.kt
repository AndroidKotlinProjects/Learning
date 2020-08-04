package playground

import kotlinx.coroutines.*

fun main() {
    for (i: Int in 0..10) {
        val job = GlobalScope.launch(Dispatchers.Default) {
            println("loop $i on thread: ${Thread.currentThread().name}")
            suspendingFunction()
            println("loop $i on thread: ${Thread.currentThread().name}")
        }
        runBlocking { job.join() }
    }
}

suspend fun suspendingFunction() {
    delay(1000L)
}

/* TODO: this program was intended to show how a suspend function can return on a different thread than the one
*   it was started on. The motivation for this was to explain how you can sometimes luck out and set the UI on
*   android without using withContext(Dispatchers.Main) because the suspend function just so happened to return
*   into the main thread anyway. Again, this is luck, use withContext(..) */