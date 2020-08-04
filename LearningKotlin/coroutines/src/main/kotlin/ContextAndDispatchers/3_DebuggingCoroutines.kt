package ContextAndDispatchers

import kotlinx.coroutines.*

private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking<Unit> {
    val a = async {
        log("I'm computing a piece of the answer")
        6
    }
    val b = async {
        log("I'm computing another piece of the answer")
        7
    }
    log("The answer is ${a.await() * b.await()}")
}
// IMPORTANT, RUN WITH PARAMATER: -Dkotlinx.coroutines.debug

/*
[main @coroutine#2] I'm computing a piece of the answer
[main @coroutine#3] I'm computing another piece of the answer
[main @coroutine#1] The answer is 42

This output makes sense because we made our first coroutine by using the runBlocking builder for main. We then made
the second and third coroutines with the async coroutine builder. It would therefore make sense the the log statements
print the coroutine#[1/2/3] respectively, depending on the scope the log statement is in. The answer statement is in
the runBlocking scope, which is our first coro we made, therefore #1. Likewise for the two asyncs, the first one
that was made gets coro #2, and second gets coro #3.

Remember that coroutines are like threads on threads. Coroutines are analogous to workers (coro's) on a building site
(thread). You can have multiple workers (coro's) on the one building site (thread). We saw that in this example. */
