package com.progandro.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.progandro.workout.databinding.ActivityLoginBinding
import com.progandro.workout.databinding.ActivityWorkoutBinding

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