package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.content.Intent

class SignUp : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getDatabase(this)

        val fullName = findViewById<EditText>(R.id.etFullName)
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val confirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val signUpBtn = findViewById<Button>(R.id.button2)

        signUpBtn.setOnClickListener {
            val name = fullName.text.toString().trim()
            val mail = email.text.toString().trim()
            val pass = password.text.toString()
            val confirmPass = confirmPassword.text.toString()

            if (name.isEmpty() || mail.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirmPass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val existing = db.userDao().getUserByEmail(mail)
                if (existing != null) {
                    runOnUiThread {
                        Toast.makeText(this@SignUp, "Email already registered", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    db.userDao().insertUser(User(fullName = name, email = mail, password = pass))
                    runOnUiThread {
                        Toast.makeText(this@SignUp, "Account created", Toast.LENGTH_SHORT).show()
                        // Optionally move to login screen
                        startActivity(Intent(this@SignUp, LoginActivity::class.java))
                    }
                }
            }
        }

        findViewById<TextView>(R.id.textView2).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
