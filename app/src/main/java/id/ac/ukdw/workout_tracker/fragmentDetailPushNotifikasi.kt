package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ukdw.workout_tracker.databinding.FragmentDetailPushNotifikasiBinding
import java.text.SimpleDateFormat

class fragmentDetailPushNotifikasi : Fragment() {
    private var _binding: FragmentDetailPushNotifikasiBinding? = null
    private val binding get() = _binding!!
    private var notif: NotifClass? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailPushNotifikasiBinding.inflate(inflater, container, false)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a")

        val jenisWorkout = arguments?.getString("JenisWorkout")
        val date = arguments?.getLong("Date")

        binding.txtTanggalNotif.text = millisecondsToDate(date.toString(), dateFormat)
        binding.txtDeskripsi.text = "Selamat karena telah berhasil menyelesaikan pelatihan $jenisWorkout Workout"
        if (jenisWorkout == "ABS") {
            binding.txtKalori.text = "Anda berhasil membakar 80 Kalori pada set kali ini"
            binding.imageView3.setImageResource(R.drawable.img_abs_workout)
        } else if (jenisWorkout == "Chest") {
            binding.txtKalori.text = "Anda berhasil membakar 100 Kalori pada set kali ini"
            binding.imageView3.setImageResource(R.drawable.img_chest_workout)
        } else if (jenisWorkout == "ARM") {
            binding.txtKalori.text = "Anda berhasil membakar 80 Kalori pada set kali ini"
            binding.imageView3.setImageResource(R.drawable.img_arm_workout)
        }

        binding.btnBack.setOnClickListener{
            Intent(getContext(), MainActivity::class.java).also {
                it.putExtra("fragmentType", "FragmentPesan")
                it.putExtra("selectedItemId", R.id.btnPesan)
                it.putExtra("selectedItemIdd", R.id.btnPesan)
                startActivity(it)
            }
        }

        return binding.root
    }

    private fun millisecondsToDate(milliseconds: String, dateFormat: SimpleDateFormat): String {
        val millis: Long = milliseconds.toLong()
        return dateFormat.format(millis)
    }
}
