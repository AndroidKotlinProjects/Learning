package ContextAndDispatchers

import kotlinx.coroutines.*

private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking(CoroutineName("main")) {
    log("Started main coroutine")
    // run two background value computations
    val v1 = async(CoroutineName("customCoroName1")) {
        delay(500)
        log("Computing v1")
        252
    }
    val v2 = async(CoroutineName("customCoroName2")) {
        delay(1000)
        log("Computing v2")
        6
    }
    log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
}

/* It is good to name your coroutines that are used for specific tasks because it helps in debug logs */