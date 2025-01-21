package com.acalabuig.pruebalogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import com.acalabuig.pruebalogin.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            val username = binding.usuario.text.toString()
            val password = binding.password.text.toString()

            //doAsync {
            lifecycleScope.launch(Dispatchers.IO) {
                val user = getUser(username, password)
                // uiThread {
                if (user.name != "") {
                    goToMain()
                }
            }

            }


        binding.register.setOnClickListener {
            val username = binding.usuario.text.toString()
            val password = binding.password.text.toString()

            val user = UserEntity(name =  username, password = password)

          //  doAsync

            lifecycleScope.launch(Dispatchers.IO) {
                UserApplication.database.userDao().addUser(user)
            }
        }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun getUser(username: String, password: String): UserEntity {
        val user = UserApplication.database.userDao().getUser(username, password)

        return user
    }
}