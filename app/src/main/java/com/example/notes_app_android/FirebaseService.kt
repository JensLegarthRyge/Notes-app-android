import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

data class Note(var id: String = "", var title: String = "", var body: String = "")

class FirebaseService {
    private val db = FirebaseFirestore.getInstance()
    private val collectionRef = db.collection("notes")

    suspend fun createNote(note: Note): Note {
        return suspendCoroutine { continuation ->
            collectionRef.add(note).addOnSuccessListener { documentReference ->
                val newNote = note.copy(id = documentReference.id)
                continuation.resume(newNote)
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }

    suspend fun getNoteById(noteId: String): Note? {
        return suspendCoroutine { continuation ->
            collectionRef.document(noteId).get().addOnSuccessListener { documentSnapshot ->
                val note = documentSnapshot.toObject(Note::class.java)
                note?.id = documentSnapshot.id
                continuation.resume(note)
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }

    suspend fun getAllNotes(): List<Note> {
        return suspendCoroutine { continuation ->
            collectionRef.get().addOnSuccessListener { querySnapshot ->
                val notes = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    val note = documentSnapshot.toObject(Note::class.java)
                    note?.id = documentSnapshot.id
                    note
                }
                continuation.resume(notes)
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }

    suspend fun updateNote(note: Note): Note {
        return suspendCoroutine { continuation ->
            collectionRef.document(note.id).set(note).addOnSuccessListener {
                continuation.resume(note)
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }

    suspend fun deleteNote(noteId: String) {
        return suspendCoroutine { continuation ->
            collectionRef.document(noteId).delete().addOnSuccessListener {
                continuation.resume(Unit)
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }

    fun getAllNotesContinuous(listener: (List<Note>) -> Unit): ListenerRegistration {
        return collectionRef.addSnapshotListener { querySnapshot, error ->
            if (error != null) {
                // Handle the error, e.g., log it or show a message to the user
                return@addSnapshotListener
            }

            if (querySnapshot != null) {
                val notes = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    val note = documentSnapshot.toObject(Note::class.java)
                    note?.id = documentSnapshot.id
                    note
                }
                listener(notes)
            }
        }
    }
}
