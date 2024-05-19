package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import id.ac.ukdw.workout_tracker.databinding.FragmentLoginBinding


class   fragment_login : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentLoginBinding.inflate(inflater, container, false)


        binding.btnRegister.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.activity_login, fragment_register()).commit()
        }


        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener{
            val email = binding.txtLoginEmail.text.toString()
            val password = binding.txtLoginPass.text.toString()

            //Validasi email
            if (email.isEmpty()){
                binding.txtLoginEmail.error = "Email Harus Diisi"
                binding.txtLoginEmail.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.txtLoginPass.error = "Email Tidak Valid"
                binding.txtLoginPass.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()){
                binding.txtLoginPass.error = "Password Harus Diisi"
                binding.txtLoginPass.requestFocus()
                return@setOnClickListener
            }
            LoginFirebase(email,password)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) {task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    Intent(getContext(), MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentHome")
                        it.putExtra("selectedItemId", R.id.btnHome)
                        startActivity(it)
                    }
                } else {
                    Toast.makeText(requireContext(), "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


}