package ContextAndDispatchers

import kotlinx.coroutines.*

class Activity {
    /* All coroutines coupled to this activity should be run in this coroutine scope so that they are automatically
    * cancelled when the activity is destroyed. */
    private val mainScope = CoroutineScope(Dispatchers.Default)

    fun destroy() {
        /* Calling cancel() on a scope is really doing mainScope.coroutineContext[Job]?.cancel(), so it is still just
        * cancelling a Job, but this job is the root of the tree for that coroutine scope, thus pruning the whole tree
        * of jobs. */
        mainScope.cancel() // this is what we mean by automatic cancel! You still have to cancel the root job
    }

    fun doSomething() {
        // launch ten coroutines for a demo, each working for a different time, but ALL running in the main scope
        repeat(10) { i ->
            mainScope.launch {
                delay((i + 1) * 100L)
                println("Coroutine $i is done")
            }
        }
    }
}

fun main() = runBlocking<Unit> {
    val activity = Activity()
    activity.doSomething() // run test function
    println("The Activity launched 10 coroutines")
    delay(500L) // delay for half a second
    activity.destroy() // cancels all coroutines
    println("Just destroyed the Activity!")
    readLine() // wait and see if any more coroutines complete or if they were all auto cancelled
}

/* CoroutineScope's are a huge part of structured concurrency. All of the coroutines inside a coroutine scope are
* cancelled when the root job of the scope is cancelled (see destroy() function above). */

/* Let us put our knowledge about contexts, children and jobs together. First of all, notice that we have
* mainScope.coroutineContext[Job]?.cancel() in the destroy() function above. This shows the relationship between Scope,
* Context, and Job. CoroutineScope -> CoroutineContext -> Job.
*
* Assume that our application has an object with a lifecycle, but that object is not a coroutine itself. For example,
* we are writing an Android application and launch various coroutines in the context of an Android activity to perform
* asynchronous operations to fetch and update data, do animations, etc. (This is what the Activity class above is
* mimicking.)
*
* All of these coroutines must be cancelled when the activity is destroyed to avoid memory leaks. We, of course, can
* manipulate contexts and jobs manually to tie the lifecycles of the activity and its coroutines, but
* kotlinx.coroutines provides an abstraction encapsulating that: CoroutineScope.
*
* We manage the lifecycles of our coroutines by creating an instance of CoroutineScope tied to the lifecycle of our
* activity. A CoroutineScope instance can be created by the CoroutineScope() or MainScope() factory functions. The
* former creates a general-purpose scope, while the latter creates a scope for UI applications and uses
* Dispatchers.Main as the default dispatcher. We also mimick using MainScope() above by explicitly creating a
* CoroutineScope that uses the default dispatcher.
*
* Now we can launch as many coroutines as we like in this scope, and know that when we destroy() the activity, all
* running coroutines will be automatically cancel()ed just by calling cancel on the mainScope (which calls cancel on
* the root Job). */