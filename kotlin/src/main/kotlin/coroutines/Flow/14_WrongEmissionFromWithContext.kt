package Flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/* In the previous sample we noted that;
* "Collection of a flow always happens in the context of the coroutine that called the terminal operator. The code in
* the flow { ... } builder also runs in the same context", but what if you want to run the flow {} in one context but
* collect in another. This is a very real concern because you often want to do some long running computation in the
* background so that the main thread is not blocked, but then update UI on the main thread. You might think to do
* the following; */

private fun simple(): Flow<Int> = flow {
    // The WRONG way to change context for CPU-consuming code in flow builder
    withContext(Dispatchers.Default) {
        for (i in 1..3) {
            /* Here the thread.sleep symbolises the CPU being used for a hard and long running computation */
            Thread.sleep(100)
            emit(i) // emit next value
        }
    }
}

fun main() = runBlocking<Unit> {
    simple().collect { value -> println(value) }
}

/* Exception in thread "main" java.lang.IllegalStateException: Flow invariant is violated:
    Flow was collected in [BlockingCoroutine{Active}@3095d8d0, BlockingEventLoop@76f00df5],
	but emission happened in [DispatchedCoroutine{Active}@11e89760, Dispatchers.Default].
	Please refer to 'flow' documentation or use 'flowOn' instead */

/* but you get this ^ error. This is because the emission and collection happened in a different context. So we cannot
* use withContext(). So how do we solve our issue of running the flow {} in the background but collecting in main?  */