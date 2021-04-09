package playground

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

fun numberFlow(): Flow<Int> = flow {
    for (i in 100..2000 step 100) {
        delay(i.toLong())
        emit(i)
    }
}

fun collectNumbers() = runBlocking {
    println("collectNumbers: press enter when you would like to start collecting the flow...")
    val numberFlow = numberFlow()
    readLine()
    numberFlow.collect() { num: Int ->
        println("received number $num")
    }
}

fun main() {
    println("main: calling collectNumbers!")
    collectNumbers()
}