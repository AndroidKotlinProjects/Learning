class Routine(
    private var name: String,
    private var subroutines: MutableList<Routine> = mutableListOf(),
    private var exercises: MutableList<Exercise> = mutableListOf()
) {
    fun start() {
        subroutines.forEach { subroutine ->
            subroutine.start()
        }
        exercises.forEach {  exercise ->
            exercise.start()
        }
    }

    fun addSubroutine(routine: Routine) {
        subroutines.add(routine)
    }
    fun addSubroutines(routines: Collection<Routine>) {
        routines.forEach { this.subroutines.add(it) }
    }

    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
    }
    fun addExercises(exercises: Collection<Exercise>) {
        exercises.forEach { this.exercises.add(it) }
    }
}