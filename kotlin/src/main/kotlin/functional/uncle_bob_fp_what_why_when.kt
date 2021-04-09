package functional

// from: https://www.youtube.com/watch?v=7Zlp9rKHGD4

/* In the intro_article package, the article talks about using recursion
* instead of iterating (for/while loops). Why though? Well, by replacing
* loops with recursive calls, you can make code functional because now
* there is no change in state, there is just multiple different calls to
* functions where the arguments passed are different each time. Below is
* an example; */

fun main() {

    non_fp_squares(10)
    fp_squares(10)

}

fun fp_squares(upto: Int) {
    if (upto > 0) fp_squares(upto-1)
    println(upto*upto)
}

fun non_fp_squares(upto: Int) {
    for (i in 0..upto) {
        println(i*i)
    }
}
