package com.example.projecthk2

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private var productList: MutableList<Product>
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
    ) {

        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val btnFavorite: ImageButton = itemView.findViewById(R.id.btnFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = productList[position]

        holder.txtName.text = product.name
        holder.txtPrice.text = product.price
        holder.imgProduct.setImageResource(product.image)

        // mở Product Detail
        holder.itemView.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                ProductDetailActivity::class.java
            )

            intent.putExtra("id", product.id)
            intent.putExtra("name", product.name)
            intent.putExtra("price", product.price)
            intent.putExtra("description", product.description)
            intent.putExtra("image", product.image)

            holder.itemView.context.startActivity(intent)
        }

        // favorite click
        holder.btnFavorite.setOnClickListener {

            FavoriteManager.toggleFavorite(product)
            notifyItemChanged(position)
        }

        // hiển thị icon favorite
        if (FavoriteManager.isFavorite(product)) {
            holder.btnFavorite.setImageResource(R.drawable.ic_heart_filled)
        } else {
            holder.btnFavorite.setImageResource(R.drawable.ic_heart_outline)
        }
    }

    // cập nhật list (dùng cho search/filter)
    fun updateList(newList: List<Product>) {

        productList.clear()
        productList.addAll(newList)

        notifyDataSetChanged()
    }
}