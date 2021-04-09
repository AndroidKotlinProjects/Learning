package scopeFunctions

fun main() {

    val words = "This is a sentence that contains many words".split(" ")
    val wordLengths = words.map { it.length }

    val wordsWithLengths = words.zip(wordLengths)
        .also {
            it.forEach { println(it) }
        }
        .map {
            "${it.first} has length ${it.second}"
        }
        .also {
            it.forEach { println(it) }
        }

}