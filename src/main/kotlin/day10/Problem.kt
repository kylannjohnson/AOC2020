package day10

import util.getResourceAsText

fun main() {
    val example = listOf(
        "16",
        "10",
        "15",
        "5",
        "1",
        "11",
        "7",
        "19",
        "6",
        "12",
        "4",
    )

    val example2 = listOf(
        "28",
        "33",
        "18",
        "42",
        "31",
        "14",
        "46",
        "20",
        "48",
        "47",
        "24",
        "23",
        "49",
        "45",
        "19",
        "38",
        "39",
        "11",
        "1",
        "32",
        "25",
        "35",
        "8",
        "17",
        "7",
        "9",
        "4",
        "2",
        "34",
        "10",
        "3",
    )

    val input = getResourceAsText("data10.txt").mapNotNull { it.toIntOrNull() }.sorted()

    val chain = mutableListOf<Int>()
    val remaining = input.toMutableList()

    var outputJolt = 0

    while (remaining.size > 0) {
        outputJolt = remaining.first { it - outputJolt <= 3 }

        chain.add(outputJolt).also { remaining.remove(outputJolt) }
    }

    // add 3 for the final head room
    chain.add(chain.last() + 3)

    val diffs = chain.mapIndexed { index, i ->
        if (index > 0) i - chain[index - 1]
        else 1
    }

    val ones = diffs.count { it == 1 }
    val threes = diffs.count { it == 3 }
    println("1's: $ones   3's: $threes  =   ${ones * threes}")
}