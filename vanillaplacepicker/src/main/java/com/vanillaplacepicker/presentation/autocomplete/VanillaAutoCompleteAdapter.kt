package com.vanillaplacepicker.presentation.autocomplete

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanillaplacepicker.R
import com.vanillaplacepicker.data.SearchAddressResponse
import com.vanillaplacepicker.extenstion.inflate
import kotlinx.android.synthetic.main.row_auto_complete_place.view.*

class VanillaAutoCompleteAdapter(private val onItemSelected: (data: SearchAddressResponse.Results) -> Unit) :
        RecyclerView.Adapter<VanillaAutoCompleteAdapter.MiAutoCompleteViewHolder>() {

    var placeList = ArrayList<SearchAddressResponse.Results>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiAutoCompleteViewHolder {
        return MiAutoCompleteViewHolder(parent.inflate(R.layout.row_auto_complete_place))
    }

    fun setList(mArrayList: ArrayList<SearchAddressResponse.Results>) {
        this.placeList.clear()
        this.placeList.addAll(mArrayList)
        notifyDataSetChanged()
    }

    fun clearList() {
        this.placeList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    override fun onBindViewHolder(holder: MiAutoCompleteViewHolder, position: Int) {
        holder.bind(placeList[position])
    }

    inner class MiAutoCompleteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(predictions: SearchAddressResponse.Results) {
            itemView.tvPlaceName.text = predictions.name
            itemView.tvPlaceAddress.text = predictions.formattedAddress
            itemView.setOnClickListener {
                onItemSelected(predictions)
            }
        }
    }
}