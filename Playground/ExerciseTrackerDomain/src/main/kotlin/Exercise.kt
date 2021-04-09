import Components.Component

enum class WorkoutType(val type: String) {
    STRENGTH("Strength"),
    CARDIO("Cardio"),
    MOBILITY("Mobility")
}

sealed class Exercise() {
    abstract var name: String
    abstract var type: WorkoutType
    abstract fun start(): Unit

    class WeightedSetsExercise

    class TimedSetsExercise

    class PlainSetsExercise

    class DurationExercise

}





/* Exercises need to be able to support doing sets and reps, timed und un-timed reps, a single activity such as a run
* which may be timed or untimed,  */

/* An emerging pattern seems to be the ability to have a timed and untimed version of something. Maybe if it is a
* timeable type then the onstart will start the timer. */

/* could a single activity just be sets=1 reps=1 ? and then just give it a different name? seems like a hacky solution... */

/* THIS IS THE MAIN FUNCTIONALITY OF THE APP SO THINK ABOUT IT AND COME UP WITH A CLEAN, EXTENSIBLE, FLEXIBLE SOLUTION */