package studio.koerniax.simpleweatherapps.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import studio.koerniax.simpleweatherapps.R
import studio.koerniax.simpleweatherapps.adapter.DailyForecastAdapter
import studio.koerniax.simpleweatherapps.adapter.HourlyForecastAdapter
import studio.koerniax.simpleweatherapps.base.BaseFragmentVB
import studio.koerniax.simpleweatherapps.data.network.ResultWrapper
import studio.koerniax.simpleweatherapps.databinding.FragmentHomeBinding
import studio.koerniax.simpleweatherapps.model.entity.FavoriteLocation
import studio.koerniax.simpleweatherapps.model.response.LocationResponse
import studio.koerniax.simpleweatherapps.model.response.WeatherResponse
import studio.koerniax.simpleweatherapps.ui.MainActivity
import studio.koerniax.simpleweatherapps.utils.*
import studio.koerniax.simpleweatherapps.viewmodel.HomeFragmentViewModel
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by KOERNIAX at 16/03/22
 */
class HomeFragment : BaseFragmentVB<FragmentHomeBinding>() {

    private val viewModel: HomeFragmentViewModel by viewModel()
    private lateinit var hourlyAdapter: HourlyForecastAdapter
    private lateinit var dailyAdapter: DailyForecastAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setupToolbar(binding.toolbar)

        getBackStackData<LocationResponse>(KEY_LOCATION_DATA) {
            viewModel.currentLocationData = it
            viewModel.fetchWeatherData()
        }

        getBackStackData<FavoriteLocation>(KEY_FAVORITE_DATA) {
            viewModel.currentLocationData = LocationResponse(
                name = it.locationName,
                lat = it.lat,
                lon = it.lng,
                country = it.locationCountry,
                state = it.locationState
            )
            viewModel.fetchWeatherData()
        }

        setupView()
        setupObserver()
        viewModel.fetchWeatherData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuSearch -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
                true
            }
            R.id.menuAddFavorite -> {
                viewModel.addToFavorite()
                true
            }
            R.id.menuFavorite -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFavoriteFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupView() {
        hourlyAdapter = HourlyForecastAdapter()
        dailyAdapter = DailyForecastAdapter()

        binding.rvHourlyForecast.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setAutoNullAdapter(hourlyAdapter)
            addItemDecoration(
                RecyclerViewMarginItemDecoration(
                    isUseEqualMargin = true,
                    allMargin = 12.toDp()
                )
            )
        }

        binding.rvDailyForecast.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setAutoNullAdapter(dailyAdapter)
            addItemDecoration(
                RecyclerViewMarginItemDecoration(
                    isUseEqualMargin = true,
                    allMargin = 12.toDp()
                )
            )
        }

        binding.swipeLayout.setOnRefreshListener {
            viewModel.fetchWeatherData(true)
        }

        binding.layoutState.btnRetry.setOnClickListener {
            viewModel.fetchWeatherData()
        }
    }

    private fun setupObserver() {
        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    with(binding.layoutState) {
                        root.isVisible = true
                        groupLoading.isVisible = true
                        groupError.isVisible = false
                    }
                    binding.swipeLayout.isVisible = false
                }
                is ResultWrapper.Success -> {
                    with(binding.swipeLayout) {
                        isRefreshing = false
                        isVisible = true
                    }
                    updateView(it.value)
                    binding.layoutState.root.isVisible = false
                }
                is ResultWrapper.Error -> {
                    with(binding.swipeLayout) {
                        isRefreshing = false
                        isVisible = false
                    }
                    with(binding.layoutState) {
                        root.isVisible = true
                        groupLoading.isVisible = false
                        groupError.isVisible = true
                        tvError.text = it.message
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.addFavoriteLiveData.collectLatest { isSuccess ->
                if (isSuccess) Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.text_add_favorite_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        /*viewModel.addFavoriteLiveData.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) Toast.makeText(
                requireContext(),
                resources.getString(R.string.text_add_favorite_success),
                Toast.LENGTH_SHORT
            ).show()
        }*/
    }

    private fun updateView(data: WeatherResponse) {
        with(binding) {
            tvCityName.text = viewModel.currentLocationData?.name
            tvCountryName.text = Helper.combineStateAndCountry(
                viewModel.currentLocationData?.state,
                viewModel.currentLocationData?.country
            )
            tvDate.text = Helper.millisToDateTime(data.current?.timeStamp)
            tvCurrentTemp.text = resources.getString(
                R.string.text_temperature,
                data.current?.temp?.roundToInt().toString()
            )
            val weatherDescription =
                data.current?.weather?.firstOrNull()?.description?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            tvCurrentWeather.text = resources.getString(
                R.string.text_weather_and_feels,
                weatherDescription,
                data.current?.feelsLike?.roundToInt()?.toString()
            )
            val urlIcon =
                Constants.IMAGE_URL + data.current?.weather?.firstOrNull()?.icon + "@2x.png"
            ivIcon.loadImage(urlIcon)

            hourlyAdapter.submitList(data.hourly?.take(24))
            dailyAdapter.submitList(data.daily)
        }
    }

    companion object {
        const val KEY_LOCATION_DATA = "key-location-data"
        const val KEY_FAVORITE_DATA = "key-favorite-data"
    }

}