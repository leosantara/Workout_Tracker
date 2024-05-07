package id.ac.ukdw.workout_tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ukdw.workout_tracker.databinding.FragmentSettingBinding
import id.ac.ukdw.workout_tracker.databinding.FragmentStartAbsWorkoutBinding

class fragmentSetting : Fragment() {
    private var _binding : FragmentSettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentLainnya()).commit()
        }
        return binding.root
    }

}