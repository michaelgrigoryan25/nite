package com.michaelgrigoryan.nite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.michaelgrigoryan.nite.R
import com.michaelgrigoryan.nite.database.Database
import com.michaelgrigoryan.nite.models.Note
import com.michaelgrigoryan.nite.ui.HomeFragmentDirections
import com.michaelgrigoryan.nite.util.truncateString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerAdapter(
    private val notes: MutableList<Note>
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val content: TextView = view.findViewById(R.id.content)
        val dateField: TextView = view.findViewById(R.id.datefield)
        val container: CardView = view.findViewById(R.id.noteContainer)
        val deleteNote: ImageView = view.findViewById(R.id.delete_note_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.reyclerview_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Setting the last modified date on the holder
        holder.dateField.text = ("Last edited at ${notes[position].time}")
        // Truncating the note and setting note holder text
        holder.content.text = truncateString(notes[position].note!!, 15)

        // Setting an onClick event listener on delete icon
        holder.deleteNote.setOnClickListener {
            // Running in coroutine thread
            GlobalScope.launch(Dispatchers.IO) {
                val db = Database
                    .setup(it.context.applicationContext)
                    .noteDao()

                // Deleting the note from the database
                db.deleteNote(notes[position])

                // Running in main thread
                withContext(Dispatchers.Main) {
                    // Removing the note from the list
                    notes.remove(notes[position])
                    // Notifying the recycler view that the data has changed
                    notifyItemRemoved(position)
                    // Notifying the recycler view that the data set has changed
                    notifyDataSetChanged()
                    // Toasting
                    Toast.makeText(it.context.applicationContext, "Note deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Setting an onClick listener on the container itself
        holder.container.setOnClickListener {
            // Navigating to EditNote fragment
            val action = HomeFragmentDirections.actionHomeFragmentToEditFragment(notes[position])
            holder.container.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = notes.size
}