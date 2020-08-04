package basics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/* This program shows how even though GlobalScope coro's will run as long as the application is alive, they themselves
* are not enough to keep the application alive. They are forced to cancel upon the application ending. Since all other
* scopes are subscopes of globalscope, this same idea applies to them (though they are more likely to have already been
* cancelled when their respective scopes were cancelled - also remember to cooperate - although at process termination
* that doesn't matter too much but it is good practice (analogous to freeing memeory before ending process)). */
fun main() = runBlocking<Unit> {
    GlobalScope.launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L)
}