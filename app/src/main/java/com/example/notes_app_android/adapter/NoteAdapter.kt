package com.example.notes_app_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.notes_app_android.R
import com.example.notes_app_android.model.Note

class NoteAdapter(private val notesList: List<Note>, private val context: Context) : BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return notesList.size
    }

    override fun getItem(p0: Int): Any {
        return notesList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return notesList[p0].getId()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView?: inflater.inflate(R.layout.notes_row, null)
        val noteTitleRow: TextView = view.findViewById(R.id.noteTitleRow)
        noteTitleRow.text = notesList[position].getTitle()
        return view
    }
}