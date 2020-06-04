package com.main.deliveryapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.main.deliveryapp.createAddressList
import com.main.deliveryapp.model.DeliveryModel

class DeliveryStopsViewModel : ViewModel() {
    var deliverData: MutableLiveData<List<DeliveryModel>> = MutableLiveData()
    var deliveryStatus = MutableLiveData<Boolean>()

    fun createStops() {
        deliverData.value = createAddressList()
    }


}