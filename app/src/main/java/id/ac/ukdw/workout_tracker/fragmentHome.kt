package id.ac.ukdw.workout_tracker

import android.content.Intent
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
import id.ac.ukdw.workout_tracker.R
import id.ac.ukdw.workout_tracker.databinding.FragmentHomeBinding

class fragmentHome : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var currentUserUid: String
    private lateinit var user : FirebaseUser

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        user = FirebaseAuth.getInstance().currentUser!!

        databaseRef = FirebaseDatabase.getInstance().getReference("users")
        currentUserUid = auth.currentUser?.uid.toString()

        var userRef = databaseRef.child(currentUserUid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(requireContext(),"jadi ga ya", Toast.LENGTH_SHORT).show()
                val namaa = snapshot.child("nama").getValue(String::class.java)
                binding.txtNamaPengguna.text = namaa
                Toast.makeText(requireContext(),namaa.toString(), Toast.LENGTH_SHORT).show()
                for (userSnapshot in snapshot.children) {
                    Toast.makeText(requireContext(),"jadi lah ya ", Toast.LENGTH_SHORT).show()
                    val nama = userSnapshot.child("nama").getValue(String::class.java)
                    if (nama != null) {
                        Toast.makeText(requireContext(),nama.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("Nama Pengguna", nama)
                        binding.txtNamaPengguna.text = nama
                    } else {
                        Toast.makeText(requireContext(),"lawak ga jadi", Toast.LENGTH_SHORT).show()
                        Log.e("Error", "Nama null atau kosong")
                    }
                }
            }

                override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
                Toast.makeText(requireContext(), "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })


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
