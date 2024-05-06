package id.ac.ukdw.workout_tracker

import android.content.Context
import id.ac.ukdw.workout_tracker.R
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
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
            "FragmentHome" ->  supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentHome()).commit().also {
                val selectedItemIdd = sharedPreferences.getInt("selectedItemId", R.id.btnHome)
                val selectedItemId = sharedPreferences.getInt("selectedItemId", selectedItemIdd)
                    binding.BarBottom.selectedItemId = selectedItemId
                binding.BarBottom.selectedItemId = R.id.btnHome
            }
            "FragmentPesan" ->  supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentListPesanNotif()).commit().also{
                val selectedItemIdd = sharedPreferences.getInt("selectedItemId", R.id.btnPesan)
                val selectedItemId = sharedPreferences.getInt("selectedItemId", selectedItemIdd)
                binding.BarBottom.selectedItemId = selectedItemId
                binding.BarBottom.selectedItemId = R.id.btnPesan
            }
            else -> Toast.makeText(this, "lainnya", Toast.LENGTH_SHORT).show() // Handle jika tipe fragment tidak dikenali
        }

        binding.BarBottom.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnHome -> {
                    this.supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentHome()).commit()
                    true
                }
                R.id.btnPesan -> {
                    Toast.makeText(this, "Pesan", Toast.LENGTH_SHORT).show()
                    this.supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentListPesanNotif()).commit()
                    true
                }
                R.id.btnLainnya -> {
                    Toast.makeText(this, "Lainnya", Toast.LENGTH_SHORT).show()
                    // Lakukan sesuatu ketika menu Lainnya dipilih
                    true
                }
                else -> false
            }
        }


    }


}
