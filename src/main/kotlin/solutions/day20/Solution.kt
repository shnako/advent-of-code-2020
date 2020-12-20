package solutions.day20

import org.apache.commons.lang3.StringUtils
import solutions.GenericSolution
import util.binaryToLong
import util.readFileAsString
import java.io.File
import kotlin.math.sqrt

data class Tile(
    val id: Int,
    var edgeTop: List<Int>,
    var edgeBottom: List<Int>,
    var edgeLeft: List<Int>,
    var edgeRight: List<Int>,
    var content: Array<IntArray>,
    val edgeNumbers: MutableSet<Int> = mutableSetOf(),
    val matchingNeighbourTiles: MutableSet<Tile> = mutableSetOf(),
    var placed: Boolean = false,
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
                "Neighbours: ${matchingNeighbourTiles.count()}\n" +
                "Content:\n${content.joinToString("\n") { it.map { x -> x }.joinToString("") }}"
    }
}

@SuppressWarnings("kotlin:S3776")
/**
 * The input is parsed with # stored as 1 and . stored as 0.
 * This allows us to treat the edges of each tile as a 10 bit binary representation of a number.
 * In order to compare 2 tile edges for compatibility, all I need to do is check that these numbers are identical,
 * thus avoiding a one by one comparison of each edge element.
 *
 * First I determine all the numbers for all edges of all tiles, after flipping and rotating each tile in all 8 positions and store them in edgeNumbers.
 *
 * Then for each tile I look for other tiles that have at least 1 number in common with this tile.
 * These tiles are compatible and can be arranged as neighbours so a link from one to the other is stored in matchingNeighbourTiles.
 *
 * Given the existing input, the 4 corner tiles will have 2 neighbours, the tiles on the edges of the image will have 3 and the rest 4.
 *
 * Part 1:
 * The solution is to find the tiles with 2 neighbours and multiply their ids.
 *
 * Part 2:
 * In order to find the solution for this, we first need to reconstruct the image.
 * We know that we can flip and rotate the image later so it doesn't matter how we orient it as long as it's correct.
 * First I take one of the corner tiles and place it at [0, 0], ensuring that it is flipped and rotated correctly.
 * Then I fill the first line of the image, rotating and flipping each tile to fit the tile to its left.
 * Then I fill the rest of the image line by line, rotating and flipping each tile to fit the tile above it and the tile to its left.
 *
 * Once this is done I have the tiles arranged and oriented correctly so I can extract the image.
 *
 * I then flip and rotate the image looking for monsters starting from each position in the image.
 *
 * I know I've got the right orientation when I find at least a monster.
 *
 * I count the number of monsters in the correct orientation and the result is:
 * <number of 1s in image> - <number of monsters in image> * <number of 1s in each monster (15)>
 */
class Solution : GenericSolution {
    private fun extractLine(line: String): List<Int> {
        return line
            .toCharArray()
            .map { if (it == '#') 1 else 0 }
    }

    private fun extractContent(tileLines: List<String>): Array<IntArray> {
        val result = Array(tileLines.size - 2) { IntArray(tileLines.size - 2) { -1 } }
        for (i in 1 until tileLines.size - 1) {
            val line = extractLine(tileLines[i])
            result[i - 1] = line.subList(1, line.size - 1).toIntArray()
        }
        return result
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
            extractLine(components.first()),
            extractLine(components.last()),
            extractLine(components.map { it.first() }.joinToString("")),
            extractLine(components.map { it.last() }.joinToString("")),
            extractContent(components),
        )
    }

    private fun parseTiles(inputFile: File): List<Tile> {
        return readFileAsString(inputFile)
            .split("\n\n")
            .filter { StringUtils.isNotBlank(it) }
            .map { parseTile(it) }
    }

    fun rotateTile(tile: Tile, rotateContent: Boolean) {
        val tempTop = tile.edgeTop
        tile.edgeTop = tile.edgeRight
        tile.edgeRight = tile.edgeBottom.reversed()
        tile.edgeBottom = tile.edgeLeft
        tile.edgeLeft = tempTop.reversed()

        if (rotateContent) {
            tile.content = rotateContent(tile.content)
        }
    }

    private fun rotateContent(content: Array<IntArray>): Array<IntArray> {
        val newContent = Array(content.size) { IntArray(content.size) { -1 } }
        for (i in content.indices) {
            for (j in content[i].indices) {
                newContent[content.size - j - 1][i] = content[i][j]
            }
        }
        return newContent
    }

    private fun flipContent(content: Array<IntArray>): Array<IntArray> {
        val newContent = Array(content.size) { IntArray(content.size) { -1 } }
        for (i in content.indices) {
            for (j in content[i].indices) {
                newContent[i][content.size - j - 1] = content[i][j]
            }
        }
        return newContent
    }

    fun flipTile(tile: Tile, flipContent: Boolean) {
        tile.edgeTop = tile.edgeTop.reversed()
        tile.edgeBottom = tile.edgeBottom.reversed()
        val tempLeft = tile.edgeLeft
        tile.edgeLeft = tile.edgeRight
        tile.edgeRight = tempLeft

        if (flipContent) {
            tile.content = flipContent(tile.content)
        }
    }

    private fun determineTileEdgeNumbers(tile: Tile) {
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

    private fun determineTileEdgeNumbersInAllOrientations(tile: Tile) {
        for (flip in 1..2) {
            for (rotation in 1..4) {
                determineTileEdgeNumbers(tile)
                rotateTile(tile, false)
            }
            flipTile(tile, false)
        }
    }

    override fun runPart1(inputFile: File): String {
        val tiles = parseTiles(inputFile)
        tiles.forEach {
            determineTileEdgeNumbersInAllOrientations(it)
        }

        tiles.forEach { linkMatchingNeighbourTiles(it, tiles) }

        var result = 1L
        tiles
            .filter { it.matchingNeighbourTiles.count() == 2 }
            .forEach { result *= it.id }

        return result.toString()
    }

    private fun placeFirstTile(tiles: List<Tile>, arrangement: Array<Array<Tile?>>) {
        val firstTile = tiles.first { tile -> tile.matchingNeighbourTiles.count() == 2 }

        var correctlyRotated = false
        for (flip in 1..2) {
            for (rotation in 1..4) {
                val rightNumber = binaryToLong(firstTile.edgeRight).toInt()
                val bottomNumber = binaryToLong(firstTile.edgeBottom).toInt()

                if (firstTile.matchingNeighbourTiles.first().edgeNumbers.contains(rightNumber)
                    && firstTile.matchingNeighbourTiles.last().edgeNumbers.contains(bottomNumber)
                ) {
                    correctlyRotated = true
                    break
                }

                rotateTile(firstTile, true)
            }
            if (correctlyRotated) {
                break
            }
            flipTile(firstTile, true)
        }

        arrangement[0][0] = firstTile
        firstTile.placed = true
    }

    private fun placeFirstLineOfTiles(arrangement: Array<Array<Tile?>>) {
        for (i in 1 until arrangement.size) {
            val previousTile = arrangement[0][i - 1]
            val previousTileRightEdgeNumber = binaryToLong(previousTile!!.edgeRight).toInt()

            val thisTile =
                previousTile.matchingNeighbourTiles.first {
                    !it.placed && it.edgeNumbers.contains(previousTileRightEdgeNumber)
                }

            var correctlyRotated = false
            for (flip in 1..2) {
                for (rotation in 1..4) {
                    val leftNumber = binaryToLong(thisTile.edgeLeft).toInt()

                    if (leftNumber == previousTileRightEdgeNumber) {
                        correctlyRotated = true
                        break
                    }

                    rotateTile(thisTile, true)
                }
                if (correctlyRotated) {
                    break
                }
                flipTile(thisTile, true)
            }

            if (!correctlyRotated) {
                throw Exception("Could not figure out how to place tile. $thisTile")
            }

            arrangement[0][i] = thisTile
            thisTile.placed = true
        }
    }

    private fun placeLineOfTiles(line: Int, arrangement: Array<Array<Tile?>>) {
        for (i in arrangement.indices) {
            val topTile = arrangement[line - 1][i]!!
            val topTileBottomNumber = binaryToLong(topTile.edgeBottom).toInt()
            val thisTile = topTile.matchingNeighbourTiles.first { !it.placed }

            val leftTile = if (i > 0) arrangement[line][i - 1] else null
            val leftTileRightNumber = if (leftTile == null) -1 else binaryToLong(leftTile.edgeRight).toInt()

            var correctlyRotated = false
            for (flip in 1..2) {
                for (rotation in 1..4) {
                    val topNumber = binaryToLong(thisTile.edgeTop).toInt()
                    val leftNumber = binaryToLong(thisTile.edgeLeft).toInt()

                    if (topTileBottomNumber == topNumber
                        && (leftTileRightNumber == -1 || leftTileRightNumber == leftNumber)
                    ) {
                        correctlyRotated = true
                        break
                    }

                    rotateTile(thisTile, true)
                }
                if (correctlyRotated) {
                    break
                }
                flipTile(thisTile, true)
            }

            if (!correctlyRotated) {
                throw Exception("Could not figure out how to place tile. $thisTile")
            }

            arrangement[line][i] = thisTile
            thisTile.placed = true
        }
    }

    private fun arrangeTiles(tiles: List<Tile>): Array<Array<Tile?>> {
        val lines = sqrt(tiles.size.toDouble()).toInt()
        val arrangement = Array(lines) { Array<Tile?>(lines) { null } }

        placeFirstTile(tiles, arrangement)
        placeFirstLineOfTiles(arrangement)
        for (line in 1 until lines) {
            placeLineOfTiles(line, arrangement)
        }

        return arrangement
    }

    private fun buildImage(arrangement: Array<Array<Tile?>>): Array<IntArray> {
        val contentSize = arrangement[0][0]!!.content.size
        val imageSize = arrangement.size * contentSize
        val image = Array(imageSize) { IntArray(imageSize) { -1 } }

        for (tileLine in arrangement.indices) {
            for (tileColumn in arrangement[tileLine].indices) {
                val tile = arrangement[tileLine][tileColumn]!!
                for (i in tile.content.indices) {
                    for (j in tile.content[i].indices) {
                        image[tileLine * contentSize + i][tileColumn * contentSize + j] = tile.content[i][j]
                    }
                }
            }
        }

        return image
    }

    private fun isMonster(x: Int, y: Int, image: Array<IntArray>): Boolean {
        for (monsterComponent in monsterComponents) {
            if (image[x + monsterComponent[0]][y + monsterComponent[1]] != 1) {
                return false
            }
        }
        return true
    }

    private fun findMonsters(image: Array<IntArray>): Int {
        val monsterHeight = 3
        val monsterWidth = 20

        var monsters = 0
        for (i in 0..image.size - monsterHeight) {
            for (j in 0..image.size - monsterWidth) {
                if (isMonster(i, j, image)) {
                    monsters++
                    println("Found monster $monsters at $i, $j.")
                }
            }
        }
        println()
        return monsters
    }

    private val monsterComponents = arrayListOf(
        arrayOf(0, 18),
        arrayOf(1, 0),
        arrayOf(1, 5),
        arrayOf(1, 6),
        arrayOf(1, 11),
        arrayOf(1, 12),
        arrayOf(1, 17),
        arrayOf(1, 18),
        arrayOf(1, 19),
        arrayOf(2, 1),
        arrayOf(2, 4),
        arrayOf(2, 7),
        arrayOf(2, 10),
        arrayOf(2, 13),
        arrayOf(2, 16),
    )

    override fun runPart2(inputFile: File): String {
        val tiles = parseTiles(inputFile)
        tiles.forEach {
            determineTileEdgeNumbersInAllOrientations(it)
        }

        tiles.forEach { linkMatchingNeighbourTiles(it, tiles) }

        val arrangement = arrangeTiles(tiles)
        var image = buildImage(arrangement)
        val totalHashes = image.map { line -> line.count { it == 1 } }.sum()

        for (flip in 1..2) {
            for (rotation in 1..4) {
                image.forEach { println(it.joinToString("")) }
                val monsters = findMonsters(image)
                if (monsters > 0) {
                    return (totalHashes - 15 * monsters).toString()
                }

                image = rotateContent(image)
            }
            image = flipContent(image)
        }

        return "Monsters not found."
    }
}
