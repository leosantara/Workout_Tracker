package id.ac.ukdw.workout_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.ac.ukdw.workout_tracker.R
import id.ac.ukdw.workout_tracker.databinding.ActivityWorkoutBinding

class WorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater);
        setContentView(binding.root)
        val fragmentType = intent.getStringExtra("fragmentType")
        when (fragmentType) {
            "FragmentA" ->  supportFragmentManager.beginTransaction().replace(R.id.workout_activity, fragmentAbsWorkout()).commit()
            else -> Toast.makeText(this, "Pesan", Toast.LENGTH_SHORT).show() // Handle jika tipe fragment tidak dikenali
        }
    }

}