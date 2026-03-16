package com.example.projecthk2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryList = mutableListOf<CategoryCollections>()

    private lateinit var categoryRecycler: RecyclerView
    private lateinit var productRecycler: RecyclerView
    private lateinit var txtAllCollections: TextView

    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        categoryRecycler = view.findViewById(R.id.categoryRecycler)
        productRecycler = view.findViewById(R.id.productRecycler)
        txtAllCollections = view.findViewById(R.id.txtAllCollections)

        loadCategories()
        loadProducts()

        // Recycler category
        categoryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        categoryAdapter = CategoryAdapter(categoryList) { categoryName ->

            val filteredList = productList.filter {
                it.category == categoryName
            }

            productAdapter.updateList(filteredList)
        }

        categoryRecycler.adapter = categoryAdapter

        // Recycler product
        productRecycler.layoutManager =
            GridLayoutManager(requireContext(), 2)

        productAdapter = ProductAdapter(productList)
        productRecycler.adapter = productAdapter

        // CLICK ALL COLLECTIONS
        txtAllCollections.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, CollectionsFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun loadCategories() {

        categoryList.add(CategoryCollections("Rings", R.drawable.n1))
        categoryList.add(CategoryCollections("Necklaces", R.drawable.d1))
        categoryList.add(CategoryCollections("Bracelets", R.drawable.v1))
        categoryList.add(CategoryCollections("Earrings", R.drawable.t1))
    }

    private fun loadProducts() {

        productList.add(
            Product(
                id = "r1",
                name = "LOVE Unlimited Ring",
                price = "$2,670.00",
                image = R.drawable.n1,
                description = "The LOVE collection began with the iconic bracelet created in New York in 1969.",
                category = "Rings",
                rating = 4.8f,
                quantity = 1
            )
        )

        productList.add(
            Product(
                id = "n1",
                name = "LOVE pendant, 2 diamonds",
                price = "$2,920.00",
                image = R.drawable.d1,
                description = "Elegant necklace with two diamonds.",
                category = "Necklaces",
                rating = 4.7f,
                quantity = 1
            )
        )

        productList.add(
            Product(
                id = "e1",
                name = "Trinity earrings",
                price = "$3,850.00",
                image = R.drawable.t1,
                description = "Classic trinity earrings design.",
                category = "Earrings",
                rating = 4.9f,
                quantity = 1
            )
        )

        productList.add(
            Product(
                id = "b1",
                name = "Juste un Clou bracelet",
                price = "$7,850.00",
                image = R.drawable.v1,
                description = "Iconic bracelet design from Cartier style.",
                category = "Bracelets",
                rating = 4.6f,
                quantity = 1
            )
        )
    }
}

