package id.ac.ukdw.workout_tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ukdw.workout_tracker.databinding.FragmentLainnyaBinding
import id.ac.ukdw.workout_tracker.databinding.FragmentSettingBinding

class fragmentLainnya : Fragment() {
    private var _binding : FragmentLainnyaBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLainnyaBinding.inflate(inflater, container, false)

        binding.btnSetting.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentSetting()).commit()
        }
        return binding.root
    }

}