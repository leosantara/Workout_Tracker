package id.ac.ukdw.workout_tracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import id.ac.ukdw.workout_tracker.R
import id.ac.ukdw.workout_tracker.databinding.ActivityMakananBinding

class MakananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakananBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakananBinding.inflate(layoutInflater);

        binding.BarBottom.setOnItemSelectedListener { menuItem ->

            when (menuItem.itemId) {

                R.id.btnHome -> {

                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentHome")
                        it.putExtra("fragmentType", "FragmentHome")
                        it.putExtra("selectedItemId", R.id.btnHome)
                        it.putExtra("selectedItemIdd", R.id.btnHome)
                        startActivity(it)
                    }
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.btnPesan -> {

                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentPesan")
                        it.putExtra("selectedItemId", R.id.btnPesan)
                        it.putExtra("selectedItemIdd", R.id.btnPesan)
                        startActivity(it)
                    }
                    Toast.makeText(this, "Pesan", Toast.LENGTH_SHORT).show()
                    // Lakukan sesuatu ketika menu Chat dipilih
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

        setContentView(binding.root)

        val fragmentResepMakanan = fragmentResepMakanan()
        val fragmentManager : FragmentManager = supportFragmentManager

        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.makanan_activity, fragmentResepMakanan).commit()
    }
}