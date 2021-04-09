package basics

import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch { // launch a new coroutine in background and continue i.e. non-blocking w.r.t main
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main thread continues here immediately
    runBlocking {     // but this expression blocks the main thread
        delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
    }
}

/* runBlocking is a coroutine builder (and thus an extension function of CoroutineScope) which starts a coroutine
* and blocks the thread it was started on until it is completed. This function should not be used from a coroutine.
* It is designed to bridge regular blocking code to libraries that are written in suspending style, to be used in
* main functions and in tests.*/

/* If you were to replace the delay in the GlobalScope coroutine with runBlocking { delay(1000L) }, you would get
 * the same result (except for the extra overhead of starting another coroutine). See RunBlockingInsideCoroutine as
 * to why you shouldn't use runBlocking inside coroutines. */