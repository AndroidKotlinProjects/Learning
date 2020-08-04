package AndroidDevsYoutube

import kotlinx.coroutines.*

fun main() {
    println("Main says hello from ${Thread.currentThread().name}")
    val job = GlobalScope.launch {
        println("Coroutine says hello from ${Thread.currentThread().name}")
        println("Coroutine says goodbye from ${Thread.currentThread().name}")
    }
    println("Main says goodbye from ${Thread.currentThread().name}")
    runBlocking {
        job.join()
    }
}

/* As you can see, launching a coroutine in the GlobalScope actually launches that coroutine on a new thread. */