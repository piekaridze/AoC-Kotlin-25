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

    fun part2(input: List<LongRange>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testResult = part1(readLines("Day02_test"))
    check(testResult == 1227775554L) { "Failed with $testResult"  }

    // Read the input from the `src/Day02.txt` file.
    val input = readLines("Day02")
    measureTimePrint { part1(input) } // 21898734247
    part2(input).println()
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
