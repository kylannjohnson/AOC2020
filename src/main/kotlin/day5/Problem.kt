package day5

import util.getResourceAsText


fun main() {

    val seat1 = "BFFFBBFRRR"
    val seat2 = "FFFBBBFRRR"
    val seat3 = "BBFFBBFRLL"

//    println(seat1.findSeat())
//    println(seat2.findSeat())
//    println(seat3.findSeat())
    val allSeats = getResourceAsText("data5.txt").map { it.findSeat() }
    val backRow = allSeats
        .maxByOrNull { it.row }
//    println(backRow)
    allSeats.groupBy { it.row }.toSortedMap()
        .filterValues { it.size < 8 }


    allSeats.filter{ it.row == 89}.forEach(::println)

}

fun String.findSeat(): Seat {
    val row = findRow(0, 127, this.take(7).parse())
    val seat = findRow(0, 7, this.takeLast(3).parse())

    return Seat(row, seat)
}

fun findRow(front: Int, back: Int, directions: List<Direction>): Int {
//    println("$front, $back, $directions")

    if (directions.isEmpty()) return front

    val half = (back - front + 1) / 2

    return if (directions.first() == Direction.LOWER) {
        findRow(front, front + half - 1, directions.drop(1))
    } else {
        findRow(front + half, back, directions.drop(1))
    }
}

fun String.parse(): List<Direction> {
    return mapNotNull { char -> Direction.values().first { it.value.contains(char.toString()) } }
}

enum class Direction(val value: List<String>) { LOWER(listOf("F", "L")), UPPER(listOf("B", "R")) }

data class Seat(val row: Int, val column: Int) {
    val seatId = row * 8 + column

    override fun toString(): String {
        return "row: $row, col: $column, seatId: $seatId"
    }
}