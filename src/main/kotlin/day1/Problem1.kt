package day1

import util.getResourceAsText

val num = listOf(
    1721,
    979,
    366,
    299,
    675,
    1456
)

fun main() {

    val target = 2020
    val nums = getResourceAsText("data1.txt").map { it.toInt() }
//    println(pairOfTarget(nums, target))
    val triples = mutableListOf<List<Int>>()
    tripleOfTarget(nums, 3, mutableListOf(), triples)

    val (f, s, t) = triples
        .map { it to it.sum() }
        .first { it.second == target }
        .first

    println("${f * s * t}")
}


fun pairOfTarget(num: List<Int>, target: Int): Int {
    val (first, second) = num.map { it to (target - it) }
        .filter { it.second in num }
        .map { it.first }

    return first * second
}

// https://hmkcode.com/calculate-find-all-possible-combinations-of-an-array-using-java/
fun tripleOfTarget(num: List<Int>, numberOfSpacesLeft: Int, group: List<Int>, triples: MutableList<List<Int>>) {


    // if num.length < k then stop
    if (num.size < numberOfSpacesLeft)
        return

    //  if k == 1 then add each element to the accumulated combination
    if (numberOfSpacesLeft == 1) {
        num.forEach { triples.add(group + it) }
    }
    //  if num.length == k then add all elements to the accumulated combination.
    else if (num.size == numberOfSpacesLeft) {
        triples.add(group + num)
    }
    // if num.length > k then for each element e make a recursive call passing sub list of the elements list, k-1 and add element e to accumulated combination.
    else if (num.size > numberOfSpacesLeft) {
        for (i in num.indices) tripleOfTarget(num.subList(i + 1, num.size), numberOfSpacesLeft - 1, (group + num[i]), triples)
    }
}


