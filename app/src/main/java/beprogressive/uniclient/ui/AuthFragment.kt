package beprogressive.uniclient.ui

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gmail.beprogressive.it.uniclient.R
import com.gmail.beprogressive.it.uniclient.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : PopFragment() {
    private lateinit var binding: FragmentAuthBinding

    private val viewModel by lazy {
        ViewModelProvider(
            this
        )[AuthViewModel::class.java]
    }

    override fun isWide(): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AuthFragment.viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        lifecycleScope.launch {
            lifecycleScope.launch {
                requireActivity().repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { uiState ->
                        if (uiState.isUserLoggedIn) {
                            dismiss()
                        }
                        if (uiState.errorMessage != null) {
                            Toast.makeText(
                                requireContext(),
                                uiState.errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        if (uiState.gitHubAuth) {
                            startAuth()
                        }

                        viewModel.clearUiState()
                    }
                }
            }
        }
    }


    private fun startAuth() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.getAuthUrl())).apply {
            val bundle = Bundle()
            bundle.putString("Accept", "application/json")
            putExtra(Browser.EXTRA_HEADERS, bundle)
        }

        val pendingIntent = PendingIntent.getActivity(
            context?.applicationContext,
            1000,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        pendingIntent.send()
    }

//    val activityLauncher = registerForActivityResult(MySecondActivityContract()) { result ->
//        // используем result
//    }
}


//class MySecondActivityContract : ActivityResultContract<String, Int?>() {
//
//    override fun createIntent(context: Context, input: String?): Intent {
//        return Intent(Intent.ACTION_VIEW, Uri.parse(GitHubDataSource.AUTH_URL))
//    }
//
//    override fun parseResult(resultCode: Int, intent: Intent?): Int? {
//        log("parseResult data: " + intent?.data)
//        log("parseResult extras: " + intent?.extras?.keySet())
//        return when {
//            resultCode != Activity.RESULT_OK -> null
//            else -> intent?.getIntExtra("my_result_key", 42)
//        }
//    }
//
//    override fun getSynchronousResult(context: Context, input: String?): SynchronousResult<Int?>? {
//        return if (input.isNullOrEmpty()) SynchronousResult(42) else null
//    }
//}