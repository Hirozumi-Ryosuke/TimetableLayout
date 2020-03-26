package com.example.timetablelayout.layoutmanager

class TimetableLayoutManager (
    private val columnWidth: Int,
    private val heightPerMinute: Int,
    private val periodLookUp: (position: Int) -> PeriodInfo
) : RecyclerView.LayoutManager() {

    companion object {
        private const val NO_TIME = -1
    }

    class PeriodInfo(val startUnixMillis: Long, val endUnixMillis: Long, val columnNumber: Int)

    private data class Period(
        val startUnixMin: Int,
        val endUnixMin: Int,
        val columnNumber: Int,
        val adapterPosition: Int,
        val positionInColumn: Int
    ) {
        val durationMin = endUnixMin - startUnixMin
    }
}