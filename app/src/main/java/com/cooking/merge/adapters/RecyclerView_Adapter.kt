package com.cooking.merge.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.cooking.merge.R
import com.cooking.merge.bottom_fragments.HomeFragment
import kotlinx.android.synthetic.main.fooditems_details.view.*
import java.util.*
import kotlin.collections.ArrayList

class RecyclerView_Adapter(private var foodList: ArrayList<String>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var foodFilterList = ArrayList<String>()

    lateinit var mcontext: Context

    class CountryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        foodFilterList = foodList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val foodListView =
            LayoutInflater.from(parent.context).inflate(R.layout.fooditems_details, parent, false)
        val sch = CountryHolder(foodListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return foodFilterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.select_food_container.setBackgroundColor(Color.TRANSPARENT)

        holder.itemView.TV_detailsname.setTextColor(Color.WHITE)
        holder.itemView.TV_detailsname.text = foodFilterList[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(mcontext, HomeFragment::class.java)
            intent.putExtra("passselectedcountry", foodFilterList[position])
            mcontext.startActivity(intent)
            Log.d("Selected:", foodFilterList[position])
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    foodFilterList = foodList
                } else {
                    val resultList = ArrayList<String>()
                    for (row in foodList) {
                        if (row.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    foodFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = foodFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                foodFilterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }

}