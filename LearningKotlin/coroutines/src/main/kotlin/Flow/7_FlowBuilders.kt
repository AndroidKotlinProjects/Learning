package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/* Use asFlow to convert containers to flows e.g. lists, arrays, etc. */
fun listAsFlow(): Flow<Int> = listOf(1,2,3).asFlow()

/* Use flowOf when you have a fixed set of things that you want to make into a flow */
fun fixedNumbersToFlow(): Flow<Int> = flowOf(4,5,6)

fun main() = runBlocking<Unit> {

    val flow1 = listAsFlow()
    val flow2 = fixedNumbersToFlow()

    flow1.collect {
        println("collected $it from flow1")
    }
    flow2.collect {
        println("collected $it from flow2")
    }

}