package com.main.deliveryapp.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.main.deliveryapp.R
import com.main.deliveryapp.adapters.DeliveryStopsListAdapter
import com.main.deliveryapp.model.DeliveryModel
import com.main.deliveryapp.viewmodel.DeliveryStopsViewModel
import kotlinx.android.synthetic.main.fragment_stops.*


const val GOOGLE_MAPS_PACKAGE_NAME = "com.google.android.apps.maps"

class StopsFragment : Fragment(R.layout.fragment_stops) {

    private var recyclerViewList: Parcelable? = null // For store list position
    private lateinit var viewModel: DeliveryStopsViewModel
    private val deliveryStopsListAdapter =
        DeliveryStopsListAdapter(arrayListOf(), this::onFinishClick, this::onNavigationClick)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(DeliveryStopsViewModel::class.java)
        }
        viewModel.createStops()

        stopsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = deliveryStopsListAdapter
        }

        observeViewModel()


    }

    companion object {
        @JvmStatic
        fun newInstance() = StopsFragment()
    }


    private fun storeInstance() {
        recyclerViewList = stopsList.layoutManager?.onSaveInstanceState()
    } // call before notifyDataSetChanged()

    private fun restore() {
        stopsList.layoutManager?.onRestoreInstanceState(recyclerViewList)
    } // call after notifyDataSetChanged()

    private fun observeViewModel() {
        viewModel.deliverData.observe(viewLifecycleOwner, Observer { stop ->
            stop?.let {
                deliveryStopsListAdapter.updateStopsList(stop as ArrayList<DeliveryModel>)
            }

        })
    }


    private fun onFinishClick(position: Int) {
        storeInstance()
        viewModel.deliverData.value?.finishStop(position)
        viewModel.deliverData.observe(viewLifecycleOwner, Observer { stop ->
            stop?.let {
                deliveryStopsListAdapter.updateStopsList(stop as ArrayList<DeliveryModel>)
                restore()
            }
        })
        viewModel.deliveryStatus.postValue(true)


    }

    private fun onNavigationClick(lat: Double, lng: Double) {
        activity?.let { openGoogleMaps(it, lat, lng) }
    }


    private fun List<DeliveryModel>.finishStop(index: Int) {

        if (index < this.lastIndex) {
            this[index].deliveryDetailsModel.deliveryStatus = true
            this[index + 1].deliveryDetailsModel.deliveryStatus = false
        } else {
            this[index].deliveryDetailsModel.deliveryStatus = true

        }

    }

    private fun openGoogleMaps(
        activity: FragmentActivity,
        lat: Double,
        lng: Double
    ) {
        val packageManager = activity.packageManager
        val intent = packageManager.getLaunchIntentForPackage(GOOGLE_MAPS_PACKAGE_NAME)
        if (intent == null) {
            try {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$GOOGLE_MAPS_PACKAGE_NAME")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$GOOGLE_MAPS_PACKAGE_NAME")
                    )
                )
            }
        } else {
            val mapsCord = "$lat,$lng"
            val gmmIntentUri =
                Uri.parse("google.navigation:q=$mapsCord&mode=d")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage(GOOGLE_MAPS_PACKAGE_NAME)
            activity.startActivity(mapIntent)
        }

    }
}