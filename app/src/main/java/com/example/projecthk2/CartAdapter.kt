package com.example.projecthk2

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val cartList: MutableList<Product>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val img: ImageView = view.findViewById(R.id.imgProduct)
        val name: TextView = view.findViewById(R.id.txtName)
        val price: TextView = view.findViewById(R.id.txtPrice)
        val quantity: TextView = view.findViewById(R.id.txtQuantity)

        val btnPlus: TextView = view.findViewById(R.id.btnPlus)
        val btnMinus: TextView = view.findViewById(R.id.btnMinus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = cartList[position]

        holder.name.text = product.name
        holder.price.text = product.price
        holder.img.setImageResource(product.image)
        holder.quantity.text = product.quantity.toString()

        // ===== Nút tăng số lượng =====
        holder.btnPlus.setOnClickListener {

            product.quantity++
            holder.quantity.text = product.quantity.toString()

            onQuantityChanged()
        }

        // ===== Nút giảm số lượng =====
        holder.btnMinus.setOnClickListener {

            val currentPosition = holder.adapterPosition

            if (currentPosition == RecyclerView.NO_POSITION) return@setOnClickListener

            val currentProduct = cartList[currentPosition]

            if (currentProduct.quantity > 1) {

                currentProduct.quantity--
                holder.quantity.text = currentProduct.quantity.toString()

                onQuantityChanged()

            } else {

                AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Remove item")
                    .setMessage("Do you want to remove this product from your cart?")
                    .setPositiveButton("Remove") { _, _ ->

                        cartList.removeAt(currentPosition)
                        notifyItemRemoved(currentPosition)

                        onQuantityChanged()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }
}