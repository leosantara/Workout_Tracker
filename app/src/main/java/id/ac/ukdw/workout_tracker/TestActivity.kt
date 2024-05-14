package id.ac.ukdw.workout_tracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import id.ac.ukdw.workout_tracker.databinding.ActivityMakananBinding
import id.ac.ukdw.workout_tracker.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater);

        binding.BarBottom.setOnItemSelectedListener { menuItem ->

            when (menuItem.itemId) {

                R.id.btnHome -> {
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentHome")
                        startActivity(it)
                    }
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.btnPesan -> {

                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentPesan")
                        startActivity(it)
                    }
                    Toast.makeText(this, "Pesan", Toast.LENGTH_SHORT).show()
                    // Lakukan sesuatu ketika menu Chat dipilih
                    true
                }
                R.id.btnLainnya -> {
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentLainnya")
                        startActivity(it)
                    }
                    Toast.makeText(this, "Lainnya", Toast.LENGTH_SHORT).show()
                    // Lakukan sesuatu ketika menu Lainnya dipilih
                    true
                }
                else -> false
            }
        }

        setContentView(binding.root)

        val fragmentSetting = fragmentSetting()
        val fragmentManager : FragmentManager = supportFragmentManager

        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.TestActivity, fragmentSetting).commit()
    }
}