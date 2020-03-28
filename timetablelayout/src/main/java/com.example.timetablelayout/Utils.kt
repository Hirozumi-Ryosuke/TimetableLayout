package com.example.timetablelayout

internal inline val View.adapterPosition
    get() = (layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

internal inline val View.layoutPosition
    get() = (layoutParams as RecyclerView.LayoutParams).viewLayoutPosition

internal inline fun <E> SparseArray<E>.getOrPut(key: Int, defaultValue: () -> E): E {
    val value = get(key)
    return if (value == null) {
        val answer = defaultValue()
        put(key, answer)
        answer
    } else {
        value
    }
}

internal fun Canvas.drawTextAtCenter(text: String, rect: Rect, paint: Paint) {
    val baseX = rect.centerX().toFloat() - paint.measureText(text) / 2f
    val textBounds = Rect().apply { paint.getTextBounds(text, 0, text.length - 1, this) }
    val baseY = rect.centerY() +
            if (textBounds.height() != 0) textBounds.height() / 2f
            else -(paint.)
}