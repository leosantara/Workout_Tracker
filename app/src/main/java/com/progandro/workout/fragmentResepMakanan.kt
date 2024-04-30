package com.progandro.workout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.progandro.workout.databinding.FragmentResepMakananBinding

class fragmentResepMakanan : Fragment() {

    private var _binding : FragmentResepMakananBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResepMakananBinding.inflate(inflater, container, false)

        binding.btnSaladBuah.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.makanan_activity, fragmentSaladBuah()).commit()
        }

        binding.btnBack.setOnClickListener{
            Intent(getContext(), MainActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.BarBottom.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnHome -> {
                    Intent(getContext(), MainActivity::class.java).also {
                        startActivity(it)
                    }
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