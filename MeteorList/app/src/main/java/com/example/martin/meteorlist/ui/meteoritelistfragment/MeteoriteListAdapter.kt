package com.example.martin.meteorlist.ui.meteoritelistfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.martin.meteorlist.databinding.ListMeteoritesBinding
import com.example.martin.meteorlist.model.Meteorite
import com.example.martin.meteorlist.ui.MeteoriteInterface

class MeteoriteListAdapter(
    private val mClickMeteoriteInterface: MeteoriteInterface,
    private val mMeteorites: List<Meteorite>
) : RecyclerView.Adapter<MeteoriteListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListMeteoritesBinding.inflate(inflater)
        binding.clickToCardView = mClickMeteoriteInterface
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meteorite = mMeteorites[position]
        holder.bind(meteorite)
    }

    override fun getItemCount(): Int = mMeteorites.size

    inner class ViewHolder(private val binding: ListMeteoritesBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meteorite: Meteorite) {
            binding.meteorite = meteorite
            binding.executePendingBindings()
        }
    }
}