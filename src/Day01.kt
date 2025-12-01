private const val START = 50
private const val MAX_EXCLUSIVE = 100

fun main() {
    fun String.moveIt() = first()
        .let { direction -> this.drop(1).toInt() * if (direction == 'R') 1 else -1 }

    fun Int.normalizePosition() = takeIf { it >= 0 } ?: (this + MAX_EXCLUSIVE)

    fun part1(input: List<String>): Int {
        var currentPosition = START
        return input
            .count { line ->
                val stepSize = line.moveIt() % MAX_EXCLUSIVE
                currentPosition = (currentPosition + stepSize).normalizePosition() % MAX_EXCLUSIVE
                currentPosition == 0
            }
    }

    val input = readInput("Day01")
    part1(input).println()
}
