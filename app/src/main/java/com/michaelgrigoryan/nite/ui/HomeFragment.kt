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
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonCreateNote: FloatingActionButton = view.findViewById(R.id.create_note)
        buttonCreateNote.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }

        GlobalScope.launch(Dispatchers.IO) {
            val db = Database.setup(requireContext()).noteDao()
            val notes: List<Note> = db.getAll()

//            db.createNote(
//                Note(
//                    0,
//                    "This is a title",
//                    "This is a description",
//                    SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())
//                )
//            )

            withContext(Dispatchers.Main) {
                val recyclerView: RecyclerView = view.findViewById(R.id.notes)
                val noNotes: TextView = view.findViewById(R.id.no_notes)

                if (notes.isEmpty()) noNotes.visibility = View.VISIBLE
                else {
                    recyclerView.adapter = RecyclerAdapter(notes)
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
    }
}