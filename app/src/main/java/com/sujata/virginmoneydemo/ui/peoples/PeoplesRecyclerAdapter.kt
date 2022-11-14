package com.sujata.virginmoneydemo.ui.peoples

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sujata.virginmoneydemo.R
import com.sujata.virginmoneydemo.databinding.ListItemPeoplesBinding
import com.sujata.virginmoneydemo.domain.PeoplesData
import com.sujata.virginmoneydemo.loadImageFromUrl
import javax.inject.Inject

class PeoplesRecyclerAdapter @Inject constructor() :
    RecyclerView.Adapter<PeoplesRecyclerAdapter.ViewHolder>() {

    var peoplesData: List<PeoplesData> = listOf()
    private var context: Context? = null

    var itemClickListener: ItemClickListener?=null
        set(value) {
            context = (value as? Fragment)?.requireContext()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemPeoplesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val name = peoplesData[position].firstName + " " + peoplesData[position].lastName
            peopleName.text = name
           titleTV.text = peoplesData[position].jobtitle
            val url = peoplesData[position].avatar
            thumbnailIV.loadImageFromUrl(url,RequestOptions.overrideOf(800))
            root.setOnClickListener { itemClickListener?.onClick(position) }
        }
    }

    fun setListData(peoplesData: List<PeoplesData>) {
        this.peoplesData = peoplesData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return peoplesData.size
    }

    class ViewHolder(val binding: ListItemPeoplesBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ItemClickListener {
        fun onClick(position: Int)
    }
}