private val directions: List<Pair<Int, Int>> = listOf(-1, 0, 1)
    .flatMap { x -> listOf(-1, 0, 1).map { y -> x to y } }
    .filterNot { (x, y) -> x == 0 && y == 0 }

fun main() {
    val testResult = part1(readInputAs2D("Day04_test"))
    check(testResult == 13) { "Failed with $testResult" }

    val testResult2 = part2(readInputAs2D("Day04_test"))
    check(testResult2 == 43) { "Failed with $testResult2" }

    val input = readInputAs2D("Day04")
    measureTimePrint { part1(input) } // 1486
    measureTimePrint { part2(input) } // 9024
}

fun readInputAs2D(name: String): List<MutableList<Char>> = readInput(name)
    .map { it.toCharArray().toMutableList() }

private fun part1(cells: List<List<Char>>): Int = processOnce(cells)

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


private fun part2(cells: List<MutableList<Char>>): Int {
    var liftedThisTime: Int
    var accumulated = 0
    do {
        toRemove.forEach { cells.removePaper(it)}
        toRemove.clear()
        liftedThisTime = processOnce(cells, modify = true)
        accumulated += liftedThisTime
        println("lifted $liftedThisTime")
    } while (liftedThisTime > 0)
    return accumulated
}

private fun List<MutableList<Char>>.removePaper(rowToCell: Pair<Int, Int>) {
    require(this[rowToCell.first][rowToCell.second].isPaper()) {"Removing paper from $rowToCell, but its not pape" }
    this[rowToCell.first][rowToCell.second] = '.'
}

private fun processOnce(cells: List<List<Char>>, modify: Boolean = false): Int {
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
                    ?.takeIf { modify }?.run { (rowN to colN).markForRemoval() }
            }
        }
    }
    return canLift
}

private fun Pair<Int, Int>.markForRemoval() = toRemove.add(this)

private val toRemove = mutableSetOf<Pair<Int, Int>>()