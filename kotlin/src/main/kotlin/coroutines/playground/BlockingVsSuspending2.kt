package playground

import kotlinx.coroutines.*

fun main() {
    /* This loop uses runBlocking, and therefore the message cannot print whilst the coroutine is running. This means
     * that we print the message up to #3 but then have to wait 5 seconds, and then the rest of them print */
    for(i in 1..10){
        println("blocking loop #$i")
        if (i == 3) {
            runBlocking { // blocks the main thread whilt it does its work
                delay(5000L)
            }
        }
    }

    /* This loop uses GlobalScope which launches the coro on a new thread. This means that the main thread is free to
     * print all messages without ever stopping. */
    for(i in 1..10){
        println("non-blocking loop #$i")
        if (i == 3) {
            GlobalScope.launch {// doesn't block the main thread, it isn't even on the main thread
                delay(5000L)
            }
        }
    }
}