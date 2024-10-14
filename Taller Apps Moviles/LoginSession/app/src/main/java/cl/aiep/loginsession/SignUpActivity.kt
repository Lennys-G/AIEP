package cl.aiep.loginsession

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.map)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Inicializar Firebase Auth
        val auth = Firebase.auth
        // Acceder a los EditText (input de email y password)
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        // Acceder a los botones (SignUp y Login)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val loginButton = findViewById<Button>(R.id.loginButton)
        // Manejar el clic del botón Login
        loginButton.setOnClickListener {
            // Navegar a la pantalla de LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        //Manejar clic del botón de Sign Up
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Crear un nuevo usuario con Firebase Auth
            auth.createUserWithEmailAndPassword(email, password) // Crear usuario con email y contraseña
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show() // Mostrar mensaje de éxito
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent) // Navegar a la actividad de inicio de sesión
                        finish() // Cerrar la actividad de registro
                    } else {
                        // Verifica si el correo ya existe en la base de datos
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            // Mensaje de error si el correo ya está en uso
                            Toast.makeText(this, "Intente con otro correo que no se encuentre en uso.", Toast.LENGTH_SHORT).show() // Mostrar el mensaje de error
                        } else {
                            // Mostrar errores desconocidos
                            val errorMessage = task.exception?.message ?: "Error desconocido."
                            Toast.makeText(this, "Registro fallido: $errorMessage", Toast.LENGTH_SHORT).show() // Mostrar el mensaje de error
                        }
                    }
                }
        }


    }
}
