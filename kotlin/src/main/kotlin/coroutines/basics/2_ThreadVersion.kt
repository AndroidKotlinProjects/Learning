package basics

import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun main() {
    thread {
        /* If you try use delay here, you get the error message: Suspend function 'delay' should be called only from a
         * coroutine or another suspend function. This is because delay is a suspend function, and all suspend
         * functions must be called from a coroutine, or from another suspend function (which when you recurse back
         * up the call tree, must have started from inside a coroutine). */
        //delay(1000L) // try use delay here instead of Thread.sleep() and see what happens
        Thread.sleep(1000L)
        println("World!")
    }
    println("Hello,")
    Thread.sleep(2000L)
}

/* The difference between thread.sleep and delay is that delay doesn't actually sleep/block the whole thread. Instead,
* delay just suspends that single coroutine that is running on that thread. This is our first benefit of coro's over
* normal threads! You can think of coroutines as threads on threads. */