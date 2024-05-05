package id.ac.ukdw.workout_tracker

import id.ac.ukdw.workout_tracker.R
import android.os.Bundle
import android.widget.Toast
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

        val fragmentType = intent.getStringExtra("fragmentType")
        when (fragmentType) {
            "FragmentHome" ->  supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentHome()).commit()
            "FragmentPesan" ->  supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentListPesanNotif()).commit()
            else -> Toast.makeText(this, "lainnya", Toast.LENGTH_SHORT).show() // Handle jika tipe fragment tidak dikenali
        }
    }
}
