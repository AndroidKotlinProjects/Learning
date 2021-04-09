package collections

fun main() {

    println("allowing compound genres;")
    Library.books.groupBy {
        it.genres
    }.forEach {
        println("${it.key}")
        it.value.forEachIndexed { i, item ->
            println("${i+1}. ${item.title}")
        }
    }

    println("\nnot allowing compound genres;")
    Library.books
        .map { book ->
            book.genres.map { Pair(it, book) }
        }
        .flatten()
        .groupBy { genre_book -> genre_book.first }
        .forEach {
            println("${listOf(it.key)}")
            it.value.forEachIndexed { index, it ->
                println("${index+1}. ${it.second.title}")
            }
        }

}