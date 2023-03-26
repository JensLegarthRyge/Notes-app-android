package com.example.notes_app_android.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.example.notes_app_android.R
import com.example.notes_app_android.adapter.NoteAdapter
import com.example.notes_app_android.model.Note

class MainActivity : AppCompatActivity() {

    private lateinit var buttonAddNote: Button
    private lateinit var buttonEdit: Button
    private lateinit var notesListContainer: ListView
    private lateinit var noteArrayList: ArrayList<Note>
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddNote = findViewById(R.id.btnAddNote)
        buttonEdit = findViewById(R.id.btnEdit)
        notesListContainer = findViewById(R.id.notesListContainer)

        noteArrayList = arrayListOf()
        noteAdapter = NoteAdapter(noteArrayList, this)

        notesListContainer.adapter = noteAdapter

        buttonAddNote.setOnClickListener {
            val newNote = Note(System.currentTimeMillis(), "New Note", "")
            noteArrayList.add(newNote)
            noteAdapter.notifyDataSetChanged()
        }

        notesListContainer.setOnItemClickListener { _, _, position, _ ->
            val note = noteAdapter.getItem(position) as Note
            val intent = Intent(this, NoteEditActivity::class.java).apply {
                putExtra("noteId", note.getId())
                putExtra("noteTitle", note.getTitle())
                putExtra("noteBody", note.getBody())
            }
            startActivity(intent)
        }
    }
}
