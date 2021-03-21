package com.michaelgrigoryan.nite.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.michaelgrigoryan.nite.R
import com.michaelgrigoryan.nite.adapters.RecyclerAdapter
import com.michaelgrigoryan.nite.database.Database
import com.michaelgrigoryan.nite.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // All notes
        val notes: MutableList<Note> = mutableListOf()
        // No notes message holder
        val noNotes: TextView = view.findViewById(R.id.no_notes)
        // Recycler View with the list of notes
        val recyclerView: RecyclerView = view.findViewById(R.id.notes)
        // Floating Action Button to create a note
        val buttonCreateNote: FloatingActionButton = view.findViewById(R.id.create_note)

        // Function to check if there are any notes
        fun checkNoNotes() {
            // If there are no notes
            if (notes.isEmpty()) {
                // Setting the no notes message visibility to VISIBLE
                noNotes.visibility = View.VISIBLE
                // Setting the visibility of the Recycler View to GONE
                recyclerView.visibility = View.GONE
            } else {
                // Attaching the adapter to the Recycler View with our notes
                recyclerView.adapter = RecyclerAdapter(notes)
                // Setting the visibility to VISIBLE
                recyclerView.visibility = View.VISIBLE
            }
        }

        // Setting the adapter of the Recycler View
        recyclerView.adapter = RecyclerAdapter(notes)
        // Setting the layout manager of the Recycler View
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Setting a onClick listener on the FAB to navigate to the create note fragment
        buttonCreateNote.setOnClickListener {
            // Switching fragments
            view.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }

        // Launching a coroutine task
        GlobalScope.launch(Dispatchers.IO) {
            // Initializing the database
            val db = Database.setup(requireContext().applicationContext).noteDao()
            // Getting all the notes as a mutable list
            notes.addAll(db.getAll())

            // Checking if notes are empty
            withContext(Dispatchers.Main) { checkNoNotes() }
        }
    }
}