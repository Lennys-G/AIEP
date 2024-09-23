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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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
        signUpButton.setOnClickListener {
            // Navegar a la pantalla de LoginActivity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Manejar el clic del botón Login
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Iniciar sesión con email y contraseña
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Inicio de sesión exitoso
                        Toast.makeText(this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish() //Cerrar la actividad de login
                    } else {
                        // Verificar si hubo un error y manejar las excepciones
                        when (val exception = task.exception) {
                            is FirebaseAuthInvalidUserException -> {
                                // Si el usuario no está registrado
                                Toast.makeText(this, "El usuario no se encuentra registrado.", Toast.LENGTH_SHORT).show()
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                // Si las credenciales son incorrectas
                                Toast.makeText(this, "Asegúrese de ingresar la información correcta.", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                // Otros errores
                                val errorMessage = exception?.message ?: "Error desconocido."
                                Toast.makeText(this, "Inicio de sesión fallido: $errorMessage", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
        }

    }


}