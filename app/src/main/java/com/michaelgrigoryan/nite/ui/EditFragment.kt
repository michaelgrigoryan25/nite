package com.michaelgrigoryan.nite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.michaelgrigoryan.nite.R
import com.michaelgrigoryan.nite.database.Database
import com.michaelgrigoryan.nite.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class EditFragment : Fragment() {
    private val args by navArgs<EditFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        val deleteButton: FloatingActionButton = view.findViewById(R.id.delete_note_button)
        val saveButton: FloatingActionButton = view.findViewById(R.id.save_edited_note_button)
        val headingInput: TextInputEditText = view.findViewById(R.id.input_heading)
        val contentInput: TextInputEditText = view.findViewById(R.id.input_content)

        headingInput.setText(args.noteToBeUpdated.heading)
        contentInput.setText(args.noteToBeUpdated.note)
        headingInput.doOnTextChanged { text, _, _, _ ->
            if (text?.length == 0 && contentInput.text?.length == 0) {
                saveButton.hide()
            } else {
                saveButton.show()
            }
        }
        contentInput.doOnTextChanged { text, _, _, _ ->
            if (text?.length == 0 && headingInput.text?.length == 0) {
                saveButton.hide()
            } else {
                saveButton.show()
            }
        }
        deleteButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val db = Database.setup(requireContext()).noteDao()

                db.deleteNote(Note(
                    args.noteToBeUpdated.id,
                    args.noteToBeUpdated.heading,
                    args.noteToBeUpdated.note,
                    args.noteToBeUpdated.time
                ))

                withContext(Dispatchers.Main) {
                    view.findNavController().navigate(R.id.action_editFragment_to_homeFragment)
                }
            }
        }

        saveButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val db = Database.setup(requireContext()).noteDao()
                val updatedNote = Note(
                    args.noteToBeUpdated.id,
                    headingInput.text.toString(),
                    contentInput.text.toString(),
                    SimpleDateFormat("dd/M/yyyy hh:mm", Locale.getDefault()).format(Date())
                )
                db.updateNote(updatedNote)

                withContext(Dispatchers.Main) {
                    view.clearFocus()
                    view.findNavController().navigate(R.id.action_editFragment_to_homeFragment)
                }
            }
        }

        return view
    }
}
