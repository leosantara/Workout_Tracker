package id.ac.ukdw.workout_tracker

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.ac.ukdw.workout_tracker.databinding.FragmentRegisterBinding


@Suppress("UNREACHABLE_CODE")
class fragment_register : Fragment() {

    var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
            auth = FirebaseAuth.getInstance()
            databaseRef = FirebaseDatabase.getInstance().reference.child("users")
            binding.btnDaftar.setOnClickListener{
                val email = binding.txtRegisEmail.text.toString()
                val pass = binding.txtRegisPass.text.toString()
                val passConfirm = binding.txtRegisPassConfirm.text.toString()

                // Memeriksa apakah variabel email kosong
                if (email.isEmpty()) {
                    // Menampilkan pesan error pada txtRegisEmail dengan pesan "Email Wajib Diisi"
                    binding.txtRegisEmail.error = "Email Wajib Diisi"
                    // Memfokuskan kembali ke txtRegisEmail agar pengguna langsung diarahkan untuk mengisi email
                    binding.txtRegisEmail.requestFocus()
                    // Menghentikan eksekusi lebih lanjut dalam blok setOnClickListener
                    return@setOnClickListener
                }


                // Memeriksa apakah format email yang dimasukkan valid
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Jika email tidak sesuai dengan pola yang valid, tampilkan pesan error pada komponen txtRegisEmail
                    // 'txtRegisEmail' adalah referensi ke komponen input email dalam tampilan
                    binding.txtRegisEmail.error = "Email Tidak Valid"

                    // Mengarahkan fokus ke komponen input email (txtRegisEmail)
                    // agar pengguna segera memperbaiki input email yang salah
                    binding.txtRegisEmail.requestFocus()

                    // Menghentikan eksekusi dari event setOnClickListener
                    // agar kode selanjutnya tidak dijalankan sampai email valid dimasukkan
                    return@setOnClickListener
                }

                if (pass.isEmpty()){
                    binding.txtRegisPass.error = "Password Wajib Diisi"
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
//                if(it.isSuccessful){
//                    Toast.makeText(requireContext(), "Register Berhasil", Toast.LENGTH_SHORT).show()
//                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.activity_login, fragment_login()).commit()
//
//                }
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid

                    if (uid != null) {
                        val userName = email.substringBefore("@")
                        val userData = mapOf(
                            "abs" to "0",
                            "arm" to "0",
                            "chest" to "0",
                            "email" to email,
                            "nama" to userName,
                            "notifikasi" to emptyList<List<Any>>()
                        )

                        databaseRef.child(uid).setValue(userData)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(requireContext(), "Register Berhasil", Toast.LENGTH_SHORT).show()
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.activity_login, fragment_login())
                                        .commit()
                                } else {
                                    Toast.makeText(requireContext(), "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(requireContext(), "Register Gagal", Toast.LENGTH_SHORT).show()
                }
            }

    }

}

