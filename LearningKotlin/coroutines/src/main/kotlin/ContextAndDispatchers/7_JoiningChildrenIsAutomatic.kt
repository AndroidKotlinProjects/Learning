package ContextAndDispatchers

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    // launch a coroutine to process some kind of incoming request
    val request = launch {
        repeat(3) { i -> // launch a few children jobs
            launch  {
                delay((i + 1) * 1000L) // variable delay 200ms, 400ms, 600ms
                println("Coroutine $i is done")
            }
        }
        println("request: I'm done and I don't explicitly join my children that are still active")
    }
    request.join() // wait for completion of the request, including all its children
    println("Now processing of the request is complete")
}

/* As you can see, the parent job will implicitly wait for all children jobs to be complete. So you only have to join()
* on the parent job. This is a really neat way to split something up into subcomponents, then wait on the one main
* action, and then when all subcomponents are done, the whole thing is done! */