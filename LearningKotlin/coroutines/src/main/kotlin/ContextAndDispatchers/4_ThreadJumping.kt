package ContextAndDispatchers

import kotlinx.coroutines.*

private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() {
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->

            /* We pass the context object ctx1 to the runBlocking coro builder */
            runBlocking(ctx1) {
                log("Started in ctx1") // thus this starts in the thread ctx1
                /* This is where we jump this coroutines execution to another thread!!! */
                withContext(ctx2) {
                    log("Working in ctx2")
                }
                /* and then we jump back! */
                log("Back to ctx1")
            }
        }
    }
}