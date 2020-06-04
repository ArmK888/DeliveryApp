package com.main.deliveryapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import com.main.deliveryapp.R
import com.main.deliveryapp.model.DeliveryModel
import com.main.deliveryapp.utils.isWithinRageOfDate
import kotlinx.android.synthetic.main.stop_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class DeliveryStopsListAdapter(
    private val list: ArrayList<DeliveryModel>,
    private val onFinishClickListener: OnFinishClick,
    private val onNavigationClickListener: OnNavigationClick
) :
    RecyclerView.Adapter<DeliveryStopsListAdapterViewHolder>() {

    fun updateStopsList(newList: ArrayList<DeliveryModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeliveryStopsListAdapterViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.stop_item, parent, false)
        return DeliveryStopsListAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeliveryStopsListAdapterViewHolder, position: Int) {
        val deliveryModel: DeliveryModel = list[position]
        holder.bind(deliveryModel, onFinishClickListener, onNavigationClickListener)

    }


    override fun getItemCount(): Int = list.size

}


class DeliveryStopsListAdapterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {


    @SuppressLint("SimpleDateFormat")
    fun bind(
        deliveryModel: DeliveryModel,
        onFinishClickListener: OnFinishClick,
        onNavigationClickListener: OnNavigationClick
    ) =
        with(view) {
            deliveryStopTitle.text = deliveryModel.deliveryTitle
            with(deliveryModel.deliveryDetailsModel) {
                val deliveryRageTime = "$deliveryStartTime-$deliveryEndTime"
                deliveryStopTimeRange.text = deliveryRageTime
                deliveryStopAddress.text = addressName
                deliveryStopID.text = (adapterPosition + 1).toString()
                deliveryTime.text = deliveryETA

                val dateFormatter = SimpleDateFormat("hh:mm") as DateFormat

                val startDate = dateFormatter.parse(deliveryStartTime)
                val endDate = dateFormatter.parse(deliveryEndTime)
                val deliveryETADate = dateFormatter.parse(deliveryETA)


                if (isWithinRageOfDate(deliveryETADate, startDate, endDate)) {
                    deliveryTime.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorDeliveryETAGreen
                        )
                    )
                    deliveryAlertIcon.visibility = View.GONE
                } else {
                    deliveryTime.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorDeliveryETARed
                        )
                    )
                    deliveryAlertIcon.visibility = View.VISIBLE
                }

                when (deliveryStatus) {
                    null -> {
                        inactiveRowManager(view, context)
                    }
                    false -> {
                        activeRowManager(view, context)
                        delivery_finish_button.setOnClickListener {
                            onFinishClickListener.invoke(adapterPosition)
                        }
                        delivery_navigation_button.setOnClickListener {
                            onNavigationClickListener.invoke(
                                addressLat,
                                addressLng
                            )
                        }
                    }
                    true -> {
                        finishedRowManager(view, context)
                    }
                }

            }

        }


    private fun inactiveRowManager(view: View, context: Context) {
        with(view) {
            deliveryStopCardView.run {
                val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(8)
                setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorInactiveRow))
                radius = 4.0F
            }
            deliveryStopID.run {
                setBackgroundResource(R.drawable.circle_inactive)
                visibility = View.VISIBLE
            }

            deliveryStopID.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryText
                )
            )

            deliveryStopTitle.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryText
                )
            )
            deliveryStopAddress.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorSecondaryText
                )
            )
            deliveryNavigationLayout.visibility = View.GONE
            deliveryFinishedImage.visibility = View.GONE
            deliveryTime.visibility = View.VISIBLE
            deliveryStopTimeRange.visibility = View.VISIBLE
        }

    }

    private fun activeRowManager(view: View, context: Context) {
        with(view) {
            deliveryStopCardView.run {
                val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(0)
                setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorActiveRow))
                radius = 0.0F
            }
            deliveryStopID.run {
                setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorActiveCircularText
                    )
                )
                setBackgroundResource(R.drawable.circle_active)
                visibility = View.VISIBLE
            }


            deliveryStopTitle.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryText
                )
            )
            deliveryStopAddress.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorSecondaryText
                )
            )
            deliveryNavigationLayout.visibility = View.VISIBLE
            deliveryFinishedImage.visibility = View.GONE
            deliveryTime.visibility = View.VISIBLE
            deliveryStopTimeRange.visibility = View.VISIBLE
        }
    }


    private fun finishedRowManager(view: View, context: Context) {
        with(view) {
            deliveryStopCardView.run {
                val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(0)
                setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorFinishRow))
                radius = 0.0F
            }
            deliveryStopID.visibility = View.GONE
            deliveryFinishedImage.visibility = View.VISIBLE
            deliveryStopTitle.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorFinishedText
                )
            )
            deliveryStopAddress.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorFinishedText
                )
            )
            deliveryNavigationLayout.visibility = View.GONE

            deliveryTime.visibility = View.INVISIBLE
            deliveryStopTimeRange.visibility = View.INVISIBLE
            deliveryAlertIcon.visibility = View.GONE
        }
    }


}