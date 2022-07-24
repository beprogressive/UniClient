package beprogressive.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import beprogressive.common.model.UserItem
import beprogressive.common.ui.UserItemClickInterface
import beprogressive.common.ui.UserItemInterface
import beprogressive.favorites.R
import beprogressive.favorites.databinding.FragmentFavoritesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BottomSheetDialogFragment(), UserItemInterface {
    private lateinit var binding: FragmentFavoritesBinding

    private val viewModel by lazy {
        ViewModelProvider(
            this
        )[FavoritesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@FavoritesFragment.viewModel
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = FavoritesListAdapter(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { uiEvent ->
                    when (uiEvent) {
                        else -> {}
                    }
                    viewModel.uiStateDone()

                }
            }
        }
    }

    override fun onUserItemClick(item: UserItem) {
        (activity as? UserItemClickInterface)?.onUserItemClick(item)
    }

    override fun onUserItemFavorite(item: UserItem) {
        viewModel.switchUserFavorite(item)
    }
}