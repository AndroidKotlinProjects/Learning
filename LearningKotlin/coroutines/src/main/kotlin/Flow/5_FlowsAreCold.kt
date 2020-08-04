package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private fun simple(): Flow<Int> = flow {
    println("simple: Flow started")
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
    println("simple: returning")
}

fun main() = runBlocking<Unit> {
    println("main: Calling simple function...")
    val flow = simple() // simple returns a reference to a Flow<Int> straight away
    println("main: Calling collect...")
    flow.collect { value -> println("main: got $value from the first flow") } // now we call collect() on that flow to actually run it
    println("main: Calling collect again...")
    flow.collect { value -> println("main: got $value from the second flow") }
}

/* When you call a function that returns a flow, said function will actually return straight away, giving you a
* reference to the flow object. The flow {} block will only start running when you call collect() on it. This is known
* as a cold flow. You can think of the flow { } builder as being completely separate from the simple() function. Note
* that you can collect a flow multiple times.
*
* Calling collect is like turning the tap on and letting it FLOW
*
* The fact that simple returns immediately is a key reason as to why it is not marked with suspend modifier. By itself,
* simple() returns quickly and does not wait for anything. The flow starts every time it is collected, that is why
*  we see "Flow started" when we call collect again, with no reason to call simple() again. */