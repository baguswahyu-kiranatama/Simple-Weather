package studio.koerniax.simpleweatherapps.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by KOERNIAX at 16/03/22
 */
class RecyclerViewMarginItemDecoration(
    private var topMargin: Int = 0,
    private var bottomMargin: Int = 0,
    private var leftMargin: Int = 0,
    private var rightMargin: Int = 0,
    private var columnMargin: Int = 0,
    private val isUseEqualMargin: Boolean = false,
    private val allMargin: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (isUseEqualMargin) {
            topMargin = allMargin
            bottomMargin = allMargin
            leftMargin = allMargin
            rightMargin = allMargin
            columnMargin = allMargin
        }

        val layoutType: Int
        var spanCount = 1
        val isHorizontal: Boolean
        val viewPosition: Int
        var spanIndex = 0

        when (parent.layoutManager) {
            is StaggeredGridLayoutManager -> {
                layoutType = STAGGER_TYPE
                spanCount = (parent.layoutManager as StaggeredGridLayoutManager).spanCount
                isHorizontal =
                    (parent.layoutManager as StaggeredGridLayoutManager).orientation == StaggeredGridLayoutManager.HORIZONTAL
                viewPosition =
                    (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).viewLayoutPosition
                spanIndex = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
            }
            is LinearLayoutManager -> {
                layoutType = LINEAR_TYPE
                isHorizontal =
                    (parent.layoutManager as LinearLayoutManager).orientation == LinearLayoutManager.HORIZONTAL
                viewPosition = parent.getChildAdapterPosition(view)
            }
            else -> {
                layoutType = GRID_TYPE
                spanCount = (parent.layoutManager as GridLayoutManager).spanCount
                isHorizontal =
                    (parent.layoutManager as GridLayoutManager).orientation == GridLayoutManager.HORIZONTAL
                viewPosition =
                    (view.layoutParams as GridLayoutManager.LayoutParams).viewLayoutPosition
                spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
            }
        }

        with(outRect) {
            if (isHorizontal) {
                when (layoutType) {
                    STAGGER_TYPE, GRID_TYPE -> {
                        if (spanCount < 2) {
                            top = topMargin
                            bottom = bottomMargin

                        } else if (spanCount > 2) {
                            when (spanIndex) {
                                0 -> {
                                    top = topMargin
                                    bottom = columnMargin / 2
                                }
                                spanCount - 1 -> {
                                    top = columnMargin / 2
                                    bottom = bottomMargin
                                }
                                else -> {
                                    top = columnMargin / 2
                                    bottom = columnMargin / 2
                                }
                            }

                        } else {
                            if (spanIndex == 0) {
                                top = topMargin
                                bottom = columnMargin / 2
                            } else {
                                top = columnMargin / 2
                                bottom = bottomMargin
                            }
                        }
                    }

                    else -> {
                        if (viewPosition == 0) {
                            left = leftMargin
                        }
                        top = topMargin
                        right = rightMargin
                        bottom = bottomMargin
                    }
                }

            } else {
                when (layoutType) {
                    STAGGER_TYPE, GRID_TYPE -> {
                        if (spanCount < 2) {
                            left = leftMargin
                            right = rightMargin

                        } else if (spanCount > 2) {
                            when (spanIndex) {
                                0 -> {
                                    left = leftMargin
                                    right = columnMargin / 2
                                }
                                spanCount - 1 -> {
                                    left = columnMargin / 2
                                    right = rightMargin
                                }
                                else -> {
                                    left = columnMargin / 2
                                    right = columnMargin / 2
                                }
                            }

                        } else {
                            if (spanIndex == 0) {
                                left = leftMargin
                                right = columnMargin / 2
                            } else {
                                left = columnMargin / 2
                                right = rightMargin
                            }
                        }

                        if (viewPosition < spanCount) {
                            top = topMargin
                        }
                        bottom = columnMargin
                    }

                    else -> {
                        if (viewPosition == 0) {
                            top = topMargin
                        }
                        left = leftMargin
                        right = rightMargin
                        bottom = bottomMargin
                    }
                }
            }
        }
    }

    companion object {
        private const val LINEAR_TYPE = 1
        private const val STAGGER_TYPE = 2
        private const val GRID_TYPE = 3
    }

}