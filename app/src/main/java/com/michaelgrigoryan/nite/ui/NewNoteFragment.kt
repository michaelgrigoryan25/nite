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

        val contentInput: TextInputEditText = view.findViewById(R.id.input_content)
        val saveButton:   FloatingActionButton = view.findViewById(R.id.save_note_button)

        contentInput.doOnTextChanged { text, _, _, _ ->
            if (text!!.isEmpty()) saveButton.hide()
             else saveButton.show()
        }

        saveButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val db = Database.setup(requireContext()).noteDao()
                val note = Note(
                        0,
                        contentInput.text.toString(),
                        SimpleDateFormat("MMMM dd hh:mm").format(Date())
                )
                db.createNote(note)

                withContext(Dispatchers.Main) {
                    view.findNavController().popBackStack()
                    view.clearFocus()
                }
            }
        }
    }
}