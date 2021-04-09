package playground

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    GlobalScope.launch { // launch a new coroutine in background and continue i.e. non-blocking w.r.t main
        println("before first delay")
        delay(1000L)
        println("after first delay")
        runBlocking {
            println("before runBlocking delay")
            delay(2000L)
            println("after runBlocking delay")
        }
        println("World!")
    }
    println("Hello,") // main thread continues here immediately
    runBlocking {     // but this expression blocks the main thread
        delay(4000L)  // ... while we delay for 2 seconds to keep JVM alive
    }
}

/* Observations: It prints hello first. It then immediately prints the before first, then sleeps for 1 second, then
 * prints the after first. It then prints the before runBlocking, then sleeps for 2 seconds, and then prints after
 * runBlocking and World one after another straight away. */

/* Explanation: launching a coroutine must have a small amount of overhead, this is why the Hello can be printed before
 * the before first message. The first delay is trivial to understand, it suspends that whole outer coroutine. The
 * reason World doesn't print before after runBlocking is because runBlocking suspends the thread it runs on, which
 * means the outer coroutine is suspended, but the inner coroutine inside runBlocking runs. */

/* The reason you shouldn't use runBlocking inside coroutines is because it will suspend all outer coroutines. It kind
 * negates the whole purpose of coroutines. */