package basics

import kotlinx.coroutines.*

/* This little program demonstrates why manually keeping track of coroutines via references to their jobs is
 * both tedious and error prone. You have to pass job references around which is cumbersome, and if you lose them then
 * you've potentially lost your only way of stopping that coro, and so it will use resources :(. Join's can also result
 * in endless waits if your coro hangs!*/

fun main() {
    /* Here we start a coroutine that we don't realise will run forever and we have no way of stopping it */
    startEndlessCoro()
    /* At this point we no longer have a reference to our endless coro (so it cannot be stopped), but it is still
    * running and will continue to do so for the rest of the application lifetime. This can cause out of memory
    * issues and bad performance */

    /* now we attempt to do our first important thing and then join it (resync with it) once it is done */
    val job1 = doImportantThing1()
    runBlocking {
        job1.join() // stuck waiting here forever because job1 is hanging!
    }
    /* and because it unintentionally hangs we never get to do our second important thing! */
    doImportantThing2()
}

fun startEndlessCoro() {
    GlobalScope.launch {
        var i = 0
        while(true) {
            delay(1000)
            println("unstoppable coro has been running for approx ${++i} seconds")
        }
    }
}

/* Simulates a request that unintentionally hangs for some reason (maybe network is slow, logic error, etc.) */
fun doImportantThing1(): Job {
    return GlobalScope.launch {
        println("doing first important thing...")
        while (true) {} // simulate function hanging
    }
}

fun doImportantThing2() {
    println("doing second important thing...")
}