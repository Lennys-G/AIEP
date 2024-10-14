package cl.aiep.loginsession

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inicializar Firebase Auth
        val auth = Firebase.auth

        // Inicializar Realtime Database y referencia del usuario autenticado
        val currentUser = auth.currentUser
        database = FirebaseDatabase.getInstance().getReference("users/${currentUser?.uid}")

        // Inicializar el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Evento click del botón de logout
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Sesión cerrada.", Toast.LENGTH_SHORT).show() //Muestra mensaje de cierre de sesión
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) //Devuelve la pantala de inicio de sesipon
            finish() //Cierra la vista de home
        }
        val btnCalcularDespacho = findViewById<Button>(R.id.btnCalcularDespacho)
        btnCalcularDespacho.setOnClickListener{
            val intent = Intent(this, CalcularDespachoActivity::class.java)
            startActivity(intent)
        }
        val btnMapsView = findViewById<Button>(R.id.btnMapsView)
        btnMapsView.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        // Verificar permisos de ubicación
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permiso ya concedido, obtener la ubicación
            getLocation()
        } else {
            // Solicitar el permiso
            requestLocationPermission()
        }
    }

    //Solicita al usuario el permiso para acceder a su ubicación
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, obtener la ubicación
                getLocation()
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show() //Muestra mensaje de permiso denegado
            }
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Obtener la última ubicación conocida
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    // Mostrar la ubicación en el TextView
                    val latLng = "Latitud: ${location.latitude}, Longitud: ${location.longitude}"

                    // Guardar la ubicación en Firebase Realtime Database
                    saveLocationToFirebase(location.latitude, location.longitude)

                } else {
                    Toast.makeText(this, "No se pudo obtener la ubicación.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "No se pudo obtener la ubicación.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para guardar la ubicación en Firebase
    private fun saveLocationToFirebase(latitude: Double, longitude: Double) {
        val userLocation = mapOf(
            "latitude" to latitude,
            "longitude" to longitude
        )

        // Mostrar mensaje de éxito o error al guardar la ubicación
        database.child("location").setValue(userLocation)
            .addOnSuccessListener {
                Toast.makeText(this, "Ubicación guardada correctamente.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al guardar la ubicación.", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}
