package beprogressive.uniclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gmail.beprogressive.it.uniclient.R
import com.gmail.beprogressive.it.uniclient.databinding.FragmentBottomNavigationDrawerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomNavigationDrawerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_bottom_navigation_drawer,
                container,
                false
            )

        binding.apply {
            lifecycleOwner = this@BottomNavigationDrawerFragment
            viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        }

        return binding.root
    }
}