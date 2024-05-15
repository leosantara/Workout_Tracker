package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import id.ac.ukdw.workout_tracker.R
import id.ac.ukdw.workout_tracker.databinding.FragmentHomeBinding
import java.io.File

class fragmentHome : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var currentUserUid: String
    private lateinit var user : User
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
//                        val  localfile = File.createTempFile("profil", "jpg")
//                        if (storageRefe != null && localfile != null){
//                            storageRefe.getFile(localfile).addOnSuccessListener {
//                                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
//                                if (bitmap != null){
//                                    binding.imgProfil.setImageBitmap(bitmap)
//                                }else {
//                                    binding.imgProfil.setImageResource(R.drawable.app_profil)
//                                }
//                            }
//                        }
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

                        databaseRef = FirebaseDatabase.getInstance().getReference("users")
                        var userRef = databaseRef.child(currentUserUid)
                        databaseRef.child(currentUserUid).get().addOnSuccessListener {
                            if (it.exists()){
                                var nama = it.child("nama").value
                                binding.txtNamaPengguna.text = nama.toString()
                            }
                        }
//                        if (databaseRef != null && userRef != null){
//                            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
//                                override fun onDataChange(snapshot: DataSnapshot) {
//                                    var namaa = snapshot.child("nama").getValue(String::class.java)
//                                    if (namaa != null) {
//                                        user = User(namaa)
//                                        if (user.getNama() != null){
//                                            binding.txtNamaPengguna.text = user.getNama()
//
//                                        }else {
//                                            binding.txtNamaPengguna.text = "Nama Pengguna"
//                                        }
//                                    } else {
//                                        binding.txtNamaPengguna.text = "Nama Pengguna"
//                                    }
//                                }
//                                override fun onCancelled(error: DatabaseError) {
//                                    // Handle onCancelled event
//                                    Toast.makeText(requireContext(), "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
//                                }
//                            })
//                        }

                    }
                }
            }
            // Inflate the layout for this fragment



            binding.btnResepMakanan.setOnClickListener {
                Toast.makeText(requireContext(), "Resep", Toast.LENGTH_SHORT).show()
                Intent(requireContext(), MakananActivity::class.java).also {
                    startActivity(it)
                }
            }

            binding.btnABSWorkout.setOnClickListener {
                Intent(getContext(), WorkoutActivity::class.java).also {
                    it.putExtra("fragmentType", "FragmentA")
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

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
