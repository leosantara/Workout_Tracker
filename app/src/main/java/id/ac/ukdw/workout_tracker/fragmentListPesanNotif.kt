package id.ac.ukdw.workout_tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ukdw.workout_tracker.R
import id.ac.ukdw.workout_tracker.databinding.FragmentListPesanNotifBinding
import id.ac.ukdw.workout_tracker.databinding.FragmentResepMakananBinding

class fragmentListPesanNotif : Fragment() {

    private var notifList : ArrayList<NotifClass> = arrayListOf()
    private lateinit var notifAdapter : NotifAdapter
    private var _binding : FragmentListPesanNotifBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListPesanNotifBinding.inflate(inflater, container, false)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        notifList.add(NotifClass("Aha", "aha", "ABS Workout", 1 ,1 ))
        notifList.add(NotifClass("biji", "aha","ABS Workout", 1 ,1 ))
        notifList.add(NotifClass("bah", "aha", "ABS Workout", 1 ,1 ))
        notifList.add(NotifClass("wkwkw", "aha", "ABS Workout", 1 ,1 ))
        notifAdapter = NotifAdapter(notifList)
        binding.recyclerView.adapter = notifAdapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}