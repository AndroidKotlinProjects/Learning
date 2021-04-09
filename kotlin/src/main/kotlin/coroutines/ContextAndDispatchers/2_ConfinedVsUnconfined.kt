package ContextAndDispatchers

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread (launches in context of the parent, main runBlocking coroutine)
        println("Unconfined before delay      : I'm working in thread ${Thread.currentThread().name}")
        /* This is a suspending function and we launched with the unconfined dispatcher so will now jump over to the
        * thread that delay uses for execution. */
        delay(500)
        println("Unconfined after delay       : After delay in thread ${Thread.currentThread().name}") // jumped thread
    }
    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking before delay: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking after delay :  After delay in thread ${Thread.currentThread().name}")
    }
}

/* The Dispatchers.Unconfined coroutine dispatcher starts a coroutine in the caller thread, but only until the first
* suspension point. After suspension it resumes the coroutine in the thread that the suspending function was invoked in.
* The unconfined dispatcher is appropriate for coroutines which neither consume CPU time nor update any shared data
* (like UI) confined to a specific thread. The reason for the former is you don't want to it suddenly start running in
* a thread that shouldn't have CPU time stolen from it, and for the latter, if you must stay in a certain thread to
* access something (e.g. UI in android can only be accessed from the main thread), then it makes no sense to use
* unconfined because you might end up on another thread that cannot access the thing in question (e.g. UI). */

/* The unconfined dispatcher is an advanced mechanism that can be helpful in certain corner cases where dispatching of
* a coroutine for its execution later is not needed or produces undesirable side-effects, because some operation in a
* coroutine must be performed right away. The unconfined dispatcher should not be used in general code. */

/* TLDR: Unconfined means the coroutine can run on any thread. To make a coroutine confined you must provide a
* dispatcher when starting the coroutine, or use a builder that inherits a dispatcher from the parent scope. */