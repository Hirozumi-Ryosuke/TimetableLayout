package com.example.timetablelayout.item

import com.example.timetablelayout.R

class SpaceItem : BindableItem<ItemSpaceBinding>() {
    override fun getLayout() = R.layout.item_space

    override fun bind(viewBinding: ItemSpaceBinding, position: Int) {
    }
}