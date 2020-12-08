package solutions.day08

import solutions.GenericSolution
import util.readLines
import java.io.File

class Solution : GenericSolution {
    override fun runPart1(inputFile: File): String {
        val instructions = readLines(inputFile)
        return BootCodeExecutor.execute(instructions).toString()
    }

    override fun runPart2(inputFile: File): String {
        TODO("Not yet implemented")
    }
}
