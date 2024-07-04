package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityAddNotesBinding
import com.example.myapplication.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: NotesDataBaseHelper
    private var noteId: Int= -1
    private lateinit var note: Note




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDataBaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if(noteId == -1){
            finish()
            return
        }

        note = db.getNoteById(noteId)
       binding.titleTextViewUpdate.setText(note.title)
        binding.contenTextViewUpdate.setText(note.content)

        binding.optionsButtonUpdate.setOnClickListener {
            val newTitle = binding.titleTextViewUpdate.text.toString()
            val newContent = binding.contenTextViewUpdate.text.toString()
            note.title = newTitle
            note.content = newContent
            db.updateNote(note)
            finish()
            Toast.makeText(this, "Cambio guardado", Toast.LENGTH_SHORT).show()
                    }
          }
}