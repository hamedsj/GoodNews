package me.pitok.goodnews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            setupNavigation()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation(){
        val host = NavHostFragment.create(R.navigation.navigation_graph)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityRoot, host)
            .setPrimaryNavigationFragment(host)
            .commit()
    }

}