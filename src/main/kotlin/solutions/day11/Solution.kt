package solutions.day11

import solutions.GenericSolution
import util.read2dCharArray
import java.io.File

/**
 * We look in all directions from each seat and use the specified rules to create a new seat grid.
 *
 * For part 1 we look only around the seat, while for part 2 we look as far as possible in the same directions.
 *
 * Once we notice that nothing changed, we count the occupied seats and return the result.
 */
class Solution : GenericSolution {
    private val floor = '.'
    private val empty = 'L'
    private val occupied = '#'

    private val directionGrid = arrayOf(
        arrayOf(-1, -1),
        arrayOf(-1, +0),
        arrayOf(-1, +1),
        arrayOf(+0, +1),
        arrayOf(+1, +1),
        arrayOf(+1, +0),
        arrayOf(+1, -1),
        arrayOf(+0, -1)
    )

    private fun countOccupiedSeats(seatGrid: Array<CharArray>): Int {
        return seatGrid.map { seatLine -> seatLine.filter { it == occupied }.count() }.sum()
    }

    private fun Array<CharArray>.copy() = Array(size) { get(it).clone() }

    private fun areSeatCoordinatesValid(it: Array<Int>, seatGrid: Array<CharArray>): Boolean {
        if (it[0] < 0 || it[1] < 0) {
            return false
        }

        if (it[0] >= seatGrid.size) {
            return false
        }

        if (it[1] >= seatGrid[0].size) {
            return false
        }

        return true
    }

    private fun getAdjacentSeats(i: Int, j: Int, seatGrid: Array<CharArray>): List<Char> {
        val adjacentSeatCoordinateGrid = directionGrid
            .map { arrayOf(i + it[0], j + it[1]) }

        return adjacentSeatCoordinateGrid
            .filter { areSeatCoordinatesValid(it, seatGrid) }
            .map { seatGrid[it[0]][it[1]] }
    }

    private fun getNewSeatStatePart1(i: Int, j: Int, seatGrid: Array<CharArray>): Char {
        val seat = seatGrid[i][j]
        val adjacentSeats = getAdjacentSeats(i, j, seatGrid)

        val occupiedSeats = adjacentSeats.filter { it == occupied }.count()

        if (seat == empty && occupiedSeats == 0) {
            return occupied
        }

        if (seat == occupied && occupiedSeats >= 4) {
            return empty
        }

        return seat
    }

    private fun getNewSeatGridPart1(seatGrid: Array<CharArray>): Array<CharArray> {
        val newSeatGrid = seatGrid.copy()

        for (i in seatGrid.indices) {
            for (j in seatGrid[0].indices) {
                val newSeatState = getNewSeatStatePart1(i, j, seatGrid)
                newSeatGrid[i][j] = newSeatState
            }
        }

        return newSeatGrid
    }

    override fun runPart1(inputFile: File): String {
        var seatGrid = read2dCharArray(inputFile)

        while (true) {
            val newSeatGrid = getNewSeatGridPart1(seatGrid)

            if (seatGrid.contentDeepEquals(newSeatGrid)) {
                break
            }

            seatGrid = newSeatGrid
        }

        return countOccupiedSeats(seatGrid).toString()
    }

    private fun getVisibleSeats(i: Int, j: Int, seatGrid: Array<CharArray>): List<Char> {
        val visibleSeatGrid = arrayListOf<Char>()

        for (direction in directionGrid) {
            var x = i
            var y = j

            while (true) {
                x += direction[0]
                y += direction[1]

                if (!areSeatCoordinatesValid(arrayOf(x, y), seatGrid)) {
                    break
                }

                if (seatGrid[x][y] != floor) {
                    visibleSeatGrid.add(seatGrid[x][y])
                    break
                }
            }
        }

        return visibleSeatGrid
    }

    private fun getNewSeatStatePart2(i: Int, j: Int, seatGrid: Array<CharArray>): Char {
        val seat = seatGrid[i][j]
        val visibleSeats = getVisibleSeats(i, j, seatGrid)

        val occupiedSeats = visibleSeats.filter { it == occupied }.count()

        if (seat == empty && occupiedSeats == 0) {
            return occupied
        }

        if (seat == occupied && occupiedSeats >= 5) {
            return empty
        }

        return seat
    }

    private fun getNewSeatGridPart2(seatGrid: Array<CharArray>): Array<CharArray> {
        val newSeatGrid = seatGrid.copy()

        for (i in seatGrid.indices) {
            for (j in seatGrid[0].indices) {
                val newSeatState = getNewSeatStatePart2(i, j, seatGrid)
                newSeatGrid[i][j] = newSeatState
            }
        }

        return newSeatGrid
    }

    override fun runPart2(inputFile: File): String {
        var seatGrid = read2dCharArray(inputFile)

        while (true) {
            val newSeatGrid = getNewSeatGridPart2(seatGrid)

            if (seatGrid.contentDeepEquals(newSeatGrid)) {
                break
            }

            seatGrid = newSeatGrid
        }

        return countOccupiedSeats(seatGrid).toString()
    }
}
