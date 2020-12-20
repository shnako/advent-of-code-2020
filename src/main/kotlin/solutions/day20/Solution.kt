package solutions.day20

import org.apache.commons.lang3.StringUtils
import solutions.GenericSolution
import util.binaryToLong
import util.readFileAsString
import java.io.File

data class Tile(
    val id: Int,
    var edgeTop: List<Int>,
    var edgeBottom: List<Int>,
    var edgeLeft: List<Int>,
    var edgeRight: List<Int>,
    val edgeNumbers: MutableSet<Int> = mutableSetOf(),
    val matchingNeighbourTiles: MutableSet<Tile> = mutableSetOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        return id == (other as Tile).id
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "$id:\n" +
                "Top: $edgeTop\n" +
                "Bottom: $edgeBottom\n" +
                "Left: $edgeLeft\n" +
                "Right: $edgeRight\n" +
                "Numbers: $edgeNumbers\n" +
                "Neighbours: ${matchingNeighbourTiles.count()}"
    }
}

class Solution : GenericSolution {
    private fun extractEdge(line: String): List<Int> {
        return line
            .toCharArray()
            .map { if (it == '#') 1 else 0 }
    }

    private fun parseTile(tileText: String): Tile {
        var components = tileText
            .split("\n")
            .filter { StringUtils.isNotBlank(it) }

        val id = components[0]
            .removePrefix("Tile ")
            .removeSuffix(":")
            .toInt()

        components = components.subList(1, components.size)
        return Tile(
            id,
            extractEdge(components.first()),
            extractEdge(components.last()),
            extractEdge(components.map { it.first() }.joinToString("")),
            extractEdge(components.map { it.last() }.joinToString("")),
        )
    }

    private fun parseTiles(inputFile: File): List<Tile> {
        return readFileAsString(inputFile)
            .split("\n\n")
            .filter { StringUtils.isNotBlank(it) }
            .map { parseTile(it) }
    }

    private fun rotateTile(tile: Tile) {
        val tempTop = tile.edgeTop
        tile.edgeTop = tile.edgeRight
        tile.edgeRight = tile.edgeBottom.reversed()
        tile.edgeBottom = tile.edgeLeft
        tile.edgeLeft = tempTop.reversed()
    }

    private fun flipTile(tile: Tile) {
        tile.edgeTop = tile.edgeTop.reversed()
        tile.edgeBottom = tile.edgeBottom.reversed()
        val tempLeft = tile.edgeLeft
        tile.edgeLeft = tile.edgeRight
        tile.edgeRight = tempLeft
    }

    private fun determineTileNumbers(tile: Tile) {
        tile.edgeNumbers.add(binaryToLong(tile.edgeTop).toInt())
        tile.edgeNumbers.add(binaryToLong(tile.edgeBottom).toInt())
        tile.edgeNumbers.add(binaryToLong(tile.edgeLeft).toInt())
        tile.edgeNumbers.add(binaryToLong(tile.edgeRight).toInt())
    }

    private fun linkMatchingNeighbourTiles(tile: Tile, tiles: List<Tile>) {
        for (candidateTile in tiles) {
            if (tile == candidateTile) {
                continue
            }

            if (tile.edgeNumbers.intersect(candidateTile.edgeNumbers).isNotEmpty()) {
                tile.matchingNeighbourTiles.add(candidateTile)
            }
        }
    }

    private fun determineTileNumbersInAllOrientations(tile: Tile) {
        for (flip in 0 until 2) {
            for (rotation in 0 until 4) {
                determineTileNumbers(tile)
                rotateTile(tile)
            }
            flipTile(tile)
        }
    }

    override fun runPart1(inputFile: File): String {
        var tiles = parseTiles(inputFile)
        tiles.forEach {
            determineTileNumbersInAllOrientations(it)
        }

        tiles.forEach { linkMatchingNeighbourTiles(it, tiles) }
        tiles = tiles.sortedBy { it.matchingNeighbourTiles.count() }

        var result = 1L
        tiles
            .filter { it.matchingNeighbourTiles.count() == 2 }
            .forEach { result *= it.id }

        return result.toString()
    }

    override fun runPart2(inputFile: File): String {
        TODO("Not yet implemented")
    }
}
