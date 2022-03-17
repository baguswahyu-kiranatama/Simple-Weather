package studio.koerniax.simpleweatherapps.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import studio.koerniax.simpleweatherapps.base.BaseActivityVB
import studio.koerniax.simpleweatherapps.databinding.ActivityMainBinding

class MainActivity : BaseActivityVB<ActivityMainBinding>() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.mainFragmentContainer.id) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}