package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.ac.ukdw.workout_tracker.R
import id.ac.ukdw.workout_tracker.databinding.FragmentAbsWorkoutBinding


class fragmentAbsWorkout : Fragment() {
    private var _binding : FragmentAbsWorkoutBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAbsWorkoutBinding.inflate(inflater, container, false)

        binding.btnStart.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.workout_activity, fragmentStartAbsWorkout()).commit()
        }

        binding.btnBack.setOnClickListener{
            Intent(getContext(), MainActivity::class.java).also {
                it.putExtra("fragmentType", "FragmentHome")
                startActivity(it)
            }
        }

        binding.BarBottom.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnHome -> {
                    Intent(getContext(), MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentHome")
                        startActivity(it)
                    }
                    Toast.makeText(requireContext(), "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.btnPesan -> {
                    Intent(getContext(), MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentPesan")
                        startActivity(it)
                    }
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

}