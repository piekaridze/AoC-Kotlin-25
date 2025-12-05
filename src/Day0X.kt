fun main() {
    val testResult = part1(readInput("Day0X_test"))
    check(testResult == 0) { "Failed with $testResult" }

//    val testResult2 = part2(readInput("Day0X_test"))
//    check(testResult2 == 0) { "Failed with $testResult2" }

    val input = readInput("Day0X")
    measureTimePrint { part1(input) }
//    measureTimePrint { part2(input) }
}

private fun part1(input: List<String>): Int = TODO()
private fun part2(input: List<String>): Int = TODO()