package com.main.deliveryapp.model


data class DeliveryModel(
    val deliveryTitle: String, //Random String
    val deliveryDetailsModel: DeliveryDetailsModel

)

data class DeliveryDetailsModel(
    val addressName: String,
    val addressLat: Double,
    val addressLng: Double,
    val deliveryStartTime: String,
    val deliveryEndTime: String,
    val deliveryETA: String,
    var deliveryStatus: Boolean? = null
)
