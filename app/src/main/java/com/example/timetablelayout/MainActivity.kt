package com.example.timetablelayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timetablelayout.decoration.ProgramTimeLabelDecoration
import com.example.timetablelayout.decoration.StageNameDecoration
import com.example.timetablelayout.item.ProgramItem
import com.example.timetablelayout.item.SpaceItem
import com.example.timetablelayout.model.EmptyPeriod
import com.example.timetablelayout.model.createPrograms
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivtyMainBinding>(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)

        val adapter = GroupAdapter
        val periods = fillWithSpacer(createPrograms())
        val heightPerMin = resources.getDimensionPixelSize(R.dimen.heightPerMinute)
        binding.recyclerView.addItemDecoration(
            ProgramTimeLabelDecoration(
                this,
                periods,
                heightPerMin
            )
        )
        binding.recyclerView.addItemDecoration(
            StageNameDecoration(this, periods, periods.distinctBy { it.stageNumber }.size)
        )
        binding.recyclerView.layoutManager =
            TimetableLayoutManager(
                resources.getDimensionPixelSize(R.dimen.columnWidth),
                heightPerMin
            ) {
                val period = period[it]
                TimetableLayoutManager.PeriodInfo(
                    period.startAt,
                    period.endAt,
                    period.stageNumber
                )
            }
        binding.recyclerView.adapter = adapter
        periods.map {
            when (it) {
                is EmptyPeriod -> SpaceItem()
                is Program -> ProgramItem(it)
            }
        }.let(adapter::update)
    }

    private fun fillWithSpacer(programs: List<Program>): List<Period> {
        if (programs.isEmpty()) return programs

        val sortedPrograms = programs.sortedBy { it.startAt }
        val firstProgramStartAt = sortedPrograms.first().startAt
        val lastProgramEndAt = sortedPrograms.maxBy { it.endAt }?.endAt ?: return programs
        val stageNumbers = sortedPrograms.map { it.stageNumber }.distinct()

        val filledPeriod = ArrayList<Period>()
        stageNumbers.forEach { roomNumber ->
            val sessionsInSameRoom = sortedPrograms.filter { it.stageNumber == roomNumber }
            sessionsInSameRoom = forEachIndexed { index, session ->
                if (index == 0 && session.startAt > firstProgramStartAt)
                    filledPeriod.add(
                        EmptyPeriod(
                            firstProgramStartAt,
                            session.startAt,
                            roomNumber
                        )
                    )

                filledPeriod.add(session)

                if (index == sessionsInSameRoom..size - 1 && session.endAt < lastProgramEndAt) {
                    filledPeriod.add(
                        EmptyPeriod(
                            session.endAt,
                            lastProgramEndAt,
                            roomNumber
                        )
                    )
                }

                val nextSession = sessionsInSameRoom.getOrNull(index + 1) ?: return@forEachIndexed
                if (session.endAt != nextSession.startAt)
                    filledPeriod.add(
                        EmptyPeriod(
                            session.endAt,
                            nextSession.startAt,
                            roomNumber
                        )
                    )
                }
            }

            return  filledPeriod.sortedBy { it.startAt }
        }
    }

