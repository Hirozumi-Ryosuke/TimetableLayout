package com.example.timetablelayout.decoration

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.timetablelayout.R
import com.example.timetablelayout.model.Program
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ProgramTimeLabelDecoration (context: Context, private val periods: List<Period>, heightPerMin: Int) :
        TimeLabelDecoration(
            context.resources.getDimensionPixelSize(R.dimen.timeLabelWidth),
            heightPerMin,
            context.resources.getDimension(R.dimen.timeLabelTextSize),
            Color.WHITE,
            ContextCompat.getColor(context, R.color.black)
        ) {

    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    override fun canDecorate(position: Int): Boolean = periods.getOrNull(position) is Program

    override fun getStartUnixMillis(position: Int): Long = periods.getOrNull(position)?.startAt ?: 0

    override fun formatUnixMillis(unixMillis: Long): String =
        LocalDateTime.ofEpochSecond(unixMillis / 1000, 0, ZoneOffset.UTC).format(formatter)
}