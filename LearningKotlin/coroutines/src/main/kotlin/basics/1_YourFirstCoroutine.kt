package basics

import kotlinx.coroutines.*

fun main() {
    /* We start by launching a coroutine in the GlobalScope. This scope lives so long as the application is running. It
     * won't prevent the application from closing. This runs in the background. Note that launch (and all other
     * coroutine builder functions e.g. async) are extension functions on CoroutineScope, hence the this parameter*/
    GlobalScope.launch {
        delay(1000L) // non-blocking (w.r.t main) delay for 1 second (default time unit is ms)
        println("World!") // prints after delay, and hence prints second
    }
    println("Hello,") // main thread continues while coroutine is delayed, hence prints first
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
}