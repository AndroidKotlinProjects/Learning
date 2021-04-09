package basics

import kotlinx.coroutines.*

/* This is the same as example 3 except that it is less code because we wrap the main function with runBlocking. It
* is parameterized to Unit because the main function in Kotlin should return Unit. */
fun main() = runBlocking<Unit> { // start the most outer coroutine, note that all of main is a coroutine now
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main coroutine continues here immediately
    delay(2000L) // can use suspend functions because all of main is a coroutine
}

/* runBlocking allows you to test suspend functions in isolation */
class MyTest {
    //@Test
    fun testMySuspendingFunction() = runBlocking<Unit> {
        // here we can use suspending functions using any assertion style that we like
        delay(1000L)
        assert(1==1)
    }
}