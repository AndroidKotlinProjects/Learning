package collections

import kotlin.math.roundToInt

fun main() {

    // TAKES A COLLECTION AND REDUCES IT TO A SINGLE ELEMENT

    Library.books.map { it.price }
        .reduce { accumulator, price ->
            accumulator + price
        }
        .also { println(it) }

    // although this could be done easier like this
    Library.books.map { it.price }.sum().also { println(it) }
    // or
    Library.books.sumBy { it.price }.also { println(it) }
    // i.e. sum is just a special case of reducing because it is common
    // average(), minBy(), maxBy() also exist

    Library.books.map { it.price }.average().roundToInt()
        .also { println("average price is: $it") }

    Library.books.minBy { book ->
        book.price
    }?.let {  book: Book ->
        println("Min price is ${book.title} for ${book.price}")
    }

    Library.books.maxBy {
        it.price
    }?.let {
        println("Max price is ${it.title} for ${it.price}")
    }
}