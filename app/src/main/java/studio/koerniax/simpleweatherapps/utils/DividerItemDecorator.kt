package studio.koerniax.simpleweatherapps.utils

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by KOERNIAX at 16/03/22
 */
class DividerItemDecorator(
    var divider: Drawable?,
    private val usePadding: Boolean = true
) : RecyclerView.ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft: Int
        val dividerRight: Int
        if (usePadding) {
            dividerLeft = parent.paddingLeft
            dividerRight = parent.width - parent.paddingRight
        } else {
            dividerLeft = 0 - parent.left
            dividerRight = parent.right
        }
        for (i in 0..parent.childCount - 2) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val dividerTop: Int = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + divider?.intrinsicHeight!!
            divider?.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider?.draw(canvas)
        }
    }
}