package day1

import util.getResourceAsText


fun main() {
    val num = listOf(
        1721,
        979,
        366,
        299,
        675,
        1456
    )

    val target = 2020
    val nums = getResourceAsText("data1.txt").map { it.toInt() }
    println(pairOfTarget(nums, target))
}


fun pairOfTarget(num: List<Int>, target: Int): Int {
    val (first, second) = num.map { it to (target - it) }
        .filter { it.second in num }
        .map { it.first }

    return first * second
}