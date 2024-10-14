package cl.aiep.loginsession

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Inicializar Firebase Database
        val currentUser = Firebase.auth.currentUser
        database = FirebaseDatabase.getInstance().getReference("users/${currentUser?.uid}/location")

        // Inicializar el fragmento de Google Maps
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Leer ubicación desde Firebase y mostrarla en el mapa
        leerUbicacionDesdeFirebase()
    }

    private fun leerUbicacionDesdeFirebase() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener latitud y longitud almacenados en Firebase
                    val latitud = dataSnapshot.child("latitude").getValue(Double::class.java)
                    val longitud = dataSnapshot.child("longitude").getValue(Double::class.java)

                    if (latitud != null && longitud != null) {
                        // Crear una ubicación con los datos obtenidos
                        val ubicacion = LatLng(latitud, longitud)

                        // Agregar un marcador en el mapa
                        mMap.addMarker(MarkerOptions().position(ubicacion).title("Ubicación actual"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
                    } else {
                        Toast.makeText(this@MapsActivity, "No se encontró la ubicación", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MapsActivity, "No se encontró la ubicación", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MapsActivity, "Error al leer la ubicación", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
