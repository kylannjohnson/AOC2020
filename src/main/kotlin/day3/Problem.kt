package day3

import util.getResourceAsText

fun main() {
    val input = listOf(
        "..##.......",
        "#...#...#..",
        ".#....#..#.",
        "..#.#...#.#",
        ".#...##..#.",
        "..#.##.....",
        ".#.#.#....#",
        ".#........#",
        "#.##...#...",
        "#...##....#",
        ".#..#...#.#",
    )

    val input2 = getResourceAsText("data3.txt")

    val map = input2.parse()
        .multiply(105).also { it.forEach(::println) }

    val points = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
    val points1 = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)

    val count = points1.map { pair ->
        map.traverseAndCount(pair.first, pair.second)
    }.fold(1) { acc, i -> acc * i }

    println("Count $count")
}

private fun List<String>.parse(): List<List<Space>> {
    return map { it.toList().map { char -> if (char.toString() == "#") Space.TREE else Space.OPEN } }
}

private fun List<List<Space>>.traverseAndCount(right: Int, down: Int): Int {
    var count = 0

    var coord = 0 to 0

    val height = this.size

    while (coord.first < height) {
        println(coord)
        if (this[coord.first][coord.second] == Space.TREE) {
            count++
        }

        coord = coord.first + down to coord.second + right
    }

    return count
}

private fun List<List<Space>>.multiply(i: Int): List<List<Space>> {
    return map { row ->
        mutableListOf<Space>().apply {
            repeat(i) { this@apply.addAll(row) }
        }
    }
}

enum class Space { OPEN, TREE }