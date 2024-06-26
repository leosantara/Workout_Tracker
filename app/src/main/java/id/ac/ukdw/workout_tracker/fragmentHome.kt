package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import id.ac.ukdw.workout_tracker.databinding.FragmentHomeBinding
import id.ac.ukdw.workout_tracker.util.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class fragmentHome : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var currentUserUid: String
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        if (_binding == null) {
            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            if (_binding != null){
                auth = FirebaseAuth.getInstance()
                if (auth != null){
                    currentUserUid = auth.currentUser?.uid.toString()
                    if (currentUserUid != null){
                        val storageRefe = FirebaseStorage.getInstance().getReference(currentUserUid+"/profil")
                        storageRefe.downloadUrl.addOnSuccessListener { uri ->
                            // Gunakan Picasso untuk memuat gambar dari URL
                            Picasso.get()
                                .load(uri)
                                .placeholder(R.drawable.app_profil) // Gambar placeholder saat gambar sedang dimuat
                                .error(R.drawable.app_profil)       // Gambar jika terjadi kesalahan
                                .into(binding.imgProfil)            // ImageView di mana gambar akan dimuat
                        }.addOnFailureListener {
                            // Tangani kesalahan jika tidak dapat mendapatkan URL
                            binding.imgProfil.setImageResource(R.drawable.app_profil)
                        }

                        // Untuk mendapatkan referensi ke node "users" di Firebase Realtime Database
                        databaseRef = FirebaseDatabase.getInstance().getReference("users")
                        databaseRef.child(currentUserUid).get().addOnSuccessListener {
                            // Untuk memeriksa apakah data pengguna dengan UID tersebut ada
                            if (it.exists()){
                                var nama = it.child("nama").value
                                binding.txtNamaPengguna.text = nama.toString()
                            }
                        }
                    }
                }
            }
            // Inflate the layout for this fragment



            binding.btnResepMakanan.setOnClickListener {
                Toast.makeText(requireContext(), "Resep", Toast.LENGTH_SHORT).show()
                Intent(requireContext(), MakananActivity::class.java).also {
                    it.putExtra("fragmentType", "FragmentA")
                    startActivity(it)
                }
            }

            binding.btnABSWorkout.setOnClickListener {
                Intent(getContext(), WorkoutActivity::class.java).also {
                    it.putExtra("fragmentType", "FragmentA")
                    startActivity(it)
                }
            }

            binding.btnTambahMakanan.setOnClickListener{
                Intent(requireContext(), MakananActivity::class.java).also {
                    it.putExtra("fragmentType", "FragmentB")
                    startActivity(it)
                }
            }

            binding.btnARMWorkout.setOnClickListener{
                Intent(getContext(), WorkoutActivity::class.java).also {
                    it.putExtra("fragmentType", "FragmentB")
                    startActivity(it)
                }
            }
            binding.btnChestWorkout.setOnClickListener {
                Intent(getContext(), WorkoutActivity::class.java).also {
                    it.putExtra("fragmentType", "FragmentC")
                    startActivity(it)
                }
            }
        }
        getCurrentWeather()
        return binding.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCurrentWeather() {
        GlobalScope.launch(Dispatchers.IO){
            val response = try{
                RetrofitInstance.api.getCurrentWeather("-7.795580", "110.369492", "36d38ea2926714eeda638f01b699af0e")
            }catch (e: IOException){
                return@launch
            }
            if (response.isSuccessful && response.body()!= null){
                withContext(Dispatchers.Main){
                    binding.txtCuaca.text = "Cuaca : ${response.body()!!.weather.get(0).description}"
                }
            }
        }
    }

}
