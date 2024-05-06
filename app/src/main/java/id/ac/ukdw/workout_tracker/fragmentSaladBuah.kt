package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.ac.ukdw.workout_tracker.R
import id.ac.ukdw.workout_tracker.databinding.FragmentSaladBuahBinding

class fragmentSaladBuah : Fragment() {

    private var _binding : FragmentSaladBuahBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSaladBuahBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.makanan_activity, fragmentResepMakanan()).commit()
        }



        return binding.root
    }
}