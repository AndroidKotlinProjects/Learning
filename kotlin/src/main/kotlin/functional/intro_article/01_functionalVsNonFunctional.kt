package functional.intro_article

// from: https://codewords.recurse.com/issues/one/an-introduction-to-functional-programming9

var global = 0 // globals are usually bad

/* This is not a pure function because it accesses data that isn't
* a parameter. It also has a side effect of printing the value. */
fun nonFunctionalIncrement() {
    global++
    println("inside nonFunctionalIncrement(): global is $global")
}

/* This is pure as it only accesses parameters, leaves them
* unchanged (in terms of the callers perspective), and returns
* the result without doing any side effects. */
fun functionalIncrement(num: Int): Int {
    return num + 1
}

fun main() {

    // callers don't expect this to print anything, but it does
    // they only find that out by reading the function or running it
    repeat(10) { nonFunctionalIncrement() }

    // here we explicitly print the result
    repeat(10) {
        global = functionalIncrement(global)
        println(global)
    }

    // a function is overkill anyways, if it doesn't need to be
    // repeated then just do it like so
    repeat(10) {
        global++
        println(global)
    }
}