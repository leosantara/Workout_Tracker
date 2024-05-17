package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import id.ac.ukdw.workout_tracker.databinding.FragmentStartWorkoutBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit


class fragmentStartWorkout : Fragment() {
    private var _binding : FragmentStartWorkoutBinding? = null
    private var listType: String? = null
    private lateinit var currentUserUid: String
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var namaGerakan: List<String>
    private lateinit var tipeGerakan: List<String>
    private lateinit var deskripsiGerakan: List<String>
    private lateinit var lamaWaktu: List<Long>
    private lateinit var imagePaths: List<String>

    private val binding get() = _binding!!
    private var COUNTDOWN_TIME_MS = TimeUnit.SECONDS.toMillis(0)

    // Mendefinisikan interval waktu untuk setiap pembaruan (1 detik)
    private val COUNTDOWN_INTERVAL_MS = TimeUnit.SECONDS.toMillis(1)

    // Mendefinisikan apakah hitungan mundur sudah berakhir
    private var isCountdownFinished = false
    private var currentImageIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listType = it.getString(fragmentStartWorkout.ARG_LIST_TYPE)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartWorkoutBinding.inflate(inflater, container, false)


        namaGerakan = when (listType){
            "A" ->  listOf(
                "Jumping Jacks",
                "Abdominal Crunches",
                "Russian Twitst",
                "Mountain Climber",
                "Leg Raises",
                "Plank"
            )
            "B" ->  listOf(
                "Tricep Dips",
                "Push Up",
                "Side Plank",
                "Burpees",
                "Triceps Stretch",
                "Biceps Stretch"
            )
            "C" ->  listOf(
                "Jumping Jacks",
                "Incline Push-ups",
                "Diamond Push-ups",
                "Decline Push-ups",
                "Knee Push-ups",
                "Chest Stretch"
            )
            else -> arrayListOf()
        }
        tipeGerakan = when (listType){
            "A" ->  listOf(
                "Step",
                "Step",
                "Step",
                "Step",
                "Waktu",
                "Waktu"
            )
            "B" ->  listOf(
                "Waktu",
                "Step",
                "Waktu",
                "Step",
                "Waktu",
                "Waktu"
            )
            "C" ->  listOf(
                "Step",
                "Step",
                "Step",
                "Step",
                "Step",
                "Waktu"
            )
            else -> arrayListOf()
        }

        deskripsiGerakan = when (listType){
            "A" ->  listOf(
                "40 reps of Jumping Jacks",
                "20 reps of Abdominal Crunches",
                "60 reps of Russian Twitst",
                "20 reps of Mountain Climber",
                "15 sec Raises",
                "30 sec Plank"
            )
            "B" ->  listOf(
                "15 sec hold Tricep Dips",
                "10 reps of Push ups",
                "20 sec Side Plank",
                "5 reps of Burpees",
                "15 sec Triceps Stretch",
                "15 sec Biceps Stretch"
            )
            "C" ->  listOf(
                "40 reps of Jumping Jacks",
                "20 reps of Incline Push-ups",
                "15 reps of Diamond Push-ups",
                "15 reps of Decline Push-ups",
                "20 reps of Knee Push-ups",
                "20 sec Chest Stretch"
            )
            else -> arrayListOf()
        }

        lamaWaktu = when (listType){
            "A" ->  listOf(
                0,
                0,
                0,
                0,
                15,
                30
            )
            "B" ->  listOf(
                15,
                0,
                20,
                0,
                15,
                15
            )
            "C" ->  listOf(
                0,
                0,
                0,
                0,
                0,
                20
            )
            else -> arrayListOf()
        }

        imagePaths = when (listType){
            "A" ->  listOf(
                "drawable/img_absworkout_big1",
                "drawable/img_absworkout_big2",
                "drawable/img_absworkout_big3",
                "drawable/img_absworkout_big4",
                "drawable/img_absworkout_big5",
                "drawable/img_absworkout_big6"
            )
            "B" ->  listOf(
                "drawable/img_armworkout_big1",
                "drawable/img_armworkout_big2",
                "drawable/img_armworkout_big3",
                "drawable/img_armworkout_big4",
                "drawable/img_armworkout_big5",
                "drawable/img_armworkout_big6"
            )
            "C" ->  listOf(
                "drawable/img_chest_big1",
                "drawable/img_chest_big2",
                "drawable/img_chest_big3",
                "drawable/img_chest_big4",
                "drawable/img_chest_big5",
                "drawable/img_chest_big6"
            )
            else -> arrayListOf()
        }
        binding.imgFotoGerakan.setImageResource(getDrawableId(imagePaths[currentImageIndex]))

        if (currentImageIndex == 0){
            binding.txtNamaGerakan.text = namaGerakan[currentImageIndex]
            binding.txtDeskripsi.text = deskripsiGerakan[currentImageIndex]
            binding.imgFotoGerakan.setImageResource(getDrawableId(imagePaths[currentImageIndex]))

            if (tipeGerakan[currentImageIndex] == "Waktu"){
                Toast.makeText(requireContext(), "tes", Toast.LENGTH_SHORT).show()
                binding.btnNext.isEnabled = false
                binding.txtTime.visibility = View.VISIBLE
                COUNTDOWN_TIME_MS = TimeUnit.SECONDS.toMillis(lamaWaktu[currentImageIndex])
                startCountdown()
            }
        }
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

                    binding.btnNext.setOnClickListener {
                        Intent(getContext(), MainActivity::class.java).also {
                            it.putExtra("fragmentType", "FragmentHome")
                            startActivity(it)
                        }

                        var workouto : String = when (listType){
                            "A" -> "abs"
                            "B" -> "arm"
                            "C" -> "chest"
                            else -> String()
                        }
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a")
                        val calendar = Calendar.getInstance()
                        var tanggal = dateFormat.format(calendar.time)
                        auth = FirebaseAuth.getInstance()
                        currentUserUid = auth.currentUser?.uid.toString()
                        databaseRef = FirebaseDatabase.getInstance().getReference("users")
                        if (workouto == "abs"){
                            addNotification("ABS", dateToMilliseconds(tanggal,dateFormat))
                        }else if (workouto == "arm"){
                            addNotification("ARM", dateToMilliseconds(tanggal,dateFormat))
                        }else if (workouto == "chest"){
                            addNotification("Chest", dateToMilliseconds(tanggal,dateFormat))
                        }

                        var userRef = databaseRef.child(currentUserUid)
                        databaseRef.child(currentUserUid).get().addOnSuccessListener {
                            if (it.exists()){
                                var workoutooo = it.child(workouto).value.toString()
                                var nambah = workoutooo.toInt() + 1
                                val userData = hashMapOf<String, Any>(
                                    workouto to nambah.toString()
                                )
                                userRef.updateChildren(userData)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            requireContext(),
                                            "Berhasil Mengupdate Data",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            requireContext(),
                                            "Gagal Mengupdate Data",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                            }
                        }
                    }
                }
                if (tipeGerakan[currentImageIndex] == "Waktu"){

                    binding.btnNext.isEnabled = false
                    binding.txtTime.visibility = View.VISIBLE
                    COUNTDOWN_TIME_MS = TimeUnit.SECONDS.toMillis(lamaWaktu[currentImageIndex])
                    startCountdown()
                }
                // Set the next image
                binding.txtNamaGerakan.text = namaGerakan[currentImageIndex]
                binding.txtDeskripsi.text = deskripsiGerakan[currentImageIndex]
                binding.imgFotoGerakan.setImageResource(getDrawableId(imagePaths[currentImageIndex]))
            }

        }

        binding.btnBack.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.workout_activity, fragmentWorkout.newInstance(listType!!))
                .commit()
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

    private fun addNotification(workoutType: String, time: Long) {
        val notificationRef = databaseRef.child(currentUserUid).child("notifikasi")

        notificationRef.get().addOnSuccessListener { snapshot ->
            // Menggunakan GenericTypeIndicator untuk menangani list dengan tipe generic
            val t = object : GenericTypeIndicator<List<List<Any>>>() {}
            val currentList: MutableList<List<Any>> = snapshot.getValue(t)?.toMutableList() ?: mutableListOf()
            val newNotification = listOf(workoutType, time)

            currentList.add(newNotification)

            notificationRef.setValue(currentList).addOnSuccessListener {
                Toast.makeText(context, "Notification added successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to add notification", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to retrieve current notifications", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val ARG_LIST_TYPE = "list_type"

        @JvmStatic
        fun newInstancet(listType: String) =
            fragmentStartWorkout().apply {
                arguments = Bundle().apply {
                    putString(ARG_LIST_TYPE, listType)
                }
            }
    }
    private fun dateToMilliseconds(date: String, dateFormat:SimpleDateFormat): Long {
        val mDate = dateFormat.parse(date)
        return mDate.time
    }
}