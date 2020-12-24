package solutions.day24

import org.apache.commons.lang3.tuple.ImmutableTriple
import org.apache.commons.lang3.tuple.MutableTriple
import solutions.GenericSolution
import util.readLines
import java.io.File

/**
 * This solution relies on the implementation of the grid as cube coordinates.
 * https://www.redblobgames.com/grids/hexagons/#coordinates-cube
 *
 * Part 1:
 * Navigate to get the coordinates.
 * Store all black tile coordinates as Triple objects in a set.
 * If the current coordinates are already in the set (already black) remove them (now white).
 * Otherwise add to the set (now black).
 * The result is the size of the set.
 *
 * Part 2:
 * Do the above to find the arrangement on day 0.
 * For each day, get all neighbours of the black tiles and the neighbours of their neighbours
 * as these are the only ones that could change and apply the rules.
 * The result is the size of the set after 100 days.
 */
class SolutionCube : GenericSolution {
    private val coordinates = mapOf(
        Pair("w", ImmutableTriple(-1, +1, +0)),
        Pair("e", ImmutableTriple(+1, -1, +0)),
        Pair("sw", ImmutableTriple(-1, +0, +1)),
        Pair("se", ImmutableTriple(+0, -1, +1)),
        Pair("nw", ImmutableTriple(+0, +1, -1)),
        Pair("ne", ImmutableTriple(+1, +0, -1)),
    )

    private fun move(
        direction: String,
        currentCoordinates: MutableTriple<Int, Int, Int>
    ): MutableTriple<Int, Int, Int> {
        val directionCoordinates = coordinates[direction]!!

        currentCoordinates.left += directionCoordinates.left
        currentCoordinates.middle += directionCoordinates.middle
        currentCoordinates.right += directionCoordinates.right

        return currentCoordinates
    }

    private fun populateBlackTiles(tileCoordinates: Collection<String>): MutableSet<MutableTriple<Int, Int, Int>> {
        val blackTiles = mutableSetOf<MutableTriple<Int, Int, Int>>()
        for (tileCoordinate in tileCoordinates) {
            val tile = MutableTriple(0, 0, 0)
            var i = 0
            while (i < tileCoordinate.length) {
                val direction: String
                if (tileCoordinate[i] == 'w' || tileCoordinate[i] == 'e') {
                    direction = tileCoordinate[i].toString()
                    i++
                } else {
                    direction = tileCoordinate.substring(i, i + 2)
                    i += 2
                }
                move(direction, tile)
            }

            if (blackTiles.contains(tile)) {
                blackTiles.remove(tile)
            } else {
                blackTiles.add(tile)
            }
        }
        return blackTiles
    }

    override fun runPart1(inputFile: File): String {
        val tileCoordinates = readLines(inputFile)

        val blackTiles = populateBlackTiles(tileCoordinates)

        return blackTiles.size.toString()
    }

    private fun getNeighbourTiles(tile: MutableTriple<Int, Int, Int>): List<MutableTriple<Int, Int, Int>> {
        return coordinates
            .map { move(it.key, MutableTriple.of(tile.left, tile.middle, tile.right)) }
            .toList()
    }

    private fun flipDaily(blackTiles: Set<MutableTriple<Int, Int, Int>>): MutableSet<MutableTriple<Int, Int, Int>> {
        val newBlackTiles = mutableSetOf<MutableTriple<Int, Int, Int>>()

        //region Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
        val tileToNeighboursMap = blackTiles
            .associateBy({ it }, { getNeighbourTiles(it) })

        for (blackTile in blackTiles) {
            val blackNeighbourTiles = tileToNeighboursMap[blackTile]!!
                .count { blackTiles.contains(it) }
            if (blackNeighbourTiles == 1 || blackNeighbourTiles == 2) {
                newBlackTiles.add(blackTile)
            }
        }
        // endregion

        // region Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
        val neighboursOfNeighbourTiles = tileToNeighboursMap.values
            .flatten()
            .associateBy({ it }, { getNeighbourTiles(it) })

        for (tile in neighboursOfNeighbourTiles.keys) {
            // Only care about white tiles
            if (blackTiles.contains(tile)) {
                continue
            }

            val blackNeighbourTiles = neighboursOfNeighbourTiles[tile]!!
                .count { blackTiles.contains(it) }
            if (blackNeighbourTiles == 2) {
                newBlackTiles.add(tile)
            }
        }
        // endregion

        return newBlackTiles
    }

    override fun runPart2(inputFile: File): String {
        val tileCoordinates = readLines(inputFile)

        var blackTiles = populateBlackTiles(tileCoordinates)

        for (day in 1..100) {
            blackTiles = flipDaily(blackTiles)
            println("After day $day there are ${blackTiles.size} black tiles.")
        }

        return blackTiles.size.toString()
    }
}
