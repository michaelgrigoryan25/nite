package com.michaelgrigoryan.nite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michaelgrigoryan.nite.R
import com.michaelgrigoryan.nite.models.Note

class RecyclerAdapter(
    private val notes: List<Note>
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var heading: TextView = view.findViewById(R.id.heading)
        var content: TextView = view.findViewById(R.id.content)
        var datefield: TextView = view.findViewById(R.id.datefield)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.reyclerview_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.heading.text = notes[position].heading
        holder.content.text = notes[position].note
        holder.datefield.text = notes[position].time
    }

    override fun getItemCount(): Int = notes.size
}