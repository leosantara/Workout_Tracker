package id.ac.ukdw.workout_tracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import id.ac.ukdw.workout_tracker.R
import id.ac.ukdw.workout_tracker.databinding.ActivityMakananBinding

class MakananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakananBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
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
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentLainnya")
                        it.putExtra("selectedItemId", R.id.btnLainnya)
                        it.putExtra("selectedItemIdd", R.id.btnLainnya)
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

        val fragmentType = intent.getStringExtra("fragmentType")
        when (fragmentType) {
            "FragmentA" -> {
                val fragmentResepMakanan = fragmentResepMakanan()
                supportFragmentManager.findFragmentById(R.id.makanan_activity)
                    ?.let { removeFragment(it) }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.makanan_activity, fragmentResepMakanan)
                    .commit()
            }
            "FragmentB" -> {
                val fragmentTambahMakanan = fragmentTambahMakanan()
                supportFragmentManager.findFragmentById(R.id.makanan_activity)
                    ?.let { removeFragment(it) }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.makanan_activity, fragmentTambahMakanan)
                    .commit()
            }
        }
    }

    private fun removeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }
}