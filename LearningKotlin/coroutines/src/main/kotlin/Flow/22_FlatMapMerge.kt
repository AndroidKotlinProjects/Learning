package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/*
*        flow(1: first, 1: second)  flow(2: first, 2: second)  flow(3: first, 3: second)
*       /                          /                          /
* flow(1,                         2,                         3)
*
* flow(1: first, 1: second, 2: first, 2: second, 3: first, 3: second)
*
* We were able to do the above in the previous example, but it took the time of both added together (the outer flow
* had to wait on the inner flow). This is because the previous method did it by emitting the first number, then
* passing it into the second flow, waiting for the second flow, and then proceeding to the second number and so on.
* But what if we concurrently collect all the incoming flows from the outer and merge their values into a single inner
* flow so that values are emitted as soon as possible. */

private fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500) // wait 500 ms
    emit("$i: Second")
}

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis() // remember the start time
    (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
        .flatMapMerge { requestFlow(it) }
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }
}

/* OUTPUT;
1: First at 129 ms from start
2: First at 227 ms from start
3: First at 329 ms from start
1: Second at 630 ms from start
2: Second at 729 ms from start
3: Second at 830 ms from start */

/* As you can see, it takes around 100ms each to collect (1,2,3) from the first flow, but because those emissions are
* happening concurrently (on another thread), we are free to pass emissions to requestFlow() whilst new ones are stil
* coming in. This means it takes around 100ms for the first emission to come in, we can call requestflow() on it which
* will print 1: first, and then waits 500ms to print 1:second. However, in that time, 2 and 3 are also emitted and
* passed to requestFlow and so they do the same thing all before 1:second prints.
*
* TLDR: with flatMapMerge the emissions are done on another thread concurrently and so you can pass emissions into the
* second flow as soon as you get them and begin working on them. This differs to flatMapConcat in that concat does not
* do emissions concurrently and so it must get an emissions, pass it to the second flow, wait for the second flow to
* complete, then wait for another emission, and so on, in a sequential way. It is like the time for the second flow is
* just concatendated onto the end of the first flow. */