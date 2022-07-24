package beprogressive.uniclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gmail.beprogressive.it.uniclient.R
import com.gmail.beprogressive.it.uniclient.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : PopFragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val viewModel by lazy {
                ViewModelProvider(
                    this
                )[DetailsViewModel::class.java]
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@DetailsFragment.viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.user.observe(viewLifecycleOwner) {
            it?.let {
//                Glide
//                    .with(binding.imageView.context)
//                    .load(it.imageUrl)
//                    .centerCrop()
//                    .error(R.drawable.ic_action_name)
//                    .into(binding.imageView)
            }
        }
    }
}