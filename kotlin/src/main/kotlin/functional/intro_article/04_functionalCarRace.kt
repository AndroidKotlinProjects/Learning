package functional.intro_article

import kotlin.random.Random

data class State(
    var time: Int,
    var carPositions: List<Int>
)

fun outputCar(carPos: Int): String {
    return "-".repeat(carPos)
}

// we are passed only what we need (not the whole State object)
fun moveCars(cars: List<Int>): List<Int> {
    return cars.map {
        if (Random.nextInt() % 2 == 0) {
            it + 1
        } else {
            it
        }
    }
}

fun runRound(state: State): State {
    return state.copy(
        time = state.time-1,
        carPositions = moveCars(state.carPositions)
    )
}

fun draw(state: State) {
    println()
    state.carPositions.map {
        outputCar(it)
    }.joinToString("\n")
        .also { println(it) }
}

fun race(state: State) {
    draw(state)
    if (state.time > 0) {
        race(runRound(state))
    }
}

fun main() {
    race(State(5, listOf(1,1,1)))
}