package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking<Unit> {
    val nums = (1..3).asFlow() // numbers 1..3
    val strs = flowOf("one", "two", "three", "four") // strings
    nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string
        .collect { println(it) } // collect and print
}

/* called zip because like a zipper it matches up the corresponding elements as it goes along the flow (just like how
* a zipper matches up the corresponding teeth as it goes along the line). Notice how "four" does not make it
* into the collection because the zip had nothing to match it to in the nums flow. */