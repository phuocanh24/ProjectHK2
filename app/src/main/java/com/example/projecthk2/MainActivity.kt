package com.example.projecthk2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        bottomNav = findViewById(R.id.bottomNav)
        navigationView = findViewById(R.id.navigationView)

        // padding status bar cho toolbar
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(
                view.paddingLeft,
                statusBarHeight,
                view.paddingRight,
                view.paddingBottom
            )
            insets
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.allcollections -> {
                    replaceFragment(CollectionsFragment())
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.menu_location -> {
                    replaceFragment(LocationFragment())
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            handleIntent(intent)
        }

        updateCartBadge()

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showToolbar()
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_search -> {
                    showToolbar()
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.nav_cart -> {
                    showToolbar()
                    replaceFragment(CartFragment())
                    true
                }
                R.id.nav_gold -> {
                    showToolbar()
                    replaceFragment(GoldPriceFragment())
                    true
                }
                R.id.nav_profile -> {
                    hideToolbar()
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    // Ẩn toolbar khi vào ProfileFragment
    fun hideToolbar() {
        toolbar.visibility = android.view.View.GONE
    }

    // Hiện toolbar khi ra khỏi ProfileFragment
    fun showToolbar() {
        toolbar.visibility = android.view.View.VISIBLE
    }

    private fun handleIntent(intent: Intent?) {
        val openCart = intent?.getBooleanExtra("openCart", false) ?: false
        if (openCart) {
            replaceFragment(CartFragment())
            bottomNav.selectedItemId = R.id.nav_cart
        } else {
            replaceFragment(HomeFragment())
            bottomNav.selectedItemId = R.id.nav_home
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }

    private fun updateCartBadge() {
        val badge = bottomNav.getOrCreateBadge(R.id.nav_cart)
        val cartSize = CartManager.cartList.sumOf { it.quantity }
        if (cartSize > 0) {
            badge.isVisible = true
            badge.number = cartSize
        } else {
            badge.isVisible = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_location -> {
                replaceFragment(LocationFragment())
                Toast.makeText(this, "Store Location", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}