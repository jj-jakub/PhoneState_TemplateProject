package com.jj.templateproject.framework.presentation.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jj.templateproject.R
import com.jj.templateproject.data.text.VersionTextProvider
import com.jj.templateproject.databinding.ActivityMainBinding
import com.jj.templateproject.core.framework.presentation.viewmodels.MainViewModel
import org.koin.java.KoinJavaComponent.inject

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val versionTextProvider: VersionTextProvider by inject(VersionTextProvider::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setMainLabelText()
        setupBottomMenu()
    }

    private fun setMainLabelText() {
        activityMainBinding.mainLabel.text = versionTextProvider.getAboutVersionText()
    }

    private fun setupBottomMenu() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        activityMainBinding.bottomNavigation.setupWithNavController(navHostFragment.navController)
        activityMainBinding.secondBottomNavigation.setupWithNavController(navHostFragment.navController)
    }
}