package com.example.notes_app_android.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.notes_app_android.R

class NoteEditActivity : AppCompatActivity() {
    private lateinit var noteTitle: TextView
    private lateinit var noteBody: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        noteTitle = findViewById(R.id.noteTitle)
        noteBody = findViewById(R.id.noteBody)

        val noteId = intent.getLongExtra("noteId", -1)
        val noteTitleText = intent.getStringExtra("noteTitle")
        val noteBodyText = intent.getStringExtra("noteBody")

        noteTitle.text = noteTitleText
        noteBody.setText(noteBodyText)
    }
}
