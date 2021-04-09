package AndroidDevsYoutube

import kotlinx.coroutines.*

fun main() {

    val job1 = GlobalScope.launch(Dispatchers.Default) {
        println("beginning calculations...")
        delay(5000L)
        println("calculations done!")
    }
    runBlocking {
        delay(2000L)
        job1.cancel()
        println("Job1 was cancelled!")
    }

    val job2 = GlobalScope.launch {
        println("beginning fibonacci...")
        for (n in 35..45) {
            println("fib($n) = ${fib(n)}")
        }
        println("fibonacci done!")
    }
    runBlocking {
        delay(1000L)
        job2.cancel()
        println("Job2 was cancelled!")
    }
    readLine()
}

fun fib(n: Int): Int {
    if (n==0) return 0;
    if (n==1) return 1;
    return fib(n-1) + fib(n-2)
}

/* When running this, you will notice that when job1 is cancelled, "calculations done" won't print (because that
* coroutine was cancelled). However, you will also notice that when job2 is cancelled, it actually continues to run!
* Why? This is because the first coroutine is suspended and so it is able to be notified that it has been cancelled,
* and thus it stops. The second coroutine however never suspends, and so even though we tell it to cancel, it doesn't
* know that we have, and so it never stops. Cancellation is cooperative by convention in coroutines. If you don't write
* cooperative cancellation code, you risk the cancellations not actually happening. See 7_CooperativeCancel for
* more - basically your coroutine has to cooperate with being cancelled by suspending, manually checking, timeout, etc.
*  */