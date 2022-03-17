package studio.koerniax.simpleweatherapps.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import studio.koerniax.simpleweatherapps.R
import studio.koerniax.simpleweatherapps.adapter.LocationGeocodingAdapter
import studio.koerniax.simpleweatherapps.base.BaseFragmentVB
import studio.koerniax.simpleweatherapps.data.network.ResultWrapper
import studio.koerniax.simpleweatherapps.databinding.FragmentSearchBinding
import studio.koerniax.simpleweatherapps.ui.MainActivity
import studio.koerniax.simpleweatherapps.utils.DividerItemDecorator
import studio.koerniax.simpleweatherapps.utils.hideKeyboard
import studio.koerniax.simpleweatherapps.utils.setAutoNullAdapter
import studio.koerniax.simpleweatherapps.utils.setBackStackData
import studio.koerniax.simpleweatherapps.viewmodel.SearchFragmentViewModel

/**
 * Created by KOERNIAX at 17/03/22
 */
class SearchLocationFragment : BaseFragmentVB<FragmentSearchBinding>() {

    private val viewModel: SearchFragmentViewModel by viewModel()
    private lateinit var locationAdapter: LocationGeocodingAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setupToolbar(binding.toolbar)

        setupView()
        setupObserver()
    }

    private fun setupView() {
        locationAdapter = LocationGeocodingAdapter().apply {
            onClicked = { data ->
                binding.etSearch.hideKeyboard()
                setBackStackData(
                    HomeFragment.KEY_LOCATION_DATA,
                    data,
                    true
                )
            }
        }

        binding.rvLocation.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setAutoNullAdapter(locationAdapter)
            addItemDecoration(
                DividerItemDecorator(
                    ContextCompat.getDrawable(requireContext(), R.drawable.divider), false
                )
            )
        }

        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            binding.rvLocation.isVisible = text.isNullOrBlank().not()
            if (text?.isNotBlank() == true) {
                viewModel.fetchLocationData(text.toString())
            }
        }

        binding.layoutState.btnRetry.setOnClickListener {
            viewModel.fetchLocationData(binding.etSearch.text.toString())
        }
    }

    private fun setupObserver() {
        viewModel.locationLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    with(binding.layoutState) {
                        root.isVisible = true
                        groupLoading.isVisible = true
                        groupError.isVisible = false
                    }
                    binding.rvLocation.isVisible = false
                }
                is ResultWrapper.Success -> {
                    binding.layoutState.root.isVisible = false
                    binding.rvLocation.isVisible = true
                    locationAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {
                    with(binding.layoutState) {
                        root.isVisible = true
                        groupLoading.isVisible = false
                        groupError.isVisible = true
                        tvError.text = it.message
                    }
                }
            }
        }
    }

}