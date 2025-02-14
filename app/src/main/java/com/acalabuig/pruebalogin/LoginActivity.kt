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
        db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "app-database").build()

        // Comprobar si el usuario está guardado en SharedPreferences
        checkRememberedUser()

        // Botón de Login
        binding.login.setOnClickListener {
            val username = binding.usuario.text.toString()
            val password = binding.password.text.toString()
            loginUser(username, password)
        }

        // Botón de Registro
        binding.register.setOnClickListener {
            val username = binding.usuario.text.toString()
            val password = binding.password.text.toString()
            registerUser(username, password)
        }
    }

    private fun checkRememberedUser() {
        val rememberedUser = preferences.getString("username", null)
        val rememberedPassword = preferences.getString("password", null)
        val rememberMe = preferences.getBoolean("remember", false)

        if (rememberMe && rememberedUser != null && rememberedPassword != null) {
            binding.usuario.setText(rememberedUser)
            binding.password.setText(rememberedPassword)
            binding.cbRememberMe.isChecked = true
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
                    Toast.makeText(this@LoginActivity, "Bienvenido $username", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("Usuario", username)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun registerUser(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                val existingUser = db.userDao().getUser(username, password)
                withContext(Dispatchers.Main) {
                    if (existingUser != null) {
                        Toast.makeText(this@LoginActivity, "Usuario duplicado", Toast.LENGTH_SHORT).show()
                    } else {
                        val newUser = UserEntity(name = username, password = password)
                        db.userDao().addUser(newUser)
                        saveUserPreferences(username, password)
                        Toast.makeText(this@LoginActivity, "Usuario registrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show()
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
}