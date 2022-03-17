package studio.koerniax.simpleweatherapps.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.koerniax.simpleweatherapps.databinding.ItemLocationBinding
import studio.koerniax.simpleweatherapps.model.response.LocationResponse
import studio.koerniax.simpleweatherapps.utils.Helper
import studio.koerniax.simpleweatherapps.utils.viewBinding

/**
 * Created by KOERNIAX at 16/03/22
 */
class LocationGeocodingAdapter :
    ListAdapter<LocationResponse, LocationGeocodingAdapter.ViewHolder>(DiffUtilCallback()) {

    var onClicked: ((data: LocationResponse) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: LocationResponse) {
            with(binding) {
                tvCity.text = data.name
                tvCountry.text = Helper.combineStateAndCountry(data.state, data.country)

                root.setOnClickListener {
                    onClicked?.invoke(data)
                }
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<LocationResponse>() {
        override fun areItemsTheSame(
            oldItem: LocationResponse,
            newItem: LocationResponse
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: LocationResponse,
            newItem: LocationResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.viewBinding(ItemLocationBinding::inflate))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

}