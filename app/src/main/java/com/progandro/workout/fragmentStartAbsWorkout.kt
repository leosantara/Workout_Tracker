package com.progandro.workout

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.progandro.workout.databinding.FragmentSaladBuahBinding
import com.progandro.workout.databinding.FragmentStartAbsWorkoutBinding
import java.util.concurrent.TimeUnit


class fragmentStartAbsWorkout : Fragment() {
    private var _binding : FragmentStartAbsWorkoutBinding? = null
    private val binding get() = _binding!!

    private val imagePaths = listOf(
        "drawable/img_absworkout_big1",
        "drawable/img_absworkout_big2",
        "drawable/img_absworkout_big3",
        "drawable/img_absworkout_big4",
        "drawable/img_absworkout_big5",
        "drawable/img_absworkout_big6"
    )

    private val namaGerakan = listOf(
        "Jumping Jacks",
        "Abdominal Crunches",
        "Russian Twitst",
        "Mountain Climber",
        "Leg Raises",
        "Plank"
    )

    private val tipeGerakan = listOf(
        "Step",
        "Step",
        "Step",
        "Step",
        "Waktu",
        "Waktu"
    )

    private val lamaWaktu = listOf(
        0,
        0,
        0,
        0,
        15,
        30
    )

    private val deskripsiGerakan = listOf(
        "40 reps of Jumping Jacks",
        "20 reps of Abdominal Crunches",
        "60 reps of Russian Twitst",
        "20 reps of Mountain Climber",
        "15 sec Raises",
        "30 sec Plank"
    )

    private var COUNTDOWN_TIME_MS = TimeUnit.SECONDS.toMillis(0)

    // Mendefinisikan interval waktu untuk setiap pembaruan (1 detik)
    private val COUNTDOWN_INTERVAL_MS = TimeUnit.SECONDS.toMillis(1)

    // Mendefinisikan apakah hitungan mundur sudah berakhir
    private var isCountdownFinished = false
    private var currentImageIndex = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartAbsWorkoutBinding.inflate(inflater, container, false)

        binding.imgFotoGerakan.setImageResource(getDrawableId(imagePaths[currentImageIndex]))


        binding.btnNext.setOnClickListener{
            binding.txtTime.visibility = View.INVISIBLE
            currentImageIndex++

            // Check if it exceeds the list size
            if (currentImageIndex >= imagePaths.size) {
                // Reset the index to the first image
                currentImageIndex = 0

            } else {

                if (currentImageIndex + 1 == imagePaths.size) {
                    // Reset the index to the first image
                    binding.btnNext.text = "Finish"
                    Toast.makeText(requireContext(), "Tugas Terakhir", Toast.LENGTH_SHORT).show()
                }
                if (tipeGerakan[currentImageIndex] == "Waktu"){
                    binding.btnNext.isEnabled = false
                    binding.txtTime.visibility = View.VISIBLE
                    COUNTDOWN_TIME_MS = TimeUnit.SECONDS.toMillis(lamaWaktu[currentImageIndex].toLong())
                    startCountdown()
                }
                // Set the next image
                binding.txtNamaGerakan.text = namaGerakan[currentImageIndex]
                binding.txtDeskripsi.text = deskripsiGerakan[currentImageIndex]
                binding.imgFotoGerakan.setImageResource(getDrawableId(imagePaths[currentImageIndex]))
            }

        }

        binding.btnBack.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.workout_activity, fragmentAbsWorkout()).commit()
        }

        binding.BarBottom.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnHome -> {
                    Intent(getContext(), MainActivity::class.java).also {
                        startActivity(it)
                    }
                    Toast.makeText(requireContext(), "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.btnPesan -> {
                    Toast.makeText(requireContext(), "Pesan", Toast.LENGTH_SHORT).show()
                    // Lakukan sesuatu ketika menu Chat dipilih
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

    private fun getDrawableId(name: String): Int {
        return resources.getIdentifier(name, "drawable", context?.packageName ?:null )
    }

    private fun startCountdown() {
        object : CountDownTimer(COUNTDOWN_TIME_MS, COUNTDOWN_INTERVAL_MS) {
            override fun onTick(millisUntilFinished: Long) {
                // Menghitung waktu yang tersisa dalam detik
                val secondsLeft = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

                // Mengatur teks pada TextView dengan waktu mundur yang tersisa
                binding.txtTime.text = secondsLeft.toString()+"s"
            }

            override fun onFinish() {
                // Mengatur teks pada TextView ketika hitungan mundur selesai
                binding.txtTime.text = "0s"

                // Mengaktifkan tombol ketika hitungan mundur selesai
                binding.btnNext.isEnabled = true

                // Menandai bahwa hitungan mundur sudah selesai
                isCountdownFinished = true
            }
        }.start()
    }
}