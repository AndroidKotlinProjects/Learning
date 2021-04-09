package recursion

// from: https://www.youtube.com/watch?v=Mv9NEXX1VHc

fun rec_fac(n: Int): Int = if (n==1) 1 else n*rec_fac(n-1)
fun it_fac(n: Int): Int {
    var res: Int = 1
    for (i in 1..n) res *= i
    return res
}

fun main() {
    println(rec_fac(7))
    println(it_fac(7))
}