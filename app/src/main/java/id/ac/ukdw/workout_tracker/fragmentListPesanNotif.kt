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

        notifList.add(NotifClass("Aha", "aha"))
        notifList.add(NotifClass("biji", "aha"))
        notifList.add(NotifClass("bah", "aha"))
        notifList.add(NotifClass("wkwkw", "aha"))
        notifAdapter = NotifAdapter(notifList)
        binding.recyclerView.adapter = notifAdapter

        binding.BarBottom.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnHome -> {
                    Toast.makeText(requireContext(), "Home", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentHome()).commit()
                    true
                }
                R.id.btnPesan -> {
                    Toast.makeText(requireContext(), "Pesan", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentListPesanNotif()).commit()
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