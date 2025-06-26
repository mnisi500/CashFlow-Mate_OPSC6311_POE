
package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        dbHelper = DatabaseHelper(this)

        // Initialize list with categories from database
        val categories = dbHelper.getAllCategories().toMutableList()
        val listView = findViewById<ListView>(R.id.categoryListView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categories)
        listView.adapter = adapter

        findViewById<Button>(R.id.btnAddCategory).setOnClickListener {
            val categoryName = findViewById<EditText>(R.id.etCategoryName).text.toString()
            if (categoryName.isNotEmpty()) {
                val id = dbHelper.addCategory(categoryName)
                if (id != -1L) {
                    categories.add(categoryName)
                    adapter.notifyDataSetChanged()
                    findViewById<EditText>(R.id.etCategoryName).text.clear()
                    Toast.makeText(this, "Category added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Category already exists", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}