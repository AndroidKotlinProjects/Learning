package basics

import kotlinx.coroutines.*

/* From within a coroutine you can call both regular and suspending functions. */
fun main() = runBlocking {
    val job = launch { doWorld() }
    println("Hello,")
    job.join()
    doName()
}

/* This is a suspending function, which allows it to suspend or call other suspending functions that suspend e.g.
delay(). It can also call normal functions e.g. println(). */
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}

/* This is a regular function. It cannot call suspending functions */
fun doName() {
    println("My name is Nicholas.")
}