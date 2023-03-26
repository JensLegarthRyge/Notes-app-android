package com.example.notes_app_android.model

class Note(private var id: Long, private var title: String, private var body: String) {

    fun getId(): Long{
        return id
    }

    fun getBody(): String{
        return body
    }

    fun getTitle(): String {
        return title
    }
}