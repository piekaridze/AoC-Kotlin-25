private val directions: List<Pair<Int, Int>> = listOf(-1, 0, 1)
    .flatMap { x -> listOf(-1, 0, 1).map { y -> x to y } }
    .filterNot { (x, y) -> x == 0 && y == 0 }

fun main() {
    val testResult = part1(readInputAs2D("Day04_test"))
    check(testResult == 13) { "Failed with $testResult" }

//    val testResult2 = part2(readInput("Day04_test"))
//    check(testResult2 == 0) { "Failed with $testResult2" }

    val input = readInputAs2D("Day04")
    measureTimePrint { part1(input) }
//    measureTimePrint { part2(input) }
}

fun readInputAs2D(name: String): List<List<Char>> {
    return readInput(name).map { it.toCharArray().toList() }
}

private fun part1(cells: List<List<Char>>): Int {
    var canLift = 0
    val context = MapContext(cells.size - 1, cells[0].size - 1)
    cells.indices.forEach { rowN ->
        cells[rowN].indices.forEach { colN ->
            val current = cells[rowN][colN]
            if (current.isPaper()) {
                validNeighboursCoordinates(rowN, colN, context)
                    .map { cells[it.first][it.second] }
                    .count { it.isPaper() }
                    .takeIf { paperNeighboursCount -> paperNeighboursCount < 4 }
                    ?.run { canLift++ }
            }
        }
    }
    return canLift
}

private fun validNeighboursCoordinates(rowN: Int, colN: Int, context: MapContext): List<Pair<Int, Int>> = directions
    .map { it.first + rowN to it.second + colN }
    .filter { notOutOfBounds(it, context) }

private fun notOutOfBounds(pointCoordinates: Pair<Int, Int>, context: MapContext): Boolean =
    pointCoordinates.first >= 0 &&
            pointCoordinates.second >= 0 &&
            pointCoordinates.first <= context.maxRowNumber &&
            pointCoordinates.second <= context.maxColNumber

private fun Char.isPaper() = this == '@'

data class MapContext(val maxRowNumber: Int, val maxColNumber: Int)


private fun part2(input: List<List<Char>>): Int = TODO()