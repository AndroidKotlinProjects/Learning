package ContextAndDispatchers

import kotlinx.coroutines.*

/* Main itself is a coroutine because we use the runBlocking builder. This coro will run on the main thread. */
fun main() = runBlocking<Unit> {

    /* Here we launch another coroutine, whose purpose is to launch another two coroutines */
    val request = launch {
        // it spawns two other jobs, one with GlobalScope
        GlobalScope.launch {
            println("job1: I run in GlobalScope and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the request")
        }
        // and the other inherits the parent context
        launch {
            delay(100)
            println("job2: I am a child of the request coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent request is cancelled") // This wont print
        }
    }
    delay(500)
    request.cancel() // cancel processing of the request (the parent to job2)
    delay(1000) // delay a second to see what happens
    println("main: Who has survived request cancellation?")
    /* Note that we don't immediately return here because an outer coro cannot complete until all inner coro's complete */
}

/* When a coroutine is launched in the CoroutineScope of another coroutine, it inherits its context via
* CoroutineScope.coroutineContext and the Job of the new coroutine becomes a child of the parent coroutine's job. When
* the parent coroutine is cancelled, all its children are recursively cancelled, too. Put simply, nesting coroutines
* means the outer coroutine is the parent, and all inner coroutines are children (or children of children ... etc).
* This gives you a tree of jobs, where cancellation of a job means you prune the whole branch of jobs stemming from the
* cancelled job (i.e. cancellation is top-down) */

/* The exception to this rule is GlobalScope. When GlobalScope is used to launch a coroutine, there is no parent for
* the job of the new coroutine. It is therefore not tied to the scope it was launched from and operates independently */