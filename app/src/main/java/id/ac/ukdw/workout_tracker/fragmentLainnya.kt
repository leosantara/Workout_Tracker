package id.ac.ukdw.workout_tracker

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import id.ac.ukdw.workout_tracker.databinding.FragmentLainnyaBinding
import id.ac.ukdw.workout_tracker.databinding.FragmentSettingBinding
import java.io.File

class fragmentLainnya : Fragment() {
    private var _binding : FragmentLainnyaBinding? = null
    private lateinit var currentUserUid: String
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        currentUserUid = auth.currentUser?.uid.toString()
        val storageRefe = FirebaseStorage.getInstance().getReference(currentUserUid+"/profil")

        val  localfile = File.createTempFile("profil", "jpg")
        storageRefe.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgFotoUser.setImageBitmap(bitmap)
        }

        databaseRef = FirebaseDatabase.getInstance().getReference("users")
        var userRef = databaseRef.child(currentUserUid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val namaa = snapshot.child("nama").getValue(String::class.java)
                binding.txtNama.text = namaa
                Toast.makeText(requireContext(),namaa.toString(), Toast.LENGTH_SHORT).show()
                for (userSnapshot in snapshot.children) {
                    val nama = userSnapshot.child("nama").getValue(String::class.java)
                    if (nama != null) {
                        Log.d("Nama Pengguna", nama)
                        binding.txtNama.text = nama
                    } else {
                        Log.e("Error", "Nama null atau kosong")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
                Toast.makeText(requireContext(), "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        _binding = FragmentLainnyaBinding.inflate(inflater, container, false)

        binding.btnSetting.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentSetting()).commit()
        }
        return binding.root
    }

}