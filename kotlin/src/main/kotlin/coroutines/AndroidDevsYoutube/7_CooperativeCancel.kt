package AndroidDevsYoutube

import kotlinx.coroutines.*

fun main() {

    val job1 = GlobalScope.launch {
        println("beginning manual cancellation version...")
        for (n in 35..45) {
            if (isActive) {
                println("fib($n) = ${fib(n)}")
            } else {
                println("manual version was cancelled by job.cancel() after waiting a set amount of time!")
                break
            }
        }
    }
    runBlocking {
        delay(1000L)
        job1.cancel()
        println("Job1 was cancelled!")
    }

    GlobalScope.launch(Dispatchers.Default) {
        println("beginning automatic cancellation version...")
        withTimeout(3000L) {
            for(n in 35..45) {
                if (isActive) {
                    println("fib($n) = ${fib(n)}")
                } else {
                    println("automatic version was cancelled via automatic timeout!")
                    break
                }
            }
        }
    }

    readLine()
}

/*

beginning manual cancellation version...
fib(35) = 9227465
fib(36) = 14930352
fib(37) = 24157817
fib(38) = 39088169
fib(39) = 63245986
Job1 was cancelled!
    beginning automatic cancellation version...
    fib(35) = 9227465
    fib(36) = 14930352
fib(40) = 102334155
manual version was cancelled by job.cancel() after waiting a set amount of time!
    fib(37) = 24157817
    fib(38) = 39088169
    fib(39) = 63245986
    fib(40) = 102334155
    fib(41) = 165580141
    fib(42) = 267914296
    automatic version was cancelled via automatic timeout!

The indented stuff is for job2, un-indented for job1. As you can see, job1 is cancelled manually after the 1 second
delay, but the coro is not yet aware that it has been cancelled. It must have been doing the calculation previously
and so it had to print that calculation once it was done (fib(40)) and then check to see if it was cancelled, and only
then would it know that it had been cancelled i.e. it had to cooperate and check!

Job2 on the other hand is cancelled automatically without and need for a manual timer because we used withTimeout(..).
Note that YOU STILL NEED THE isActive check!!!


 */