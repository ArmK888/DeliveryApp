package com.main.deliveryapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.main.deliveryapp.view.DeliveryMapFragment
import com.main.deliveryapp.view.StopsFragment

class DeliveryTabAdapter(
    fragment: Fragment,
    private var totalTabs: Int
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return totalTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StopsFragment.newInstance()
            1 -> DeliveryMapFragment()
            else -> StopsFragment()
        }
    }


}