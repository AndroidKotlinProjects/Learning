package functional.intro_article

import kotlin.random.Random

var time = 5
val carPositions = mutableListOf<Int>(1,1,1)


fun drawCar(car: Int) {
    println("-".repeat(car))
}

fun draw() {
    println()
    for (car in carPositions) {
        drawCar(car)
    }
}

fun moveCars() {
    var i: Int = 0
    for (i in (0..carPositions.size-1)) {
        if (Random.nextInt() % 2 == 0) {
            carPositions[i]++
        }
    }
}

fun runRound() {
    time -= 1
    moveCars()
}

fun main() {

    while (time > 0) {
        runRound()
        draw()
    }
}