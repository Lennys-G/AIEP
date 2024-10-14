package cl.aiep.loginsession

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import cl.aiep.loginsession.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class CalcularDespachoActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var inputMontoCompra: EditText
    private lateinit var resultadoDistancia: TextView
    private lateinit var resultadoDespacho: TextView

    private var ubicacionActual: LatLng? = null
    private var direccionLatLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_despacho)

        // Inicialización de los elementos de la UI
        inputMontoCompra = findViewById(R.id.inputMontoCompra)
        resultadoDistancia = findViewById(R.id.resultadoDistancia)
        resultadoDespacho = findViewById(R.id.resultadoDespacho)
        val btnCalcularDespacho = findViewById<Button>(R.id.btnCalcular)

        // Inicializa el cliente de localización y Google Places API
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Places.initialize(applicationContext, "AIzaSyDexhu0y26NCNOeW5tN-zCsPHuPxL8Tn7U")
        val placesClient = Places.createClient(this)

        obtenerUbicacionActual()

        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        // Evento de selección de lugar
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                direccionLatLng = place.latLng
            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                Toast.makeText(this@CalcularDespachoActivity, "Error: ${status.statusMessage}", Toast.LENGTH_SHORT).show()
            }
        })

        // Botón para calcular el despacho
        btnCalcularDespacho.setOnClickListener {
            if (direccionLatLng != null && ubicacionActual != null) {
                val montoCompra = inputMontoCompra.text.toString().toDoubleOrNull()
                if (montoCompra != null) {
                    val distancia = calcularDistancia(ubicacionActual!!, direccionLatLng!!)
                    resultadoDistancia.text = "Distancia: %.2f km".format(distancia)

                    val valorDespacho = calcularValorDespacho(montoCompra, distancia)
                    resultadoDespacho.text = "Valor del despacho: %.0f CLP".format(valorDespacho)
                } else {
                    Toast.makeText(this, "Por favor, introduce un monto válido.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, selecciona una dirección válida.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Obtener ubicación actual
    private fun obtenerUbicacionActual() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    ubicacionActual = LatLng(it.latitude, it.longitude)
                    guardarUbicacionEnFirebase(ubicacionActual!!)
                }
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
        }
    }

    private fun guardarUbicacionEnFirebase(ubicacion: LatLng) {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val locationData = hashMapOf("latitud" to ubicacion.latitude, "longitud" to ubicacion.longitude)

        userId?.let {
            db.collection("users").document(it).set(locationData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Ubicación guardada con éxito", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar ubicación", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Calcular distancia entre dos ubicaciones (Haversine formula)
    private fun calcularDistancia(ubicacion1: LatLng, ubicacion2: LatLng): Double {
        val earthRadius = 6371 // Radio de la Tierra en kilómetros
        val latDiff = Math.toRadians(ubicacion2.latitude - ubicacion1.latitude)
        val lonDiff = Math.toRadians(ubicacion2.longitude - ubicacion1.longitude)

        val a = sin(latDiff / 2) * sin(latDiff / 2) +
                cos(Math.toRadians(ubicacion1.latitude)) * cos(Math.toRadians(ubicacion2.latitude)) *
                sin(lonDiff / 2) * sin(lonDiff / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c
    }

    // Calcular el valor del despacho basado en la distancia y monto de la compra
    private fun calcularValorDespacho(montoCompra: Double, distancia: Double): Double {
        val tarifaPorKmBajo = 300.0  // Tarifa por km para compras menores a 25,000
        val tarifaPorKmMedio = 150.0  // Tarifa por km para compras entre 25,000 y 49,999
        val kmGratis = 20.0           // Primeros 20 km son gratis

        return when {
            // Si el monto de la compra es mayor a 50,000 CLP
            montoCompra >= 50000 -> {
                // Si la distancia es mayor a 20 km, se cobra solo la diferencia
                if (distancia > kmGratis) {
                    (distancia - kmGratis) * tarifaPorKmMedio
                } else {
                    0.0 // Si la distancia es menor o igual a 20 km, no hay costo
                }
            }
            // Si el monto de la compra está entre 25,000 y 49,999 CLP
            montoCompra in 25000.0..49999.0 -> {
                distancia * tarifaPorKmMedio // Cobramos $150 por km
            }
            // Si el monto de la compra es menor a 25,000 CLP
            else -> {
                distancia * tarifaPorKmBajo // Cobramos $300 por km
            }
        }
    }

}
