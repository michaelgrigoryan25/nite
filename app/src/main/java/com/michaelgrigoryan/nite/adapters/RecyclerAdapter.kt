package com.michaelgrigoryan.nite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.michaelgrigoryan.nite.R
import com.michaelgrigoryan.nite.models.Note
import com.michaelgrigoryan.nite.ui.HomeFragmentDirections

class RecyclerAdapter(
    private val notes: List<Note>
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val heading: TextView = view.findViewById(R.id.heading)
        val content: TextView = view.findViewById(R.id.content)
        val datefield: TextView = view.findViewById(R.id.datefield)
        val container: CardView = view.findViewById(R.id.noteContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.reyclerview_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        fun truncateString(string: String, maxChar: Int): String {
            return if (string.length < maxChar) {
                string
            } else {
                (string.trim().subSequence(0, maxChar)).toString() + "..."
            }
        }

        if (notes[position].heading.toString().isEmpty()) holder.heading.visibility = View.GONE
        if (notes[position].note.toString().isEmpty()) holder.content.visibility = View.GONE

        holder.heading.text = truncateString(notes[position].heading.toString(), 20)
        holder.content.text = truncateString(notes[position].note.toString(), 15)
        holder.datefield.text = ("Last edited at " + notes[position].time)
        holder.container.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditFragment(notes[position])
            holder.container.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = notes.size
}