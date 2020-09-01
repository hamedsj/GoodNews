package me.pitok.goodnews.appMain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import me.pitok.goodnews.R
import me.pitok.settings.entity.UIMode

class MainActivity : AppCompatActivity() {

    companion object{
        const val NAV_HOST_TAG = "good_news_nav_host"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val userInterfaceMode = when (UIMode.currentUIMode) {
            is UIMode.DarkMode -> AppCompatDelegate.MODE_NIGHT_YES
            is UIMode.LightMode -> AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(userInterfaceMode)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(
            R.anim.enter_activity_fade_in_anim,
            R.anim.enter_activity_fade_out_anim
        )
        if (savedInstanceState == null){
            setupNavigation()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation(){
        if (supportFragmentManager.findFragmentByTag(NAV_HOST_TAG) != null){
            val host = supportFragmentManager
                .findFragmentByTag(NAV_HOST_TAG) as NavHostFragment
            supportFragmentManager
                .beginTransaction()
                .attach(host)
                .setPrimaryNavigationFragment(host)
                .commit()
        }else{
            val host = NavHostFragment.create(R.navigation.navigation_graph)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainActivityRoot, host, NAV_HOST_TAG)
                .setPrimaryNavigationFragment(host)
                .commit()
        }
    }

}