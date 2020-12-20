package util

fun binaryToLong(binary: List<Int>): Long {
    var multiplier = 1L
    var number = 0L
    for (i in binary.count() - 1 downTo 0) {
        if (binary[i] == 1) {
            number += multiplier
        }
        multiplier *= 2
    }
    return number
}

fun longToBinary(number: Long, bits: Int): MutableList<Int> {
    val numberInBinary = number.toString(2).toCharArray().map { it.toInt() - 48 }

    val correctlySizedNumberInBinary = mutableListOf<Int>()
    for (i in numberInBinary.size until bits) {
        correctlySizedNumberInBinary.add(0)
    }
    correctlySizedNumberInBinary.addAll(numberInBinary)

    return correctlySizedNumberInBinary.toMutableList()
}

