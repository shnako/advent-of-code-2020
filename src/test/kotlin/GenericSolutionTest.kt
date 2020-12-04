import org.junit.jupiter.api.Test
import solutions.GenericSolution
import util.getInputFileForDay
import kotlin.test.assertEquals

abstract class GenericSolutionTest(private val solution: GenericSolution) {
    abstract val expectedPart1Result: String
    abstract val expectedPart2Result: String

    private val day = solution.javaClass.`package`.name.split('.').last()
    private val inputFile = getInputFileForDay(day)

    @Test
    fun part1Test() {
        val part1Result = solution.runPart1(inputFile)
        assertEquals(expectedPart1Result, part1Result)
    }

    @Test
    fun part2Test() {
        val part2Result = solution.runPart2(inputFile)
        assertEquals(expectedPart2Result, part2Result)
    }
}
