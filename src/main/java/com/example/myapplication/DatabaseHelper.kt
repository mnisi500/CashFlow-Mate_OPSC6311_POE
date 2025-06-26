
package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ExpenseTracker.db"
        private const val DATABASE_VERSION = 1

        // User table
        const val TABLE_USERS = "users"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_FULL_NAME = "full_name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"

        // Category table
        const val TABLE_CATEGORIES = "categories"
        const val COLUMN_CATEGORY_ID = "category_id"
        const val COLUMN_CATEGORY_NAME = "category_name"

        // Expense table
        const val TABLE_EXPENSES = "expenses"
        const val COLUMN_EXPENSE_ID = "expense_id"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_DATE = "date"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_RECEIPT_URI = "receipt_uri"

        // Budget table
        const val TABLE_BUDGETS = "budgets"
        const val COLUMN_BUDGET_ID = "budget_id"
        const val COLUMN_TOTAL_BUDGET = "total_budget"
        const val COLUMN_CATEGORY_BUDGET = "category_budget"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create users table
        val createUserTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_FULL_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()

        // Create categories table
        val createCategoryTable = """
            CREATE TABLE $TABLE_CATEGORIES (
                $COLUMN_CATEGORY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORY_NAME TEXT NOT NULL UNIQUE
            )
        """.trimIndent()

        // Create expenses table
        val createExpenseTable = """
            CREATE TABLE $TABLE_EXPENSES (
                $COLUMN_EXPENSE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_AMOUNT REAL NOT NULL,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_CATEGORY_ID INTEGER NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_RECEIPT_URI TEXT,
                FOREIGN KEY ($COLUMN_CATEGORY_ID) REFERENCES $TABLE_CATEGORIES($COLUMN_CATEGORY_ID)
            )
        """.trimIndent()

        // Create budgets table
        val createBudgetTable = """
            CREATE TABLE $TABLE_BUDGETS (
                $COLUMN_BUDGET_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TOTAL_BUDGET REAL NOT NULL,
                $COLUMN_CATEGORY_ID INTEGER,
                $COLUMN_CATEGORY_BUDGET REAL,
                FOREIGN KEY ($COLUMN_CATEGORY_ID) REFERENCES $TABLE_CATEGORIES($COLUMN_CATEGORY_ID)
        )
        """.trimIndent()


        db.execSQL(createUserTable)
        db.execSQL(createCategoryTable)
        db.execSQL(createExpenseTable)
        db.execSQL(createBudgetTable)

        // Insert some default categories
        val defaultCategories = arrayOf("Groceries", "Entertainment", "Transport", "Utilities")
        defaultCategories.forEach { category ->
            val values = ContentValues().apply {
                put(COLUMN_CATEGORY_NAME, category)
            }
            db.insert(TABLE_CATEGORIES, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BUDGETS")
        onCreate(db)
    }

    // User CRUD operations
    fun addUser(fullName: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FULL_NAME, fullName)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
        }
        return db.insert(TABLE_USERS, null, values)
    }

    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null)
        return cursor.count > 0
    }

    // Category CRUD operations
    fun addCategory(name: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY_NAME, name)
        }
        return db.insert(TABLE_CATEGORIES, null, values)
    }

    fun getAllCategories(): List<String> {
        val categories = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_CATEGORIES, arrayOf(COLUMN_CATEGORY_NAME), null, null, null, null, null)

        while (cursor.moveToNext()) {
            categories.add(cursor.getString(0))
        }
        cursor.close()
        return categories
    }

    // Expense CRUD operations
    fun addExpense(amount: Double, date: String, categoryId: Int, description: String?, receiptUri: String?): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AMOUNT, amount)
            put(COLUMN_DATE, date)
            put(COLUMN_CATEGORY_ID, categoryId)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_RECEIPT_URI, receiptUri)
        }
        return db.insert(TABLE_EXPENSES, null, values)
    }

    // Budget CRUD operations
    fun setTotalBudget(amount: Double): Long {
        val db = this.writableDatabase
        // Clear previous total budget if exists
        db.delete(TABLE_BUDGETS, "$COLUMN_CATEGORY_ID IS NULL", null)

        val values = ContentValues().apply {
            put(COLUMN_TOTAL_BUDGET, amount)
        }
        return db.insert(TABLE_BUDGETS, null, values)
    }

    fun setCategoryBudget(categoryId: Int, amount: Double): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY_ID, categoryId)
            put(COLUMN_CATEGORY_BUDGET, amount)
        }
        return db.insert(TABLE_BUDGETS, null, values)
    }
}