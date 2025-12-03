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
        var zeroesCount = 0
        input.map { line -> line.moveIt() }
            .fold(START) { currentPosition, stepSize ->
                zeroesCount += abs(stepSize / MAX_EXCLUSIVE)
                val newPosition = getPosition(currentPosition, stepSize)
                if (newPosition == 0 ||
                    stepSize > 0 && newPosition < currentPosition ||
                    currentPosition != 0 && stepSize < 0 && newPosition > currentPosition) {
                    zeroesCount++
                }
                newPosition
            }

        return zeroesCount
    }


    val input = readInput("Day01")
    val one = part1(input).also { it.println() } // 1182 OK
    val two = part2(input).also { it.println() } // 6907 OK
}

fun String.moveIt() = first()
    .let { direction -> this.drop(1).toInt() * if (direction == 'R') 1 else -1 }

fun Int.normalizePosition() = takeIf { it >= 0 } ?: (this % MAX_EXCLUSIVE + MAX_EXCLUSIVE)

fun getPosition(position: Int, stepSize: Int): Int {
    val newPosition =  (position + stepSize).mod(MAX_EXCLUSIVE)
    require(newPosition in 0..<MAX_EXCLUSIVE) { "position should be in range [0, $MAX_EXCLUSIVE), but was $newPosition" }
    return newPosition
}