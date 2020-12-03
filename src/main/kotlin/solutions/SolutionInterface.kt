package solutions

import java.io.File

interface SolutionInterface {
    fun runPart1(inputFile: File): String
    fun runPart2(inputFile: File): String

    fun run(inputFile: File, part: Int): String {
        return if (part == 1) runPart1(inputFile) else runPart2(inputFile)
    }
}
