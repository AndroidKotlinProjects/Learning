
fun main() {

    val benchPress = Exercise("Bench press")
    val squat = Exercise("squat")
    val deadlift = Exercise("deadlift")
    val shldrPress = Exercise("Shoulder press")
    val pullUps = Exercise("Pull-ups")

    val quadStretch = Exercise("Quad stretch")
    val hammyStretch = Exercise("Hamstring stretch")
    val calveStretch = Exercise("Calf stretch")

    val strengthRoutineA = Routine("Strength A")
    strengthRoutineA.addExercises(listOf(benchPress, squat, deadlift))
    val strengthRoutineB = Routine("Strength B")
    strengthRoutineB.addExercises(listOf(shldrPress, pullUps))
    val upperDay = Routine("Upper")
    val lowerDay = Routine("Lower")
    val pushDay = Routine("Push")
    val pullDay = Routine("Pull")
    val generalStretch = Routine("Basic stretch")
    generalStretch.addExercises(listOf(quadStretch, hammyStretch, calveStretch))
    val fullBodyStretchRoutine = Routine("Full-body stretch")

    val routineA = Routine("A", mutableListOf(generalStretch, strengthRoutineA))
    val routineB = Routine("B", mutableListOf(generalStretch, strengthRoutineB))

    println("performing routine A")
    routineA.start()
    println("routine A finished!\n")

    println("performing routine B")
    routineB.start()
    println("routine B finished!\n")

}