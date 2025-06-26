
package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class ExpenseActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private val calendar = Calendar.getInstance()
    private lateinit var imageView: ImageView
    private var receiptUri: Uri? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageView.setImageURI(it)
                receiptUri = it
                imageView.visibility = ImageView.VISIBLE
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        dbHelper = DatabaseHelper(this)
        imageView = findViewById(R.id.iv_receipt_preview)

        // Category Spinner
        val categories = dbHelper.getAllCategories()
        val spinner = findViewById<Spinner>(R.id.spinnerCategory)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Date Picker
        val etDate = findViewById<EditText>(R.id.etDate)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        etDate.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    etDate.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Receipt Image Picker
        findViewById<Button>(R.id.btn_add_receipt).setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Save Button
        findViewById<Button>(R.id.btnSaveExpense).setOnClickListener {
            val amountText = findViewById<EditText>(R.id.etAmount).text.toString()
            val date = etDate.text.toString()
            val description = findViewById<EditText>(R.id.etDescription).text.toString()
            val category = spinner.selectedItem.toString()

            if (amountText.isNotEmpty() && date.isNotEmpty()) {
                val amount = amountText.toDouble()
                val categoryId = categories.indexOf(category) + 1 // Assuming IDs start from 1

                val id = dbHelper.addExpense(
                    amount,
                    date,
                    categoryId,
                    description,
                    receiptUri?.toString()
                )

                if (id != -1L) {
                    Toast.makeText(this, "Expense saved", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error saving expense", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Back Button
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}