package id.ac.ukdw.workout_tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ukdw.workout_tracker.databinding.FragmentDetailNotifBinding
import id.ac.ukdw.workout_tracker.databinding.FragmentListPesanNotifBinding

class fragmentDetailNotif : Fragment() {
    private var _binding : FragmentDetailNotifBinding? = null
    private val binding get() = _binding!!
    private var notif: NotifClass? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailNotifBinding.inflate(inflater, container, false)

        arguments?.let { args ->
            // Mengecek apakah data dengan key "notif" ada dalam argument
            if (args.containsKey("notif")) {

                // Mengambil objek NotifClass dari argument
                notif = args.getParcelable("notif")
                binding.txtDeskripsi.text = "Selamat karena telah berhasil menyelesaikan pelatihan "+ notif?.JenisWorkout + " \n sebanyak "+ notif?.JumlahLatihan + " kali! \n \n Anda berhasil membakar Total "+ notif?.Kalori + " Kalori !!"
                if (notif?.JenisWorkout == "ABS Workout"){

                }else if (notif?.JenisWorkout == "Chest Workout"){

                }else if (notif?.JenisWorkout == "ARM Workout"){

                }
            }
        }
        binding.btnBack.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentListPesanNotif()).commit()
        }

        return binding.root
    }
}