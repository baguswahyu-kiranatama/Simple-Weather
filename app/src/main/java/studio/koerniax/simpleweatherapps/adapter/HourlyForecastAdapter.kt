package studio.koerniax.simpleweatherapps.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.koerniax.simpleweatherapps.R
import studio.koerniax.simpleweatherapps.databinding.ItemHourlyForecastBinding
import studio.koerniax.simpleweatherapps.model.response.HourlyForecast
import studio.koerniax.simpleweatherapps.utils.Constants
import studio.koerniax.simpleweatherapps.utils.Helper
import studio.koerniax.simpleweatherapps.utils.loadImage
import studio.koerniax.simpleweatherapps.utils.viewBinding
import kotlin.math.roundToInt

/**
 * Created by KOERNIAX at 16/03/22
 */
class HourlyForecastAdapter :
    ListAdapter<HourlyForecast, HourlyForecastAdapter.ViewHolder>(DiffUtilCallback()) {

    inner class ViewHolder(private val binding: ItemHourlyForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: HourlyForecast) {
            with(binding) {
                tvTime.text = Helper.millisToHour(data.timeStamp)
                tvTemp.text = itemView.resources.getString(
                    R.string.text_temperature,
                    data.temp?.roundToInt().toString()
                )
                val urlIcon = Constants.IMAGE_URL + data.weather?.firstOrNull()?.icon + "@2x.png"
                ivIcon.loadImage(urlIcon)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<HourlyForecast>() {
        override fun areItemsTheSame(oldItem: HourlyForecast, newItem: HourlyForecast): Boolean {
            return oldItem.timeStamp == newItem.timeStamp
        }

        override fun areContentsTheSame(oldItem: HourlyForecast, newItem: HourlyForecast): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.viewBinding(ItemHourlyForecastBinding::inflate))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

}