package id.ac.ukdw.workout_tracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.ac.ukdw.workout_tracker.databinding.ActivityWorkoutBinding

class WorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater);
        binding.BarBottom.setOnItemSelectedListener { menuItem ->
            val itemId = menuItem.itemId
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("selectedItemId", itemId)
            editor.apply()
            true
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
            "FragmentA" -> supportFragmentManager.beginTransaction().replace(R.id.workout_activity, fragmentWorkout.newInstance("A")).commit()
            "FragmentB" -> supportFragmentManager.beginTransaction().replace(R.id.workout_activity, fragmentWorkout.newInstance("B")).commit()
            "FragmentC" -> supportFragmentManager.beginTransaction().replace(R.id.workout_activity, fragmentWorkout.newInstance("C")).commit()
            else -> Toast.makeText(this, "Pesan", Toast.LENGTH_SHORT).show() // Handle jika tipe fragment tidak dikenali
        }
    }

}