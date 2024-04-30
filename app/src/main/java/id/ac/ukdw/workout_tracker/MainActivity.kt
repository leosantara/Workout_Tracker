package id.ac.ukdw.workout_tracker

import id.ac.ukdw.workout_tracker.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import id.ac.ukdw.workout_tracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentHome = fragmentHome()
        val fragmentManager : FragmentManager = supportFragmentManager

        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.main_activity, fragmentHome).commit()
    }
}
