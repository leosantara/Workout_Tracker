package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.ac.ukdw.workout_tracker.databinding.ActivityMakananBinding

class   MakananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakananBinding
    private val TAG = "MakananActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        supportActionBar?.hide()
        binding = ActivityMakananBinding.inflate(layoutInflater)

        binding.BarBottom.setOnItemSelectedListener { menuItem ->
            Log.d(TAG, "Bottom navigation item selected: ${menuItem.title}")
            when (menuItem.itemId) {
                R.id.btnHome -> {
                    Log.d(TAG, "Menu Home selected")
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentHome")
                        it.putExtra("selectedItemId", R.id.btnHome)
                        startActivity(it)
                    }
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.btnPesan -> {
                    Log.d(TAG, "Menu Pesan selected")
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentPesan")
                        it.putExtra("selectedItemId", R.id.btnPesan)
                        startActivity(it)
                    }
                    Toast.makeText(this, "Pesan", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.btnLainnya -> {
                    Log.d(TAG, "Menu Lainnya selected")
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentLainnya")
                        it.putExtra("selectedItemId", R.id.btnLainnya)
                        startActivity(it)
                    }
                    Toast.makeText(this, "Lainnya", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        setContentView(binding.root)

        val fragmentType = intent.getStringExtra("fragmentType")
        Log.d(TAG, "Fragment type: $fragmentType")
        when (fragmentType) {
            "FragmentA" -> {
                Log.d(TAG, "Replacing fragment with FragmentA")
                val fragmentResepMakanan = fragmentResepMakanan()
                supportFragmentManager.findFragmentById(R.id.makanan_activity)
                    ?.let { removeFragment(it) }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.makanan_activity, fragmentResepMakanan)
                    .commit()
            }
            "FragmentB" -> {
                Log.d(TAG, "Replacing fragment with FragmentB")
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
        Log.d(TAG, "Removing fragment")
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }
}
