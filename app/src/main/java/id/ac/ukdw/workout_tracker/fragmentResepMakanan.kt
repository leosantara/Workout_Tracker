package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import id.ac.ukdw.workout_tracker.R
import id.ac.ukdw.workout_tracker.databinding.FragmentResepMakananBinding

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
                it.putExtra("fragmentType", "FragmentHome")
                it.putExtra("selectedItemId", R.id.btnHome)
                it.putExtra("selectedItemIdd", R.id.btnHome)
                startActivity(it)
            }
        }



        return binding.root


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}