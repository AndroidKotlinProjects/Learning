package CancellationAndTimeouts

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {

    /* TimeoutCancellationException is a type of CancellationException and so all resources are closed in the usual
    * way. If you have some extra resources or logic that needs to be done, you can always catch the exception and
    * do it there. */
    try {
        withTimeout(500) {
            println("sending long-running request...")
            delay(1000)
        }
    } catch (e: TimeoutCancellationException) {
        println("request timed out, doing whatever extra cleanup or logic i need to do")
    }

    /* Alternatively, if you don't want the exception, but instead just want null returned for your result, then you
    * can use withTimeoutOrNull which will either succeed and give you the correct result, or timeout and give you
    * null. */
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            println("performing long calculations, step $i ...")
            delay(500L)
        }
        "ResultOfCalculation" // will get cancelled before it produces this result
    }
    println("Result is $result")
    readLine()
}