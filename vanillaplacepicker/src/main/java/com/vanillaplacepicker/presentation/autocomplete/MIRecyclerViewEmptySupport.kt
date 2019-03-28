package com.vanillaplacepicker.presentation.autocomplete

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vanillaplacepicker.extenstion.hideView
import com.vanillaplacepicker.extenstion.showView

class RecyclerViewEmptySupport : RecyclerView {

    private var emptyView: View? = null

    private val emptyObserver = object : AdapterDataObserver() {

        override fun onChanged() {
            val adapter = adapter
            if (adapter != null && emptyView != null) {
                if (adapter.itemCount == 0) {
                    emptyView?.showView()
                    this@RecyclerViewEmptySupport.hideView()
                } else {
                    emptyView?.hideView()
                    this@RecyclerViewEmptySupport.showView()
                }
            }
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        super.setAdapter(adapter)

        adapter?.registerAdapterDataObserver(emptyObserver)

        emptyObserver.onChanged()
    }

    fun setEmptyView(emptyView: View) {
        this.emptyView = emptyView
    }
}