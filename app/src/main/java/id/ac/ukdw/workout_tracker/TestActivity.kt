package id.ac.ukdw.workout_tracker

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import id.ac.ukdw.workout_tracker.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                    // Izin sudah diberikan
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Tampilkan dialog yang menjelaskan mengapa aplikasi memerlukan izin ini
                }
                else -> {
                    // Minta izin secara langsung
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }

        binding.BarBottom.selectedItemId = R.id.btnPesan

        binding.BarBottom.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnHome -> {
//                    NotificationHelper(this, "aha").notifyUser("ABS", 1715736420000)
//                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
//                    Toast.makeText(this, "biji is send", Toast.LENGTH_SHORT).show()
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentHome")
                        it.putExtra("fragmentType", "FragmentHome")
                        it.putExtra("selectedItemId", R.id.btnHome)
                        it.putExtra("selectedItemIdd", R.id.btnHome)
                        startActivity(it)
                    }
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.btnPesan -> {
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentPesan")
                        it.putExtra("selectedItemId", R.id.btnPesan)
                        it.putExtra("selectedItemIdd", R.id.btnPesan)
                        startActivity(it)
                    }
                    Toast.makeText(this, "Pesan", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.btnLainnya -> {
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentLainnya")
                        it.putExtra("selectedItemId", R.id.btnLainnya)
                        it.putExtra("selectedItemIdd", R.id.btnLainnya)
                        startActivity(it)
                    }
                    Toast.makeText(this, "Lainnya", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Lainnya", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        handleIntent(intent)

        setContentView(binding.root)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Izin diberikan
        } else {
            // Izin ditolak
            Toast.makeText(this, "Izin untuk notifikasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleIntent(intent: Intent?) {
        intent?.let {
            val jenisWorkout = it.getStringExtra("JenisWorkout")
            val date = it.getLongExtra("Date", -1)
            if (jenisWorkout != null && date != -1L) {
                // Kirim data ke fragment
                val fragment = fragmentDetailPushNotifikasi().apply {
                    arguments = Bundle().apply {
                        putString("JenisWorkout", jenisWorkout)
                        putLong("Date", date)
                    }
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.TestActivity, fragment)  // Pastikan ID ini sesuai dengan ID kontainer di layout
                    .commit()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }
}
