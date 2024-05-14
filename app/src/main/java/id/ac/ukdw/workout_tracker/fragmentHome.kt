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
import id.ac.ukdw.workout_tracker.R
import id.ac.ukdw.workout_tracker.databinding.FragmentHomeBinding
import java.io.File

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
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        currentUserUid = auth.currentUser?.uid.toString()
        val storageRefe = FirebaseStorage.getInstance().getReference(currentUserUid+"/profil")

        val  localfile = File.createTempFile("profil", "jpg")
        storageRefe.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgProfil.setImageBitmap(bitmap)
        }
        databaseRef = FirebaseDatabase.getInstance().getReference("users")
        var userRef = databaseRef.child(currentUserUid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val namaa = snapshot.child("nama").getValue(String::class.java)
                binding.txtNamaPengguna.text = namaa
                Toast.makeText(requireContext(),namaa.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
                Toast.makeText(requireContext(), "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })


        _binding = FragmentHomeBinding.inflate(inflater, container, false)


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

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
