package studio.koerniax.simpleweatherapps.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import studio.koerniax.simpleweatherapps.adapter.FavoriteLocationAdapter
import studio.koerniax.simpleweatherapps.base.BaseFragmentVB
import studio.koerniax.simpleweatherapps.databinding.FragmentFavoriteLocationBinding
import studio.koerniax.simpleweatherapps.ui.MainActivity
import studio.koerniax.simpleweatherapps.utils.RecyclerViewMarginItemDecoration
import studio.koerniax.simpleweatherapps.utils.setAutoNullAdapter
import studio.koerniax.simpleweatherapps.utils.setBackStackData
import studio.koerniax.simpleweatherapps.utils.toDp
import studio.koerniax.simpleweatherapps.viewmodel.FavoriteFragmentViewModel

/**
 * Created by KOERNIAX at 16/03/22
 */
class FavoriteLocationFragment : BaseFragmentVB<FragmentFavoriteLocationBinding>() {

    private val viewModel: FavoriteFragmentViewModel by viewModel()
    private lateinit var favoriteAdapter: FavoriteLocationAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteLocationBinding
        get() = FragmentFavoriteLocationBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setupToolbar(binding.toolbar)

        setupView()
        setupObserver()
        viewModel.fetchData()
    }

    private fun setupView() {
        favoriteAdapter = FavoriteLocationAdapter().apply {
            onClicked = {
                setBackStackData(
                    HomeFragment.KEY_FAVORITE_DATA,
                    it,
                    true
                )
            }
            onDeleteClicked = {
                viewModel.deleteData(it.id)
            }
        }

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setAutoNullAdapter(favoriteAdapter)
            addItemDecoration(
                RecyclerViewMarginItemDecoration(
                    isUseEqualMargin = true,
                    allMargin = 12.toDp()
                )
            )
        }
    }

    private fun setupObserver() {
        viewModel.favoriteLiveData.observe(viewLifecycleOwner) {
            favoriteAdapter.submitList(it)
        }
    }

}