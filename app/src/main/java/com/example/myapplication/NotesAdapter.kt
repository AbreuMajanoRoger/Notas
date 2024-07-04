package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.NoteItemBinding


class NotesAdapter (private var notes: MutableList<Note>, context: Context):RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    private val db: NotesDataBaseHelper = NotesDataBaseHelper(context)

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
                        val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                            putExtra("note_id", note.id)
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

                        popupMenu.show()

                    popupMenu.setOnDismissListener {
                        popupMenu.dismiss()
                             }

                          }

                    }
                fun refreshData(newNotes: List<Note>){
                 notes.clear()
                 notes.addAll(newNotes)
                notifyDataSetChanged()
            }

            private fun removeItem(position: Int){
                notes.removeAt(position)
                notifyItemRemoved(position)

            }

            fun getItem(position: Int): Note{
                return notes[position]

            }

        fun enableSwipe(recyclerView: RecyclerView){
            val swipeHelper = object: ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val note = getItem(position)
                    when(direction){
                        ItemTouchHelper.LEFT ->{
                            val intent = Intent(
                                    viewHolder.itemView.context,
                                    UpdateActivity::class.java
                            ).apply{
                                putExtra("note_id", note.id)
                            }
                            viewHolder.itemView.context.startActivity(intent)

                        }
                        ItemTouchHelper.RIGHT->{
                            db.deleteNote(note.id)
                            removeItem(position)
                            Toast.makeText(viewHolder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
                                  }

                              }
                         }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    viewHolder?.itemView.let{itemView ->
                        val iconLeft: Drawable? = ContextCompat.getDrawable(viewHolder.itemView.context, R.drawable.baseline_edit_24)
                        val iconRight : Drawable? = ContextCompat.getDrawable(viewHolder.itemView.context, R.drawable.baseline_delete_24)

                        val backgroundLeft = ColorDrawable(Color.BLUE)
                        val backgroundRight = ColorDrawable(Color.RED)
                        val itemView = viewHolder.itemView
                        val iconMargin = (itemView.height - iconLeft!!.intrinsicHeight) /2

                        if(dX >0){
                            backgroundRight.setBounds(
                                itemView.left,
                                itemView.top,
                                itemView.right + dX.toInt(),
                                itemView.bottom
                            )

                            val iconTop =
                                itemView.top + (itemView.height - iconRight!!.intrinsicHeight) /2
                            val iconBotton = iconTop + iconRight.intrinsicHeight
                            val iconLeftMargin = itemView.right - iconMargin - iconRight.intrinsicHeight
                            val iconRightMargin = itemView.right - iconMargin
                            iconRight.setBounds(
                                iconLeftMargin,
                                iconTop,
                                iconRightMargin,
                                iconBotton
                            )
                            iconRight.draw(c)


                        } else if(dX <0 ){
                            backgroundLeft.setBounds(
                                itemView.left + dX.toInt(),
                                itemView.top,
                                itemView.right,
                                itemView.bottom)
                            backgroundLeft.draw(c)



                        }
                        super.onChildDraw(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                                    )

                               }


                          }
                   }
            val itemTouchHelper = ItemTouchHelper(swipeHelper)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }

}

