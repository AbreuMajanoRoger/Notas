package com.example.myapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityAddNotesBinding


class ActivityAddNotes : AppCompatActivity() {


    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var  db:NotesDataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db =NotesDataBaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note = Note(0, title,content)
            db.insertNote(note)
            finish()
            Toast.makeText(this,"NOTA guardada", Toast.LENGTH_SHORT).show()

         }
    }
}