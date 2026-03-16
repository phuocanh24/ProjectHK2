package com.example.projecthk2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

class LocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    // vị trí cửa hàng
    private val Location = LatLng(10.849176372056514, 106.65249004883462)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_location,
            container,
            false
        )

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        val btnCall = view.findViewById<Button>(R.id.btnCall)
        val btnDirections = view.findViewById<Button>(R.id.btnDirections)

        // CALL STORE
        btnCall.setOnClickListener {

            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:0901234567")
            startActivity(intent)
        }

        // DIRECTIONS TO STORE
        btnDirections.setOnClickListener {

            val uri = Uri.parse(
                "google.navigation:q=${Location.latitude},${Location.longitude}"
            )

            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")

            startActivity(intent)
        }

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        // marker cửa hàng
        mMap.addMarker(
            MarkerOptions()
                .position(Location)
                .title("Lumière Jewelry Store")
        )

        // zoom tới cửa hàng
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(Location, 15f)
        )

        enableUserLocation()
    }

    // bật vị trí user
    private fun enableUserLocation() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            mMap.isMyLocationEnabled = true

        } else {

            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 100) {

            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {

                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {

                    mMap.isMyLocationEnabled = true
                }
            }
        }
    }
}