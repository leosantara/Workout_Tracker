package id.ac.ukdw.workout_tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ukdw.workout_tracker.databinding.FragmentDetailNotifBinding
import id.ac.ukdw.workout_tracker.databinding.FragmentListPesanNotifBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a")
        val calendar = Calendar.getInstance()
        var tanggal = dateFormat.format(calendar.time)
        arguments?.let { args ->
            // Mengecek apakah data dengan key "notif" ada dalam argument
            if (args.containsKey("notif")) {

                // Mengambil objek NotifClass dari argument
                notif = args.getParcelable("notif")

                binding.txtTanggalNotif.text = millisecondsToDate(notif?.Waktu.toString(), dateFormat)
                binding.txtDeskripsi.text = "Selamat karena telah berhasil menyelesaikan pelatihan "+ notif?.JenisWorkout + " Workout"
                if (notif?.JenisWorkout == "ABS"){
                    binding.txtKalori.text = "Anda berhasil membakar 80 Kalori pada set kali ini"
                    binding.imageView3.setImageResource(R.drawable.img_abs_workout)
                }else if (notif?.JenisWorkout == "Chest"){
                    binding.txtKalori.text = "Anda berhasil membakar 100 Kalori pada set kali ini"
                    binding.imageView3.setImageResource(R.drawable.img_chest_workout)
                }else if (notif?.JenisWorkout == "ARM"){
                    binding.txtKalori.text = "Anda berhasil membakar 80 Kalori pada set kali ini"
                    binding.imageView3.setImageResource(R.drawable.img_arm_workout)
                }
            }
        }
        binding.btnBack.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentListPesanNotif()).commit()
        }
        return binding.root
    }
    private fun millisecondsToDate(milliseconds: String, dateFormat: SimpleDateFormat): String{
        val millis:Long = milliseconds.toLong()
        return dateFormat.format(millis)
    }

}