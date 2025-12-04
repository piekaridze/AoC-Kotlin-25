import kotlin.math.log10
import kotlin.math.pow
import kotlin.time.measureTime


fun measureTimePrint(measured: () -> Any) {
    val value: Any
    val time = measureTime { value = measured() }
    println("Answer: $value (done in $time)")
}

fun main() {
    fun part1(input: List<LongRange>): Long {
        return input .fold(0L) { acc, range ->
            val addition = range.filter { isTwoSameSequencesWithDividingSolution(it) }.sum()
            return@fold acc + addition
        }
    }

    fun part2(input: List<LongRange>): Long {
        return input .fold(0L) { acc, range ->
            val invalidIds = range.filter { isInvalidIdSameSequenceAtLeastTwice(it) }
            val addition = invalidIds.sum()
            return@fold acc + addition
        }
    }

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testResult = part1(readLines("Day02_test"))
    check(testResult == 1227775554L) { "Failed with $testResult"  }
    val testResult2 = part2(readLines("Day02_test"))
    check(testResult2 == 4174379265L) { "Failed with $testResult"  }

    // Read the input from the `src/Day02.txt` file.
    val input = readLines("Day02")
    measureTimePrint { part1(input) } // 21898734247
    measureTimePrint { part2(input) } // 28915664389
}

private fun readLines(fileName: String): List<LongRange> = readInputByComa(fileName)
    .map { it -> it.split("-").map { it.toLong() } }
    .map { LongRange(it[0], it[1]) }

fun isTwoSameSequencesWithStringSolution(id: Long): Boolean {
    val asString = id.toString()
    return asString.length % 2 == 0 &&
            asString.take(asString.length / 2) == asString.substring(asString.length / 2)
}

fun isTwoSameSequencesWithDividingSolution(id: Long): Boolean {
    val digits = if (id == 0L) 1 else (log10(id.toDouble()).toInt() + 1)
    if (digits % 2 != 0) return false
    val half = digits / 2
    val divisor = 10.0.pow(half.toDouble()).toLong()
    return id / divisor == id % divisor
}

// part 2

/**
 * an ID is invalid if it is made only of some sequence of digits repeated at least twice.
 * So, 12341234 (1234 two times), 123123123 (123 three times),
 * 1212121212 (12 five times), and 1111111 (1 seven times) are all invalid IDs.
 */
fun isInvalidIdSameSequenceAtLeastTwice(id: Long): Boolean {
    //  1111111 -> {[1,1,1,1,1,1,1]}, 123123 -> {[1,2,3,1,2,3], [12,31,23], [123,123]} - find if any array has all elements the same
    if (id < 10) return false
    val numberString = id.toString()
    val divisors = getDivisors(numberString.length)
    return divisors.any { divisor -> numberString.tokenizedWithSizeAreAllTheSame(divisor) }
}

private fun String.tokenizedWithSizeAreAllTheSame(divisor: Int) = this.chunked(divisor).toSet().size == 1

val divisors = HashMap<Int, List<Int>>()

fun getDivisors(number: Int): List<Int> {
    return divisors.computeIfAbsent(number) { computeDivisors(it) }
}

fun computeDivisors(number: Int): List<Int> {
    val divisors = mutableSetOf<Int>()
    divisors.add(1)
    for (i in 2..kotlin.math.sqrt(number.toDouble()).toInt()) {
        if (number % i == 0) {
            divisors.add(i)
            divisors.add(number / i)
        }
    }
    return divisors.toList()
}

