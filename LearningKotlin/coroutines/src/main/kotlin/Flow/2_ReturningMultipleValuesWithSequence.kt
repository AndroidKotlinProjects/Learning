package Flow

private fun simple(): Sequence<Int> = sequence { // sequence builder
    println("simple was called!")
    for (i in 1..3) {
        println("simple loop $i")
        Thread.sleep(1000) // pretend we are computing it
        yield(i) // yield next value
    }
    println("simple is returning!")
}

fun main() {
    println("Getting sequence...")
    var i = 1
    simple().forEach { value ->
        println("forEach loop ${i++}")
        println(value)
    }
}

/* Using a sequence we can get the values 1 by 1 instead of all at once. The only problem here is that the main thread
* is blocked when thread.sleep() is called (which would be very bad if this was the UI thread). */