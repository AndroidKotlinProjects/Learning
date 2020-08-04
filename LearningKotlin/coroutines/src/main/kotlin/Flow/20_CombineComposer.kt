package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking<Unit> {

    val nums = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms

    val strs = flowOf("one", "two", "three").onEach { delay(400) } // strings every 400 ms

    /* Here we use zip and so it has to wait for the longest of the two to be able to match two emissions up. Hence it
    * must wait 3*400ms in total, or 400ms per emission */
    val startTime1 = System.currentTimeMillis() // remember the start time
    nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string with "zip"
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime1} ms from start")
        }

    /* When we use a combine, we get quite a different output, where a line is printed at each emission from either
    * nums or strs flows. This is why we get 2 -> one and 3 -> two, because the nums emits faster than the strs and so
    * it only has access to the previous strs value. Note that the first combination only prints after 400ms because
    * even though the nums emits its first number after 300ms there is no inital str until after 400ms. */
    val startTime2 = System.currentTimeMillis() // remember the start time
    nums.combine(strs) { a, b -> "$a -> $b" } // compose a single string with "combine"
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime2} ms from start")
        }
}