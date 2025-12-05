import kotlin.math.pow

fun main() {
    val testResult = part1(readInput("Day03_test"))
    check(testResult == 357) { "Failed with $testResult" }

    val input = readInput("Day03")
    measureTimePrint { part1(input) } // 17321


    val testResult2 = part2(readInput("Day03_test"))
    check(testResult2 == 3121910778619) { "Failed with $testResult2" }
    measureTimePrint { part2(input) } // 171989894144198

}

private fun part1(batteries: List<String>): Int {
    return batteries.sumOf { it.toMaxJolts() }
}

private fun String.toMaxJolts(): Int {
    val batteries = this.chunked(1).map { it.toByte() }
    var maxFirst = batteries[0]
    var maxSecond = batteries[1]
    for (i in 2 until batteries.size) {
        if (maxFirst < maxSecond) {
            maxFirst = maxSecond
            maxSecond = batteries[i]
        } else if ( batteries[i] > maxSecond ) {
            maxSecond = batteries[i]
        }
    }
    val result = maxFirst*10+maxSecond
//    println("Maximum value of jolts is $result for $this")
    return result
}

// **** part 2 ****

private fun part2(batteries: List<String>): Long {
    return batteries.sumOf { it.toMaxJolts12() }
}

private fun String.toMaxJolts12(): Long {
    val batteries = this.chunked(1).map { it.toByte() }
    val battery = batteries.take(12).toMutableList()
    val rest = batteries.drop(12)
    rest.forEach { candidate ->
        for (i in 0..(battery.size-2)) {
            if (battery[i] < battery[i + 1]) {
                battery.removeAndShift(i, candidate)
                return@forEach
            }
        }

        candidate.takeIf { it > battery.last() }
            ?.run { battery.removeLast() }
            ?.also { battery.addLast(candidate) }

    }
    val result = battery.foldIndexed(0L) { index, acc, next ->
        return@foldIndexed acc + next * tens[index]
    }
    println("Maximum value of jolts [$battery] is $result for $this")
    return result
}

private fun MutableList<Byte>.removeAndShift(i: Int, candidate: Byte) {
    this.removeAt(i)
    this.addLast(candidate)
}

private val tens = IntRange(0, 11).reversed().map { 10.0.pow(it).toLong() }
