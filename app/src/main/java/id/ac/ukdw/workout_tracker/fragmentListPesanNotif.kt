package id.ac.ukdw.workout_tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import id.ac.ukdw.workout_tracker.databinding.FragmentListPesanNotifBinding

class fragmentListPesanNotif : Fragment() {

    private var notifList : ArrayList<NotifClass> = arrayListOf()
    private lateinit var notifAdapter : NotifAdapter
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var currentUserUid: String
    private var _binding : FragmentListPesanNotifBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListPesanNotifBinding.inflate(inflater, container, false)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        auth = FirebaseAuth.getInstance()
        currentUserUid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().getReference("users")

        fetchNotifications()
        notifAdapter = NotifAdapter(notifList)
        binding.recyclerView.adapter = notifAdapter

        notifAdapter.onItemClick = {
            val fragment = fragmentDetailNotif()
            val args = Bundle().apply {
                putParcelable("notif", it)
            }
            fragment.arguments = args
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragment).commit()
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun fetchNotifications() {
        databaseRef.get().addOnSuccessListener { snapshot ->
            for (userSnapshot in snapshot.children) {
                val notifSnapshot = userSnapshot.child("notifikasi")
                if (notifSnapshot.exists()) {
                    val t = object : GenericTypeIndicator<List<List<Any>>>() {}
                    val notifications = notifSnapshot.getValue(t)
                    if (notifications != null) {
                        for (notification in notifications) {
                            val workoutType = notification[0] as? String ?: continue
                            val time = notification[1] as? Long ?: continue
                            notifList.add(NotifClass(workoutType, time))
                        }
                    }
                }
            }
            // Handle notifList (e.g., update UI)
            notifAdapter.notifyDataSetChanged()
        }.addOnFailureListener {

        }
    }
}