package solutions.day03

import solutions.SolutionInterface
import util.read2dCharArray
import java.io.File

/**
 * We're traversing the grid, wrapping around vertically as the pattern repeats.
 */
class Solution : SolutionInterface {
    private val tree = '#'

    private fun calculateTreesOnSlope(grid: Array<CharArray>, slope: Slope): Long {
        val rows = grid.size
        val cols = grid[0].size

        var trees: Long = 0
        var colIndex = 0
        for (rowIndex in 0 until rows step slope.downMove) {
            if (grid[rowIndex][colIndex] == tree) {
                trees++
            }
            colIndex += slope.rightMove
            if (colIndex >= cols) {
                colIndex -= cols
            }
        }
        return trees
    }

    override fun runPart1(inputFile: File): String {
        val grid = read2dCharArray(inputFile)

        val trees = calculateTreesOnSlope(grid, Slope(3, 1))

        return trees.toString()
    }

    override fun runPart2(inputFile: File): String {
        val grid = read2dCharArray(inputFile)

        val slopes = listOf(
            Slope(1, 1),
            Slope(3, 1),
            Slope(5, 1),
            Slope(7, 1),
            Slope(1, 2)
        )

        return slopes.map { slope -> calculateTreesOnSlope(grid, slope) }.reduce { acc, i -> acc * i }.toString()
    }

    data class Slope(
        val rightMove: Int,
        val downMove: Int
    )
}
