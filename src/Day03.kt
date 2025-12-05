fun main() {
    val testResult = part1(readInput("Day03_test"))
    check(testResult == 357) { "Failed with $testResult" }

    val input = readInput("Day03")
    measureTimePrint { part1(input) } // 17321


    val testResult2 = part2(readInput("Day03_test"))
    check(testResult2 == 3121910778619) { "Failed with $testResult" }
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
    println("Maximum value of jolts is $result for $this")
    return result
}

// **** part 2 ****

private fun part2(batteries: List<String>): Long {
    TODO()
}