package com.example.timetablelayout.item

import com.example.timetablelayout.R

class ProgramItem (private val program: Program) : BindableItem<ItemProgramBinding>() {
    override fun getLayout() = R.layout.item_program

    override fun bind(viewBinding: ItemProgramBinding, position: Int) {
        viewBinding.program = program
    }
}