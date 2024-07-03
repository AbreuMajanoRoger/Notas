package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.NoteItemBinding


class NotesAdapter (private var notes: MutableList<Note>, context: Context):RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){


    class NoteViewHolder(private val binding: NoteItemBinding):
            RecyclerView.ViewHolder(binding.root){
                val titleTextView: TextView = binding.titleTextView
                val contenTextView: TextView = binding.contenTextView
                val optionButton: ImageView = binding.optionsButton
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
      val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
    val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contenTextView.text = note.content

        holder.optionButton.setOnClickListener {
            val popupMenu = PopupMenu(holder.itemView.context, holder.optionButton)
            popupMenu.inflate(R.menu.menu_item_notes)

            popupMenu.setOnMenuItemClickListener {menuItem ->
                when(menuItem.itemId){
                    R.id.editOption ->{
                        val intent = Intent(holder.itemView.context,UpdateActivity::class.java).apply {
                            putExtra("note_id, note.id")
                        }
                        holder.itemView.context.startActivity(intent)
                        true
                    }

                    R.id.deleteOption ->{
                        db.deleteNote(note.id)
                        removeItem(holder.adapterPosition)
                        Toast.makeText(holder.itemView.context,"Note Deleted", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }

            }





        }






    }

    fun refreshData(newNotes: List<Note>){

        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()

    }


}

