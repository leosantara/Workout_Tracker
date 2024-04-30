package com.progandro.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.progandro.workout.databinding.ActivityMainBinding
import com.progandro.workout.databinding.ActivityMakananBinding

class MakananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakananBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakananBinding.inflate(layoutInflater);
        setContentView(binding.root)

        val fragmentResepMakanan = fragmentResepMakanan()
        val fragmentManager : FragmentManager = supportFragmentManager

        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.makanan_activity, fragmentResepMakanan).commit()
    }
}