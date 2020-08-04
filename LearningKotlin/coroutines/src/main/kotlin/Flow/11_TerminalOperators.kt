package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking<Unit> {
    val sum = (1..5).asFlow()
        .map { it * it } // squares of numbers from 1 to 5
        .reduce { a, b -> a + b } // sum them (terminal operator)
    println(sum)
}

/* Terminal operators are operators that cause the flow to be collected. collect() is the simplest of the terminal
* operators. Others include;
* - toList(), toSet() : these are operators that collect and convert the flow
* - first(), first { it > 5 } : retrieve the first emission and cancel, or first that satisfies a predicate and then
* cancel.
* - single() : waits for one and only one emission.
* - reduce(operation), fold(operation, initval) : reduce is an accumulator, meaning it applies the operation across
* the entire flow, accumulating as it goes e.g. you can sum all emissions of the flow to reduce to 1 final sum. fold
* is the same except you can pass a starting value for the accumulator operation. */