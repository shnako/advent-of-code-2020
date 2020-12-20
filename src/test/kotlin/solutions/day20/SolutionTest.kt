package solutions.day20

import GenericSolutionTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SolutionTest : GenericSolutionTest(Solution()) {
    override val expectedPart1Result = "140656720229539"
    override val expectedPart2Result = "1885"

    private val classUnderTest = Solution()

    // 1 0 1 1 0
    // 1 1 0 0 1
    // 1 1 1 0 0
    // 1 0 1 0 1
    // 0 1 0 0 0
    private val tile = Tile(
        1,
        listOf(1, 0, 1, 1, 0),
        listOf(0, 1, 0, 0, 0),
        listOf(1, 1, 1, 1, 0),
        listOf(0, 1, 0, 1, 0),
        arrayOf(
            intArrayOf(1, 0, 0),
            intArrayOf(1, 1, 0),
            intArrayOf(0, 1, 0),
        )
    )

    // 0 1 0 1 0
    // 1 0 0 0 0
    // 1 0 1 1 0
    // 0 1 1 0 1
    // 1 1 1 1 0
    private val rotatedTile = Tile(
        1,
        listOf(0, 1, 0, 1, 0),
        listOf(1, 1, 1, 1, 0),
        listOf(0, 1, 1, 0, 1),
        listOf(0, 0, 0, 1, 0),
        arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(0, 1, 1),
            intArrayOf(1, 1, 0),
        )
    )

    @Test
    fun rotateTileWithoutContentTest() {
        classUnderTest.rotateTile(tile, false)

        assertEquals(rotatedTile.edgeTop, tile.edgeTop)
        assertEquals(rotatedTile.edgeBottom, tile.edgeBottom)
        assertEquals(rotatedTile.edgeLeft, tile.edgeLeft)
        assertEquals(rotatedTile.edgeRight, tile.edgeRight)
    }

    @Test
    fun rotateTileAndContentTest() {
        classUnderTest.rotateTile(tile, true)

        assertEquals(rotatedTile.edgeTop, tile.edgeTop)
        assertEquals(rotatedTile.edgeBottom, tile.edgeBottom)
        assertEquals(rotatedTile.edgeLeft, tile.edgeLeft)
        assertEquals(rotatedTile.edgeRight, tile.edgeRight)
        assertTrue(rotatedTile.content.contentDeepEquals(tile.content))
    }

    // 0 1 1 0 1
    // 1 0 0 1 1
    // 0 0 1 1 1
    // 1 0 1 0 1
    // 0 0 0 1 0
    private val flippedTile = Tile(
        1,
        listOf(0, 1, 1, 0, 1),
        listOf(0, 0, 0, 1, 0),
        listOf(0, 1, 0, 1, 0),
        listOf(1, 1, 1, 1, 0),
        arrayOf(
            intArrayOf(0, 0, 1),
            intArrayOf(0, 1, 1),
            intArrayOf(0, 1, 0),
        )
    )

    @Test
    fun flipTileWithoutContentTest() {
        classUnderTest.flipTile(tile, false)

        assertEquals(flippedTile.edgeTop, tile.edgeTop)
        assertEquals(flippedTile.edgeBottom, tile.edgeBottom)
        assertEquals(flippedTile.edgeLeft, tile.edgeLeft)
        assertEquals(flippedTile.edgeRight, tile.edgeRight)
    }

    @Test
    fun flipTileAndContentTest() {
        classUnderTest.flipTile(tile, true)

        assertEquals(flippedTile.edgeTop, tile.edgeTop)
        assertEquals(flippedTile.edgeBottom, tile.edgeBottom)
        assertEquals(flippedTile.edgeLeft, tile.edgeLeft)
        assertEquals(flippedTile.edgeRight, tile.edgeRight)
        assertTrue(flippedTile.content.contentDeepEquals(tile.content))
    }
}
