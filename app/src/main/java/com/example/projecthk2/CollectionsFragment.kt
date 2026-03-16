package com.example.projecthk2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class CollectionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_collections,
            container,
            false
        )

        // Rings
        view.findViewById<View>(R.id.layoutRings).setOnClickListener {
            openCategory("Rings")
        }

        // Necklaces
        view.findViewById<View>(R.id.layoutNecklaces).setOnClickListener {
            openCategory("Necklaces")
        }

        // Earrings
        view.findViewById<View>(R.id.layoutEarrings).setOnClickListener {
            openCategory("Earrings")
        }

        // Bracelets
        view.findViewById<View>(R.id.layoutBracelets).setOnClickListener {
            openCategory("Bracelets")
        }

        // Watches
        view.findViewById<View>(R.id.layoutWatches).setOnClickListener {
            openCategory("Watches")
        }

        return view
    }

    private fun openCategory(category: String) {

        val intent = Intent(requireContext(), ProductListActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)

    }
}