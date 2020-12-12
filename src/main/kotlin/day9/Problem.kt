package day9

import util.getResourceAsText

fun main() {
    val preambleLength = 25

    val input = listOf(
        "35",
        "20",
        "15",
        "25",
        "47",
        "40",
        "62",
        "55",
        "65",
        "95",
        "102",
        "117",
        "150",
        "182",
        "127",
        "219",
        "299",
        "277",
        "309",
        "576",
    )

    val inputN = getResourceAsText("data9.txt")
        .mapNotNull { it.toIntOrNull() }

    val result = inputN
        .filterIndexed { index, n ->
            if (index < preambleLength) false
            else {
                val pre = inputN.subList(index - preambleLength, index)
                val diff = pre.map { n - it }

                !diff.any { pre.contains(it) }
            }
        }

    println(result.first())
}