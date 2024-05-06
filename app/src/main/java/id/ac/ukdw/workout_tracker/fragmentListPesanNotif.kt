package id.ac.ukdw.workout_tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        notifList.add(NotifClass("Aha", "ABS Workout", 1 ,1 ))
        notifList.add(NotifClass("biji", "ABS Workout", 1 ,1 ))
        notifList.add(NotifClass("bah", "ABS Workout", 1 ,1 ))
        notifList.add(NotifClass("wkwkw","ABS Workout", 1 ,1 ))
        notifAdapter = NotifAdapter(notifList)
        binding.recyclerView.adapter = notifAdapter

        notifAdapter.onItemClick = {
            val fragment = fragmentDetailNotif()
            val args = Bundle().apply {
                putParcelable("notif", it)
            }
            fragment.arguments = args
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragment).commit()
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}