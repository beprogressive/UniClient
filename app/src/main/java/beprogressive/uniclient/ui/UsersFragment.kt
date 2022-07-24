package beprogressive.uniclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import beprogressive.common.model.UserItem
import beprogressive.common.ui.UserItemClickInterface
import beprogressive.common.ui.UserItemInterface
import beprogressive.uniclient.observeInLifecycle
import com.gmail.beprogressive.it.uniclient.R
import com.gmail.beprogressive.it.uniclient.databinding.FragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class UsersFragment : Fragment(), UserItemInterface {

    private lateinit var binding: FragmentUsersBinding

    private val viewModel by lazy {
        ViewModelProvider(
            this
        )[UsersViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_users, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@UsersFragment.viewModel
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = UserListAdapter(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
//        viewModel.currentItem.observe(viewLifecycleOwner) {
//            it?.let {
//                viewModel.clearCurrentItem()
//                openDetails(it.userId)
//            }
//        }

        viewModel.eventsFlow
            .onEach {
                when (it) {
                    is UsersViewModel.Event.ShowSnackBar -> {
                        it.text
                    }
                    is UsersViewModel.Event.ShowToast -> {}
                }
            }
            .observeInLifecycle(viewLifecycleOwner)
    }

    override fun onUserItemClick(item: UserItem) {
        (activity as? UserItemClickInterface)?.onUserItemClick(item)
    }

    override fun onUserItemFavorite(item: UserItem) {
        viewModel.switchUserFavorite(item)
    }
}