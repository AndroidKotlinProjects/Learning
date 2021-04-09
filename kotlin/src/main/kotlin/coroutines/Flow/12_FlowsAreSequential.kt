package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking<Unit> {
    (1..5).asFlow()
        .filter {
            println("Filtering $it")
            it % 2 == 0
        }
        .map {
            println("Mapping $it")
            "string $it"
        }.collect {
            println("Collected $it")
        }
}

/* OUTPUT;
Filtering 1
Filtering 2
Mapping 2
Collected string 2
Filtering 3
Filtering 4
Mapping 4
Collected string 4
Filtering 5 */

/* As you can see from the output, each individual collection of an emission is performed sequentially (unless special
* operators that operate on multiple flows are used). For example, 1 is emitted and first goes to the filter, but the
* filter drops it out so it doesn't proceed. 2 is emitted and then passes the filter, is mapped to a string, and is
* then collected, all one after another and all before 3 is passed to the filter.
*
* Collection works directly in the coroutine that calls a terminal operator. No new coroutines are launched by default.
* Each emitted value is processed by all the intermediate operators from upstream to downstream and is then delivered
* to the terminal operator after.
*
* TLDR: an emission is put through all operators (potentially being dropped at any given one) in sequential order
* and collected by a terminal operator before that happens for the next emission. */