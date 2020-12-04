import solutions.GenericSolution
import util.getInputFileForDay

fun main(args: Array<String>) {
    println("Welcome to Vlad's solutions to Advent of Code 2020!")

    val day = args[0].toInt()
    if (day < 1 || day > 25) {
        throw Exception("Day argument must be a number between 1 and 25.")
    }

    val part = args[1].toInt()
    if (part != 1 && part != 2) {
        throw Exception("Part argument must be either 1 or 2.")
    }

    run(day, part)
}

fun run(day: Int, part: Int) {
    val dayStr = if (day < 10) "0$day" else day.toString()
    val input = getInputFileForDay(day)

    println("Running day $day part $part")
    val solution: GenericSolution = Class.forName("solutions.day$dayStr.Solution").kotlin.java.getDeclaredConstructor().newInstance() as GenericSolution
    val result = solution.run(input, part)

    println("Result:")
    println(result)
}
