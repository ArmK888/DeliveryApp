package com.main.deliveryapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.ui.IconGenerator
import com.main.deliveryapp.R
import com.main.deliveryapp.model.DeliveryModel
import com.main.deliveryapp.viewmodel.DeliveryStopsViewModel

class DeliveryMapFragment : Fragment(R.layout.fragment_delivery_map),
    GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private var markerList = listOf<DeliveryModel>()
    private val markerArray = arrayListOf<Marker>()
    private lateinit var viewModel: DeliveryStopsViewModel
    private var map: GoogleMap? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(DeliveryStopsViewModel::class.java)
            observeInput(viewModel)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(DeliveryStopsViewModel::class.java)
            observeInput(viewModel)
            updateMap(viewModel)
        }

    }

    private fun observeInput(sharedViewModel: DeliveryStopsViewModel) {
        sharedViewModel.deliverData.observe(viewLifecycleOwner, Observer { stop ->
            stop?.let {
                markerList = stop
            }

        })

    }

    private fun updateMap(sharedViewModel: DeliveryStopsViewModel) {
        sharedViewModel.deliveryStatus.observe(viewLifecycleOwner, Observer { stop ->
            stop?.let {
                map?.run {
                    clear()
                    createMarkers(this)
                }
            }

        })

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        map?.let { createMarkers(it) }


    }


    private fun createMarkers(map: GoogleMap) {
        val iconGenerator = IconGenerator(context)
        iconGenerator.setTextAppearance(R.style.BaseText)


        markerList.forEachIndexed { index, markerData ->
            with(markerData.deliveryDetailsModel) {
                when (deliveryStatus) {
                    null -> iconGenerator.setBackground(
                        resources.getDrawable(
                            R.drawable.waited_marker,
                            null
                        )
                    )
                    false -> iconGenerator.setBackground(
                        resources.getDrawable(
                            R.drawable.current_marker,
                            null
                        )
                    )
                    true -> iconGenerator.setBackground(
                        resources.getDrawable(
                            R.drawable.finished_marker,
                            null
                        )
                    )
                }


                val marker = map.addMarker(
                    MarkerOptions()
                        .position(LatLng(addressLat, addressLng))
                        .anchor(0.5f, 0.5f)
                        .title(addressName)
                        .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon((index + 1).toString())))
                )
                marker?.let { markerArray.add(it) }

            }
        }


        val builder = LatLngBounds.Builder()
        for (markers in markerArray) {
            builder.include(markers.position)
        }
        val bounds = builder.build()
        val padding = 50
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        map.moveCamera(cu)
        map.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {

        return false
    }
}