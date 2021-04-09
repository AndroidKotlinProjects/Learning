package basics

import kotlinx.coroutines.*

fun main() = runBlocking { // this: CoroutineScope ("runBlocking scope")
    /* We launch an inner coro in the runBlocking scope. Hence, the outer coro cannot finish until this one does. */
    launch {
        delay(3000L)
        /* (2) This is able to print even though the new scope hasn't finished. This is because coroutineScope {}
         * suspended instead of blocking the whole thread. */
        println("inner coro of runBlocking scope completed")
    }

    // Creates a coroutine scope ("new scope")
    coroutineScope {
        /* This doesn't launch a coro in the runBlocking scope, it's in new scope (which suspends, not blocks) */
        launch {
            delay(5000L)
            println("inner coro of new scope completed")
        }

        delay(1000L)
        println("new scope is waiting on its inner coro, but won't block the thread whilst waiting")
    }

    /* (1) You would think this would print second, but it doesn't, because the new scope cannot finish until its
     * coro is done */
    println("new scope is over, waiting on runBlocking scope's inner coro")
}

/* This program shows how coroutineScope {} is similar to runBlocking {} in that it won't finish until both its
 * code block and all of it's children coro's have finished (see (1)). However, it also shows how it is different
 * because it doesn't block the thread whilst it is doing its work, instead it just suspends which means other work
 * can be done on the thread (see (2)). */