package studio.koerniax.simpleweatherapps.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.koerniax.simpleweatherapps.R
import studio.koerniax.simpleweatherapps.databinding.ItemDailyForecastBinding
import studio.koerniax.simpleweatherapps.model.response.DailyForecast
import studio.koerniax.simpleweatherapps.utils.Constants
import studio.koerniax.simpleweatherapps.utils.Helper
import studio.koerniax.simpleweatherapps.utils.loadImage
import studio.koerniax.simpleweatherapps.utils.viewBinding
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by KOERNIAX at 16/03/22
 */
class DailyForecastAdapter :
    ListAdapter<DailyForecast, DailyForecastAdapter.ViewHolder>(DiffUtilCallback()) {

    inner class ViewHolder(private val binding: ItemDailyForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: DailyForecast) {
            with(binding) {
                tvDate.text = Helper.millisToDate(data.timeStamp)
                val urlIcon = Constants.IMAGE_URL + data.weather?.firstOrNull()?.icon + "@2x.png"
                ivIcon.loadImage(urlIcon)
                tvWeather.text = data.weather?.firstOrNull()?.description?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                tvTemp.text = itemView.resources.getString(
                    R.string.text_temperature_range,
                    data.temperature?.minTemp?.roundToInt().toString(),
                    data.temperature?.maxTemp?.roundToInt().toString()
                )
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<DailyForecast>() {
        override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem.timeStamp == newItem.timeStamp
        }

        override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.viewBinding(ItemDailyForecastBinding::inflate))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

}