package com.vanillaplacepicker.presentation.autocomplete

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanillaplacepicker.R
import com.vanillaplacepicker.data.AutocompletePredictionResponse
import com.vanillaplacepicker.extenstion.inflate
import kotlinx.android.synthetic.main.row_auto_complete_place.view.*

class VanillaAutoCompleteAdapter(private val onItemSelected: (data: AutocompletePredictionResponse.PredictionsBean) -> Unit) :
        RecyclerView.Adapter<VanillaAutoCompleteAdapter.MiAutoCompleteViewHolder>() {

    var places = ArrayList<AutocompletePredictionResponse.PredictionsBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiAutoCompleteViewHolder {
        return MiAutoCompleteViewHolder(parent.inflate(R.layout.row_auto_complete_place))
    }

    fun setList(predictions: ArrayList<AutocompletePredictionResponse.PredictionsBean>?) {
        this.places.clear()
        predictions?.let {
            this.places.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun clearList() {
        this.places.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return places.size
    }

    override fun onBindViewHolder(holder: MiAutoCompleteViewHolder, position: Int) {
        holder.bind(places[position])
    }

    inner class MiAutoCompleteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(predictions: AutocompletePredictionResponse.PredictionsBean) {
            itemView.tvPlaceName.text = predictions.structuredFormatting?.mainText
            itemView.tvPlaceAddress.text = predictions.structuredFormatting?.secondaryText
            itemView.setOnClickListener {
                onItemSelected(predictions)
            }
        }
    }
}