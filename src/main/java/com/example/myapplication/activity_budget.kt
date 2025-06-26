
package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BudgetActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        dbHelper = DatabaseHelper(this)

        findViewById<Button>(R.id.btnSaveTotalBudget).setOnClickListener {
            val totalBudget = findViewById<EditText>(R.id.etTotalBudget).text.toString()
            if (totalBudget.isNotEmpty()) {
                val amount = totalBudget.toDouble()
                val id = dbHelper.setTotalBudget(amount)
                if (id != -1L) {
                    Toast.makeText(this, "Total budget set", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error saving budget", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a budget amount", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}