package com.example.martin.meteorlist.ui.mapsdetailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.martin.meteorlist.R
import com.example.martin.meteorlist.model.Meteorite
import com.example.martin.meteorlist.utils.METEORITE_KEY

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsDetailFragment : Fragment(), OnMapReadyCallback {


    private lateinit var mMeteorite: Meteorite

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        mMeteorite = arguments!!.getParcelable(METEORITE_KEY)
        val map = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
        map.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val meteoriteLat = LatLng(mMeteorite.reclat, mMeteorite.reclong)
        val zoomLevel = 5f
        googleMap ?: return
        with(googleMap) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(meteoriteLat, zoomLevel))
            addMarker(MarkerOptions().position(meteoriteLat).title(mMeteorite.name))
        }
    }
}
