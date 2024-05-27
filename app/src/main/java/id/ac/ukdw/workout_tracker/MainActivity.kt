package id.ac.ukdw.workout_tracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.ac.ukdw.workout_tracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val fragmentType = intent.getStringExtra("fragmentType")
        Log.d(TAG, "Fragment type: $fragmentType")
        when (fragmentType) {
            "FragmentHome" -> {
                Log.d(TAG, "Replacing fragment with FragmentHome")
                val fragmentHome = fragmentHome()
                supportFragmentManager.findFragmentById(R.id.main_activity)
                    ?.let { removeFragment(it) }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_activity, fragmentHome)
                    .commit()
                binding.BarBottom.selectedItemId = R.id.btnHome
            }
            "FragmentPesan" -> {
                Log.d(TAG, "Replacing fragment with FragmentPesan")
                val fragmentPesan = fragmentListPesanNotif()
                supportFragmentManager.findFragmentById(R.id.main_activity)
                    ?.let { removeFragment(it) }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_activity, fragmentPesan)
                    .commit()
                binding.BarBottom.selectedItemId = R.id.btnPesan
            }
            "FragmentLainnya" -> {
                Log.d(TAG, "Replacing fragment with FragmentLainnya")
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
            Log.d(TAG, "Bottom navigation item selected: ${menuItem.title}")
            when (menuItem.itemId) {
                R.id.btnHome -> {
                    Log.d(TAG, "Replacing fragment with FragmentHome")
                    removeFragment(supportFragmentManager.findFragmentById(R.id.main_activity)!!)
                    this.supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentHome()).commit()
                    true
                }
                R.id.btnPesan -> {
                    Log.d(TAG, "Replacing fragment with FragmentPesan")
                    removeFragment(supportFragmentManager.findFragmentById(R.id.main_activity)!!)
                    this.supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentListPesanNotif()).commit()
                    true
                }
                R.id.btnLainnya -> {
                    Log.d(TAG, "Replacing fragment with FragmentLainnya")
                    removeFragment(supportFragmentManager.findFragmentById(R.id.main_activity)!!)
                    this.supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentLainnya()).commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun removeFragment(fragment: Fragment) {
        Log.d(TAG, "Removing fragment")
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }
}
