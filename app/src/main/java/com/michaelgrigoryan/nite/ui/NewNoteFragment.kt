package com.michaelgrigoryan.nite.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.findNavController
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

class NewNoteFragment : Fragment(R.layout.fragment_new_note) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveButton:   FloatingActionButton = view.findViewById(R.id.save_note_button)
        val headingInput: TextInputEditText = view.findViewById(R.id.input_heading)
        val contentInput: TextInputEditText = view.findViewById(R.id.input_content)

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

        saveButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val db = Database.setup(requireContext()).noteDao()
                val note = Note(
                        0,
                        headingInput.text.toString(),
                        contentInput.text.toString(),
                        SimpleDateFormat("MMMM dd hh:mm").format(Date())
                )

                db.createNote(note)
                withContext(Dispatchers.Main) {
                    view.clearFocus()
//                    view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
                    view.findNavController().popBackStack()
                }
            }
        }
    }
}