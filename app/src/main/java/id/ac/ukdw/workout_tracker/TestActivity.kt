package id.ac.ukdw.workout_tracker

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import id.ac.ukdw.workout_tracker.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    private val TAG = "TestActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = ActivityTestBinding.inflate(layoutInflater)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                    Log.d(TAG, "Notification permission granted")
                    // Izin sudah diberikan
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    Log.d(TAG, "Showing rationale for notification permission")
                    // Tampilkan dialog yang menjelaskan mengapa aplikasi memerlukan izin ini
                }
                else -> {
                    Log.d(TAG, "Requesting notification permission")
                    // Minta izin secara langsung
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }

        binding.BarBottom.selectedItemId = R.id.btnPesan

        binding.BarBottom.setOnItemSelectedListener { menuItem ->
            Log.d(TAG, "Bottom navigation item selected: ${menuItem.title}")
            when (menuItem.itemId) {
                R.id.btnHome -> {
                    Log.d(TAG, "Menu Home selected")
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentHome")
                        it.putExtra("selectedItemId", R.id.btnHome)
                        startActivity(it)
                    }
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.btnPesan -> {
                    Log.d(TAG, "Menu Pesan selected")
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentPesan")
                        it.putExtra("selectedItemId", R.id.btnPesan)
                        startActivity(it)
                    }
                    Toast.makeText(this, "Pesan", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.btnLainnya -> {
                    Log.d(TAG, "Menu Lainnya selected")
                    Intent(this, MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentLainnya")
                        it.putExtra("selectedItemId", R.id.btnLainnya)
                        startActivity(it)
                    }
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
            Log.d(TAG, "Notification permission granted")
            // Izin diberikan
        } else {
            Log.d(TAG, "Notification permission denied")
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
