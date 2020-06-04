package com.main.deliveryapp

import com.main.deliveryapp.model.DeliveryDetailsModel
import com.main.deliveryapp.model.DeliveryModel

fun createAddressList(): List<DeliveryModel> {
    val stopsList = arrayListOf<DeliveryModel>()
    for (details in addressData()) {

        val randomTitle = (10000..999999).random().toString()
        stopsList.add(DeliveryModel(randomTitle, details))

    }
    return stopsList
}

fun addressData(): List<DeliveryDetailsModel> {
    return listOf(
        DeliveryDetailsModel(
            "21 Gyulikevkhyan St",
            40.187029,
            44.571433,
            "08:40",
            "09:20",
            "09:10",
            true
        ),
        DeliveryDetailsModel(
            "36-62 Avet Avetisyan",
            40.198266,
            44.500626,
            "09:20",
            "10:10",
            "10:12",
            false
        ),
        DeliveryDetailsModel(
            "27 Hrachya Kochar St",
            40.205975,
            44.506609,
            "10:10",
            "11:50",
            "11:30"
        ),
        DeliveryDetailsModel(
            "15 Amiryan St",
            40.181845,
            44.506434,
            "11:50",
            "12:15",
            "12:23"
        ),
        DeliveryDetailsModel(
            "6 Arzumanyan St",
            40.192885,
            44.475265,
            "12:15",
            "12:35",
            "12:33"
        ),
        DeliveryDetailsModel(
            "13 Rostovyan St",
            40.150725,
            44.515683,
            "12:35",
            "13:10",
            "12:40"
        ),
        DeliveryDetailsModel(
            "9A home, 11th Street, N. Shengavit",
            40.138810,
            44.488568,
            "13:10",
            "14:00",
            "13:38"
        ),
        DeliveryDetailsModel(
            "87, apt. 5 Andranik Zoravar St",
            40.171554,
            44.446423,
            "14:00",
            "14:40",
            "14:38"
        ),
        DeliveryDetailsModel(
            "50 Leningradyan St",
            40.191125,
            44.464822,
            "14:40",
            "15:50",
            "15:20"
        ),
        DeliveryDetailsModel(
            "17 Halabyan St",
            40.193876,
            44.477947,
            "15:50",
            "16:10",
            "16:20"
        ),
        DeliveryDetailsModel(
            "Beknazarian 5/15 St",
            40.209894,
            44.469625,
            "16:10",
            "16:40",
            "16:35"
        ),
        DeliveryDetailsModel(
            "18 Tigran Petrosyan St",
            40.218920,
            44.490636,
            "16:40",
            "17:25",
            "17:20"
        ),
        DeliveryDetailsModel(
            "5 Armen Tigranyan St",
            40.201384,
            44.524141,
            "17:25",
            "17:45",
            "17:35"
        ),
        DeliveryDetailsModel(
            "Paruyr Sevak 9",
            40.207920,
            44.536784,
            "17:45",
            "18:00",
            "17:28"
        ),
        DeliveryDetailsModel(
            "6 Shopron St",
            40.185989,
            44.561113,
            "18:00",
            "18:30",
            "18:20"
        ),
        DeliveryDetailsModel(
            "28 Aram St",
            40.175464,
            44.519612,
            "18:30",
            "18:55",
            "18:45"
        ),
        DeliveryDetailsModel(
            "50 Teryan St",
            40.185095,
            44.517647,
            "18:55",
            "19:30",
            "19:35"
        ),
        DeliveryDetailsModel(
            "8 Moskovyan St",
            40.188199,
            44.511911,
            "19:30",
            "20:05",
            "20:00"
        ),
        DeliveryDetailsModel(
            "33A Marshal Baghramyan Ave",
            40.192035,
            44.502334,
            "20:05",
            "20:50",
            "20:20"
        ),
        DeliveryDetailsModel(
            "22 Komitas Ave",
            40.202777,
            44.499292,
            "20:50",
            "21:30",
            "21:10"
        )
    )
}