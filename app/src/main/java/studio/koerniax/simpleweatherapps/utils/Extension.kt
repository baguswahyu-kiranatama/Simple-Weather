package studio.koerniax.simpleweatherapps.utils

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by KOERNIAX at 16/03/22
 */

inline fun <T : ViewBinding> ViewGroup.viewBinding(bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> T) =
    bindingInflater(LayoutInflater.from(context), this, false)

fun <VH : RecyclerView.ViewHolder> RecyclerView.setAutoNullAdapter(
    adapter: RecyclerView.Adapter<VH>
) {
    this.adapter = adapter
    this.clearAdapter()
}

internal fun RecyclerView.clearAdapter() {
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View?) {}

        override fun onViewDetachedFromWindow(v: View?) {
            this@clearAdapter.adapter = null
        }

    })
}

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Int.toDp(): Int {
    return (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

fun <T> Fragment.setBackStackData(key: String, data: T, popFragment: Boolean = false) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
    if (popFragment) findNavController().popBackStack()
}

fun <T> Fragment.getBackStackData(key: String, singleCall: Boolean = true, result: (T) -> (Unit)) {
    findNavController().currentBackStackEntry?.savedStateHandle
        ?.getLiveData<T>(key)?.observe(viewLifecycleOwner) {
            result(it)
            if (singleCall)
                findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key)
        }
}