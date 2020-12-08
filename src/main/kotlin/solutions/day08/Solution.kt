package solutions.day08

import solutions.GenericSolution
import util.readLines
import java.io.File

/**
 * Nothing special in the first part - just executing the instructions
 * until we find one we've already executed and then return the accumulator.
 *
 * For the second part we know that there's exactly 1 instruction
 * that needs to be flipped to fix the infinite loop.
 * Because the dataset is small we simply flip each jmp/nop in the instruction list
 * until we no longer find an infinite loop and then calculate.
 *
 * If performance needs to be improved,
 * we could execute the instructions and keep a list of what we executed.
 * Once we hit an instruction that was executed, we can work our way backwards
 * flipping until the program terminates correctly.
 */
class Solution : GenericSolution {
    override fun runPart1(inputFile: File): String {
        val instructionsText = readLines(inputFile)
        val bootCodeExecutor = BootCodeExecutor(instructionsText)
        return bootCodeExecutor.execute().toString()
    }

    private fun flipInstructionOperation(instruction: BootCodeExecutor.Instruction) {
        when (instruction.operation) {
            "nop" -> instruction.operation = "jmp"
            "jmp" -> instruction.operation = "nop"
        }
    }

    override fun runPart2(inputFile: File): String {
        val instructionsText = readLines(inputFile)
        var bootCodeExecutor = BootCodeExecutor(instructionsText)
        val instructions = bootCodeExecutor.instructions

        for (instruction in instructions) {
            if (instruction.operation == "jmp" || instruction.operation == "nop") {
                flipInstructionOperation(instruction)

                bootCodeExecutor = BootCodeExecutor(instructions)

                if (!bootCodeExecutor.hasInfiniteLoop()) {
                    return bootCodeExecutor.execute().toString()
                }

                flipInstructionOperation(instruction)
            }
        }

        return "not found"
    }
}
