package com.progandro.workout

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.progandro.workout.databinding.FragmentHomeBinding
import com.progandro.workout.databinding.FragmentResepMakananBinding

class fragmentHome : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnResepMakanan.setOnClickListener {
            Toast.makeText(requireContext(), "Resep", Toast.LENGTH_SHORT).show()
            Intent(requireContext(), MakananActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnABSWorkout.setOnClickListener {
            Intent(getContext(), WorkoutActivity::class.java).also {
                it.putExtra("fragmentType", "FragmentA")
                startActivity(it)
            }

        }

            binding.BarBottom.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnHome -> {
                    Toast.makeText(requireContext(), "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.btnPesan -> {
                    Toast.makeText(requireContext(), "Pesan", Toast.LENGTH_SHORT).show()
                    // Lakukan sesuatu ketika menu Chat dipilih
                    true
                }
                R.id.btnLainnya -> {
                    Toast.makeText(requireContext(), "Lainnya", Toast.LENGTH_SHORT).show()
                    // Lakukan sesuatu ketika menu Lainnya dipilih
                    true
                }
                else -> false
            }
        }


        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}