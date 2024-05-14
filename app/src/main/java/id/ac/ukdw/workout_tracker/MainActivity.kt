package id.ac.ukdw.workout_tracker

import android.content.Context
import id.ac.ukdw.workout_tracker.R
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import id.ac.ukdw.workout_tracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        setContentView(binding.root)


        val fragmentType = intent.getStringExtra("fragmentType")
        when (fragmentType) {
            "FragmentHome" -> {
                val fragmentHome = fragmentHome()
                supportFragmentManager.findFragmentById(R.id.main_activity)
                    ?.let { removeFragment(it) }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_activity, fragmentHome)
                    .commit()
                binding.BarBottom.selectedItemId = R.id.btnHome
            }
            "FragmentPesan" -> {
                val fragmentPesan = fragmentListPesanNotif()
                supportFragmentManager.findFragmentById(R.id.main_activity)
                    ?.let { removeFragment(it) }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_activity, fragmentPesan)
                    .commit()
                binding.BarBottom.selectedItemId = R.id.btnPesan
            }
            "FragmentLainnya" -> {
                val fragmentLainnya = fragmentLainnya()
                supportFragmentManager.findFragmentById(R.id.main_activity)
                    ?.let { removeFragment(it) }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_activity, fragmentLainnya)
                    .commit()
                binding.BarBottom.selectedItemId = R.id.btnLainnya
            }
        }





        binding.BarBottom.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnHome -> {
                    removeFragment(supportFragmentManager.findFragmentById(R.id.main_activity)!!)
                    this.supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentHome()).commit()
                    true
                }
                R.id.btnPesan -> {
                    removeFragment(supportFragmentManager.findFragmentById(R.id.main_activity)!!)
                    this.supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentListPesanNotif()).commit()
                    true
                }
                R.id.btnLainnya -> {
                    removeFragment(supportFragmentManager.findFragmentById(R.id.main_activity)!!)
                    this.supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentLainnya()).commit()
                    true
                }
                else -> false
            }
        }


    }
    private fun removeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }

}
