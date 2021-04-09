package AndroidDevsYoutube

import kotlinx.coroutines.*

fun main() {
    println("program starting on thread ${Thread.currentThread().name}")
    val job = GlobalScope.launch(Dispatchers.IO) {
        println("launched coroutine for doing network request on thread ${Thread.currentThread().name}")
        val res = doNetworkRequest()
        withContext(Dispatchers.Default) { // IMPORTANT: on android you would use Dispatchers.Main here
            println("setting UI with $res on thread ${Thread.currentThread().name}")
        }
    }
    runBlocking { job.join() }
}

suspend fun doNetworkRequest(): String {
    delay(3000L)
    return "result"
}

/* A coroutine can change its context (thread it is running on). This is super useful because it solves the problem
 * in android of needing to do intensive work (sorting lists etc.) and long running requests (network req etc.) on
 * a non-main thread, but then only being allowed to set UI from the main thread. NOTE: suspend functions are not
 * guaranteed to return in the same thread from which they were called. This means that it is possible to do
 * a network request on Dispatchers.IO and then successfully set the UI without using withContext(Dispatchers.Main),
 * but that is just luck - most of the times it will crash because you more likely than not won't get the main thread.
 * */