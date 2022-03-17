package studio.koerniax.simpleweatherapps.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.koerniax.simpleweatherapps.databinding.ItemFavoriteLocationBinding
import studio.koerniax.simpleweatherapps.model.entity.FavoriteLocation
import studio.koerniax.simpleweatherapps.utils.Helper
import studio.koerniax.simpleweatherapps.utils.viewBinding

/**
 * Created by KOERNIAX at 17/03/22
 */
class FavoriteLocationAdapter :
    ListAdapter<FavoriteLocation, FavoriteLocationAdapter.ViewHolder>(DiffUtilCallback()) {

    var onClicked: ((data: FavoriteLocation) -> Unit)? = null
    var onDeleteClicked: ((data: FavoriteLocation) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemFavoriteLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: FavoriteLocation) {
            with(binding) {
                tvCity.text = data.locationName
                tvState.text =
                    Helper.combineStateAndCountry(data.locationState, data.locationCountry)

                btnDelete.setOnClickListener {
                    onDeleteClicked?.invoke(data)
                }

                root.setOnClickListener {
                    onClicked?.invoke(data)
                }
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<FavoriteLocation>() {
        override fun areItemsTheSame(
            oldItem: FavoriteLocation,
            newItem: FavoriteLocation
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavoriteLocation,
            newItem: FavoriteLocation
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.viewBinding(ItemFavoriteLocationBinding::inflate))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

}