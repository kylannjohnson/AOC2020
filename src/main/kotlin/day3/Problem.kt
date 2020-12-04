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
        .multiply(35).also { it.forEach(::println) }

    val count = map.traverseAndCount(3, 1)

    println("Count $count")
}

private fun List<String>.parse(): List<List<Space>> {
    return map { it.toList().map { char -> if (char.toString() == "#") Space.TREE else Space.OPEN } }
}

private fun List<List<Space>>.traverseAndCount(right: Int, down: Int): Int {
    var count = 0

    var coord = 0 to 0

    val height = this.size

    while(coord.first < height) {
        println(coord)
        if(this[coord.first][coord.second] == Space.TREE) {
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