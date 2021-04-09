package playground

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {

    val req = GlobalScope.launch {
        launch {
            println("child1 of GlobalScope launched!")
            delay(1000)
            println("child1 still running")
        }
        launch {
            println("child2 of GlobalScope launched!")
            delay(1000)
            println("child2 still running")
        }
    }

    delay(500L)
    req.cancel()
    delay(2000)
}

/* Launching in globalscope will mean you don't become the child of whichever coroutine you launched in, HOWEVER, you
* will still become the parent of any coro's launched in you i.e. GlobalScope can be a parent */