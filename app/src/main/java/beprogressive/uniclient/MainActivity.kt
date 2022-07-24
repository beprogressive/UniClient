package beprogressive.uniclient

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import beprogressive.common.model.UserItem
import beprogressive.common.ui.UserItemClickInterface
import beprogressive.uniclient.ui.FabInterface
import beprogressive.uniclient.ui.MainViewModel
import beprogressive.uniclient.ui.UsersFragmentDirections
import com.gmail.beprogressive.it.uniclient.MainNavDirections
import com.gmail.beprogressive.it.uniclient.R
import com.gmail.beprogressive.it.uniclient.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FabInterface, UserItemClickInterface {

    /** Used to load the 'uniclient' library on application startup. */
    init {
        System.loadLibrary("uniclient")
    }

//    val Context.dataStore by preferencesDataStore("user_preferences")

    private val viewModel by lazy {
        ViewModelProvider(
            this
        )[MainViewModel::class.java]
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).apply {
            fabInterface = this@MainActivity
            setSupportActionBar(bar)
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { uiEvent ->
                    when (uiEvent) {
                        is MainViewModel.Event.ShowAuth -> showAuth()
                        else -> {}
                    }
                    viewModel.uiStateDone()
                }
            }
        }
    }

    private fun showAuth() {
        val action = MainNavDirections.openAuth()
        navController.navigate(action)
    }

    private fun showBottomNavigation() {
        val action = MainNavDirections.openBottomNavigation()
        navController.navigate(action)
    }

    private fun showFavorites() {
        if (navController.currentDestination?.id == beprogressive.favorites.R.id.favoritesFragment) return

        val action = UsersFragmentDirections.openFavorites()
        navController.navigate(action)
    }

    private fun showDetails(userItem: UserItem) {
        val action = MainNavDirections.openDetails(userItem.userId)
        navController.navigate(action)
    }

    override fun onResume() {
        super.onResume()
        intent?.data?.let {
            viewModel.handleAuth(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottomappbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                showBottomNavigation()
            }
        }
        return false
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onUserItemClick(userItem: UserItem) {
        showDetails(userItem)
    }

    override fun onFABClick() {
        showFavorites()
    }
}