package com.acalabuig.pruebalogin
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.acalabuig.pruebalogin.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar SharedPreferences
        preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Inicializar Base de Datos
        db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "UserDatabase")
            .fallbackToDestructiveMigration()
            .build()

        // Comprobar si el usuario está guardado en SharedPreferences
        checkRememberedUser()

        // Botón de Login
        binding.login.setOnClickListener {
            val username = binding.usuario.text.toString().trim()
            val password = binding.password.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                showToast("Por favor, introduce usuario y contraseña")
            }
        }

        // Botón de Registro
        binding.register.setOnClickListener {
            val username = binding.usuario.text.toString().trim()
            val password = binding.password.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                registerUser(username, password)
            } else {
                showToast("Por favor, introduce usuario y contraseña")
            }
        }
    }

    private fun checkRememberedUser() {
        val rememberedUser = preferences.getString("username", null)
        val rememberedPassword = preferences.getString("password", null)
        val rememberMe = preferences.getBoolean("remember", false)

        if (rememberMe && rememberedUser != null && rememberedPassword != null) {
            goToMainActivity(rememberedUser)
        }
    }

    private fun loginUser(username: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val user = db.userDao().getUser(username, password)
            withContext(Dispatchers.Main) {
                if (user != null) {
                    if (binding.cbRememberMe.isChecked) {
                        saveUserPreferences(username, password)
                    } else {
                        clearUserPreferences()
                    }
                    showToast("Bienvenido $username")
                    goToMainActivity(username)
                } else {
                    showToast("Credenciales inválidas")
                }
            }
        }
    }

    private fun registerUser(username: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val existingUser = db.userDao().getUser(username,password)
            withContext(Dispatchers.Main) {
                if (existingUser != null) {
                    showToast("Usuario ya existe")
                } else {
                    val newUser = UserEntity(name = username, password = password)
                    lifecycleScope.launch(Dispatchers.IO) {
                        db.userDao().addUser(newUser)
                        withContext(Dispatchers.Main) {
                            showToast("Usuario registrado correctamente")
                        }
                    }
                }
            }
        }
    }

    private fun saveUserPreferences(username: String, password: String) {
        with(preferences.edit()) {
            putString("username", username)
            putString("password", password)
            putBoolean("remember", true)
            apply()
        }
    }

    private fun clearUserPreferences() {
        preferences.edit().clear().apply()
    }

    private fun goToMainActivity(username: String) {
        runOnUiThread {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Usuario", username)
            startActivity(intent)
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}