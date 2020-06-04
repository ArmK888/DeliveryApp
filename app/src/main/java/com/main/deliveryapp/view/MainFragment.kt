package com.main.deliveryapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.main.deliveryapp.R
import com.main.deliveryapp.adapters.DeliveryTabAdapter
import com.main.deliveryapp.addressData
import com.main.deliveryapp.viewmodel.DeliveryStopsViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*


class MainFragment : Fragment(R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            deliveryTabLayout.run {
                addTab(newTab().setText("Stops (${addressData().size})"))// Stops (12)
                addTab(newTab().setText("Map"))
                tabGravity = TabLayout.GRAVITY_FILL

            }
            val adapterTab = DeliveryTabAdapter(this@MainFragment, view.deliveryTabLayout.tabCount)
            deliveryViewPager.run {
                isUserInputEnabled = false
                adapter = adapterTab
            }

            TabLayoutMediator(view.deliveryTabLayout, view.deliveryViewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Stops (${addressData().size})"
                    else -> "Map"

                }
            }.attach()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}