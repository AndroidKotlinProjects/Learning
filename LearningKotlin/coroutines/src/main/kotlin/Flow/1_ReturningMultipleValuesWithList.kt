package Flow

private fun simple(): List<Int> = listOf(1, 2, 3)

fun main() {
    simple().forEach { value ->
        println(value)
    }
}

/* In this program we return a List of values all at once, then iterate through it and print them. */