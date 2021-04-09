package recursion

// video link: https://www.youtube.com/watch?v=ngCos392W4w

// given a number of dots (dots >= 0) and a maximum partition size (p >= 0),
// return the number of ways you can partition the dots
fun q3(dots: Int, p: Int): Int {
    return 5
}

// write a recursive function that returns the number of unique paths
// from the top left corner to the bottom right corner of a grid. You
// can only move 1 right or 1 down each step.
fun q2(r: Int, c: Int): Int {
    if (r == 1 || c == 1) return 1;
    return q2(r-1, c) + q2(r, c-1)
}

// write a recursive function that given an input n, sums all non-negative
// integers up to n
fun q1(n: Int): Int {
    return if (n==0) 0 else n + q1(n-1)
}

fun main() {
    print("enter an integer >= 0: ")
    println(q1(readLine()?.toInt()!!))
    print("enter an r: "); val n = readLine()?.toInt()!!
    print("enter a c: "); val m = readLine()?.toInt()!!
    println(q2(n, m))
    q3(2,2)
}