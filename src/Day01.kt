import kotlin.math.abs

private const val START = 50
private const val MAX_EXCLUSIVE = 100

fun main() {
    fun part1(input: List<String>): Int {
        var currentPosition = START
        return input
            .count { line ->
                val stepSize = line.moveIt() % MAX_EXCLUSIVE
                currentPosition = (currentPosition + stepSize).normalizePosition() % MAX_EXCLUSIVE
                currentPosition == 0
            }
    }

    fun part2(input: List<String>): Int {
        var currentPosition = START
        var zeroesPassed = 0
        for (line in input) {
            val stepSize = line.moveIt()
//            println("Pos $currentPosition, Step $line parsed as $stepSize")
            val incrByFullRound = abs((stepSize + currentPosition) / 100)
            zeroesPassed += incrByFullRound
            if (movedBackOverZero(stepSize, currentPosition)) zeroesPassed++
            currentPosition = getPosition(currentPosition, stepSize)
            if (currentPosition == 0) { zeroesPassed++ }
        }
        return zeroesPassed
    }


    val input = readInput("Day01")
    val one = part1(input).also { it.println() } // 1182 OK
    val two = part2(input).also { it.println() } // 8217 Not OK
}

fun String.moveIt() = first()
    .let { direction -> this.drop(1).toInt() * if (direction == 'R') 1 else -1 }

fun Int.normalizePosition() = takeIf { it >= 0 } ?: (this % 100 + MAX_EXCLUSIVE)

fun getPosition(currentPosition: Int, stepSize: Int): Int {
    val x =  (currentPosition + stepSize).normalizePosition() % MAX_EXCLUSIVE
    require(x in 0..<MAX_EXCLUSIVE) { "X should be in range [0, $MAX_EXCLUSIVE)" }
    return x
}

fun movedBackOverZero(stepSize: Int, currentPosition: Int): Boolean = stepSize + currentPosition < 0

