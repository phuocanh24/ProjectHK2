package com.example.projecthk2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var imgProduct: ImageView
    private lateinit var txtName: TextView
    private lateinit var txtPrice: TextView
    private lateinit var txtDescription: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var btnShare: ImageButton
    private lateinit var btnAddToCart: Button
    private lateinit var btnBuyNow: Button

    private var id: String? = null
    private var name: String? = null
    private var price: String? = null
    private var description: String? = null
    private var image: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_detail)

        // nhận dữ liệu từ Adapter
        id = intent.getStringExtra("id")
        name = intent.getStringExtra("name")
        price = intent.getStringExtra("price")
        description = intent.getStringExtra("description")
        image = intent.getIntExtra("image", 0)

        imgProduct = findViewById(R.id.imgProduct)
        txtName = findViewById(R.id.txtName)
        txtPrice = findViewById(R.id.txtPrice)
        txtDescription = findViewById(R.id.txtDescription)

        btnBack = findViewById(R.id.btnBack)
        btnShare = findViewById(R.id.btnShare)
        btnAddToCart = findViewById(R.id.btnAddToCart)
        btnBuyNow = findViewById(R.id.btnBuyNow)

        txtName.text = name
        txtPrice.text = price
        txtDescription.text = description
        imgProduct.setImageResource(image)

        // BACK
        btnBack.setOnClickListener {
            finish()
        }

        // SHARE
        btnShare.setOnClickListener {

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"

            val shareText =
                "Check out this product:\n\n$name\nPrice: $price"

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)

            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        // ADD TO CART
        btnAddToCart.setOnClickListener {

            val product = Product(
                id = id ?: "",
                name = name ?: "",
                price = price ?: "",
                description = description ?: "",
                category = "Jewelry",
                rating = 4.5f,
                image = image,
                quantity = 1
            )

            CartManager.addToCart(product)

            Toast.makeText(
                this,
                "$name added to cart",
                Toast.LENGTH_SHORT
            ).show()
        }

        // BUY NOW
        btnBuyNow.setOnClickListener {

            val product = Product(
                id = id ?: "",
                name = name ?: "",
                price = price ?: "",
                description = description ?: "",
                category = "Jewelry",
                rating = 4.5f,
                image = image,
                quantity = 1
            )

            CartManager.addToCart(product)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("openCart", true)
            startActivity(intent)
            finish()
        }
    }
}