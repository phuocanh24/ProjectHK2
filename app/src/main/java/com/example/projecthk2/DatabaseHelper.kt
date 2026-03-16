package com.example.projecthk2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "ProductDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        // BẢNG SẢN PHẨM
        db.execSQL(
            """
            CREATE TABLE products(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                price TEXT,
                description TEXT,
                category TEXT,
                rating REAL,
                image INTEGER
            )
            """
        )

        // BẢNG ĐÁNH GIÁ
        db.execSQL(
            """
            CREATE TABLE reviews(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                productId INTEGER,
                userName TEXT,
                rating REAL,
                comment TEXT
            )
            """
        )

        // BẢNG HỎI ĐÁP
        db.execSQL(
            """
            CREATE TABLE questions(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                productId INTEGER,
                question TEXT,
                answer TEXT
            )
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS products")
        db.execSQL("DROP TABLE IF EXISTS reviews")
        db.execSQL("DROP TABLE IF EXISTS questions")

        onCreate(db)
    }

    // ===============================
    // PRODUCT
    // ===============================

    fun insertProduct(product: Product) {

        val db = writableDatabase
        val values = ContentValues()

        values.put("name", product.name)
        values.put("price", product.price)
        values.put("description", product.description)
        values.put("category", product.category)
        values.put("rating", product.rating)
        values.put("image", product.image)

        db.insert("products", null, values)
    }

    fun getProducts(): MutableList<Product> {

        val list = mutableListOf<Product>()
        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM products", null)

        while (cursor.moveToNext()) {

            val id = cursor.getString(0)
            val name = cursor.getString(1)
            val price = cursor.getString(2)
            val description = cursor.getString(3)
            val category = cursor.getString(4)
            val rating = cursor.getFloat(5)
            val image = cursor.getInt(6)
            val quantity = cursor.getInt(7)

            list.add(
                Product(
                    id,
                    name,
                    price,
                    description,
                    category,
                    rating,
                    image,
                    quantity
                )
            )
        }

        cursor.close()
        return list
    }

    fun updateProduct(product: Product) {

        val db = writableDatabase
        val values = ContentValues()

        values.put("name", product.name)
        values.put("price", product.price)
        values.put("description", product.description)
        values.put("category", product.category)
        values.put("rating", product.rating)
        values.put("image", product.image)

        db.update(
            "products",
            values,
            "id=?",
            arrayOf(product.id.toString())
        )
    }

    fun deleteProduct(id: Int) {

        val db = writableDatabase

        db.delete(
            "products",
            "id=?",
            arrayOf(id.toString())
        )
    }

    // ===============================
    // REVIEW
    // ===============================

    fun insertReview(
        productId: Int,
        userName: String,
        rating: Float,
        comment: String
    ) {

        val db = writableDatabase
        val values = ContentValues()

        values.put("productId", productId)
        values.put("userName", userName)
        values.put("rating", rating)
        values.put("comment", comment)

        db.insert("reviews", null, values)
    }

    fun getReviews(productId: Int): MutableList<Review> {

        val list = mutableListOf<Review>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM reviews WHERE productId=?",
            arrayOf(productId.toString())
        )

        while (cursor.moveToNext()) {

            val id = cursor.getInt(0)
            val user = cursor.getString(2)
            val rating = cursor.getFloat(3)
            val comment = cursor.getString(4)

            list.add(
                Review(
                    id,
                    productId,
                    user,
                    rating,
                    comment
                )
            )
        }

        cursor.close()
        return list
    }

    // ===============================
    // QUESTION
    // ===============================

    fun insertQuestion(
        productId: Int,
        question: String,
        answer: String
    ) {

        val db = writableDatabase
        val values = ContentValues()

        values.put("productId", productId)
        values.put("question", question)
        values.put("answer", answer)

        db.insert("questions", null, values)
    }

    fun getQuestions(productId: Int): MutableList<Question> {

        val list = mutableListOf<Question>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM questions WHERE productId=?",
            arrayOf(productId.toString())
        )

        while (cursor.moveToNext()) {

            val id = cursor.getInt(0)
            val question = cursor.getString(2)
            val answer = cursor.getString(3)

            list.add(
                Question(
                    id,
                    productId,
                    question,
                    answer
                )
            )
        }

        cursor.close()
        return list
    }
}