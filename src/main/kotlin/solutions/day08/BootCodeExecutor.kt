package solutions.day08

import org.apache.commons.lang3.StringUtils

object BootCodeExecutor {
    data class Instruction(
        val operation: String,
        val argument: Int,
        var executed: Boolean
    )

    fun parseInstructionText(instructionText: String): Instruction {
        val components = instructionText.split(" ")
        return Instruction(
            operation = components[0],
            argument = components[1].toInt(),
            executed = false
        )
    }

    fun execute(instructionsText: List<String>): Int {
        val instructions = instructionsText
            .filter { StringUtils.isNotBlank(it) }
            .map { parseInstructionText(it) }

        return executeInstructions(instructions)
    }

    private fun executeInstructions(instructions: List<Instruction>): Int {
        var accumulator: Int = 0
        var operationIndex: Int = 0
        while (operationIndex < instructions.size) {
            val instruction = instructions[operationIndex]

            // Break out of infinite loops
            if (instruction.executed) {
                break;
            }

            when (instruction.operation) {
                "acc" -> accumulator += instruction.argument
                "jmp" -> operationIndex += instruction.argument - 1
                // "nop" -> Do Nothing
            }
            instruction.executed = true
            operationIndex++
        }

        return accumulator
    }
}
