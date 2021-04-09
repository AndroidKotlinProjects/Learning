package basics

import kotlinx.coroutines.*

/* The entirety of the main function is a coroutine, started by the runBlocking coroutine builder. Remember that every
 * coroutine builder function is an extension function of CoroutineScope, which is what the this parameter refers to.
 * One VERY IMPORTANT PROPERTY about coroutines is that an outer coroutine will not finish until every other coroutine
 * in its scope completes (i.e. the outer coro won't finish until all inner coro's (with the same scope) have finished).
 * The reason i added (with the same scope), was because i beleive it is possible to change scope. */

fun main() = runBlocking {
    launch { // launch a new coroutine in the scope of runBlocking (this.launch)
        delay(1000L)
        println("World!")
    }
    println("Hello,")
    /* Hello prints immediately, but the main function cannot terminate because it is an outer coroutine to the
     * coroutine that is printing World, which is in the same CoroutineScope. Once the inner coroutine completes, the
     * main coroutine can also complete, and the program terminates. */
}

/* Now, by using scoping to associate related coro's, we get all the benefits that we saw with job.join(), but without
 * having to manually track the jobs! */