package id.ac.ukdw.workout_tracker

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import id.ac.ukdw.workout_tracker.R
import id.ac.ukdw.workout_tracker.databinding.FragmentRegisterBinding


@Suppress("UNREACHABLE_CODE")
class fragment_register : Fragment() {

    var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
            auth = FirebaseAuth.getInstance()
            binding.btnDaftar.setOnClickListener{
                val email = binding.txtRegisEmail.text.toString()
                val pass = binding.txtRegisPass.text.toString()
                val passConfirm = binding.txtRegisPassConfirm.text.toString()

                if (email.isEmpty()){
                    binding.txtRegisEmail.error = "Email Harus Diisi"
                    binding.txtRegisEmail.requestFocus()
                    return@setOnClickListener
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.txtRegisEmail.error = "Email Tidak Valid"
                    binding.txtRegisEmail.requestFocus()
                    return@setOnClickListener
                }

                if (pass.isEmpty()){
                    binding.txtRegisPass.error = "Password Harus Diisi"
                    binding.txtRegisPass.requestFocus()
                    return@setOnClickListener
                }

                if (pass.length < 6) {
                    binding.txtRegisPass.error = "Password Minimal 6 Karakter"
                    binding.txtRegisPass.requestFocus()
                    return@setOnClickListener
                }

                if (pass != passConfirm){
                    binding.txtRegisPassConfirm.error = "Password Konfirmasi Beda dengan Password"
                    binding.txtRegisPassConfirm.requestFocus()
                    return@setOnClickListener
                }

                RegisterFirebase(email, pass)

            }
        return binding.root


    }

    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()){
                if(it.isSuccessful){
                    Toast.makeText(requireContext(), "Register Berhasil", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.activity_login, fragment_login()).commit()

                }

            }

    }




}