    package id.ac.ukdw.workout_tracker

    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.widget.Toast
    import androidx.fragment.app.FragmentManager
    import androidx.fragment.app.FragmentTransaction
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.DatabaseReference
    import com.google.firebase.database.FirebaseDatabase
    import com.google.firebase.database.GenericTypeIndicator
    import id.ac.ukdw.workout_tracker.databinding.ActivityMakananBinding
    import id.ac.ukdw.workout_tracker.databinding.ActivityTestBinding
    import java.text.SimpleDateFormat
    import java.util.Calendar
    import java.util.Locale

    class TestActivity : AppCompatActivity() {
        private lateinit var binding: ActivityTestBinding
        private lateinit var auth : FirebaseAuth
        private lateinit var databaseRef: DatabaseReference
        private lateinit var currentUserUid: String
        private lateinit var user : User

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityTestBinding.inflate(layoutInflater);

            binding.BarBottom.setOnItemSelectedListener { menuItem ->

                when (menuItem.itemId) {

                    R.id.btnHome -> {
                        true
                    }
                    R.id.btnPesan -> {

                        Intent(this, MainActivity::class.java).also {
                            it.putExtra("fragmentType", "FragmentPesan")
                            startActivity(it)
                        }
                        Toast.makeText(this, "Pesan", Toast.LENGTH_SHORT).show()
                        // Lakukan sesuatu ketika menu Chat dipilih
                        true
                    }
                    R.id.btnLainnya -> {
                        Intent(this, MainActivity::class.java).also {
                            it.putExtra("fragmentType", "FragmentLainnya")
                            startActivity(it)
                        }
                        Toast.makeText(this, "Lainnya", Toast.LENGTH_SHORT).show()
                        // Lakukan sesuatu ketika menu Lainnya dipilih
                        true
                    }
                    else -> false
                }
            }

            setContentView(binding.root)

            val fragmentResepMakanan = fragmentResepMakanan()
            val fragmentManager : FragmentManager = supportFragmentManager

            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.activity_login, fragmentResepMakanan).commit()

        }



    }

