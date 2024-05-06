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
    private var selectedButtonIndex = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        fun onBottomBarButtonClicked(index: Int) {
            selectedButtonIndex = index
            // Update tampilan bottom bar untuk menandai button yang dipilih
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

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val selectedItemId = sharedPreferences.getInt("selectedItemId", -1)


        if (selectedItemId != -1) {
            binding.BarBottom.selectedItemId = selectedItemId
        }

        setContentView(binding.root)

        val fragmentType = intent.getStringExtra("fragmentType")
        when (fragmentType) {
            "FragmentHome" ->  supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentHome()).commit()
            "FragmentPesan" ->  supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentListPesanNotif()).commit()
            else -> Toast.makeText(this, "lainnya", Toast.LENGTH_SHORT).show() // Handle jika tipe fragment tidak dikenali
        }


    }


}
