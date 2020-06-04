package com.main.deliveryapp.utils

import java.util.*


fun isWithinRageOfDate(
    deliveryETA: Date?,
    deliveryStartDate: Date?,
    deliveryEndDate: Date?
): Boolean {
    deliveryETA?.let {
        deliveryStartDate?.let {
            deliveryEndDate?.let {
                return deliveryETA.after(deliveryStartDate) && deliveryETA.before(deliveryEndDate)
            }
        }
    }
    return false
}
