package collections

fun main() {

    println("begin")

    Library.books
        .filter {
            it.authors.size > 1
        }
        .map {
            "\"${it.title}\" written by ${it.authors.map { it.name }.joinToString(", ")}"
        }
        .forEach {
            println(it)
        }

    // the above map can be shortened by using the transformation function on joinToString
    Library.books
        .filter {
            it.authors.size > 1
        }
        .map {
            "\"${it.title}\" written by ${it.authors.joinToString(", ") { it.name } }"
        }
        .forEach {
            println(it)
        }

}