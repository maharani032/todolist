package com.jvanks05025.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes= new ArrayList<>();
    private OnItemClickListener listenerDelete,listenerUpdate;

    @NonNull
    @Override
    public NoteAdapter.NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_card,parent,false);
        return  new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteHolder holder, int position) {
//        transfer data dari setiap java object ke noteholder
//        jadi setiap note datang dari database berupa java object
        Note currentNote =notes.get(position);
        holder.titleTodo.setText(currentNote.getTodo());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    public void setNotes(List<Note> notes){
//        mengamati perubahan live data
        this.notes= notes;
        notifyDataSetChanged();
    }
    public Note getNotes(int position){
        return notes.get(position);
    }
    //    untuk menghubungkan bagian backend dengan frontend
    class NoteHolder extends RecyclerView.ViewHolder{
        TextView titleTodo;
        ImageView doneButton;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            titleTodo=itemView.findViewById(R.id.titleToDo);
            doneButton=itemView.findViewById(R.id.doneButton);

            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if (listenerDelete != null && position != RecyclerView.NO_POSITION){
                        listenerDelete.onItemClick(notes.get(position));
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(listenerUpdate!=null && position!=RecyclerView.NO_POSITION){
                        listenerUpdate.onItemClick(notes.get(position));
//                    dialog=new AlertDialog.Builder(context).setView(R.layout.add_note);

                }
            }
            });


        }
    }
    public interface OnItemClickListener{
        void onItemClick(Note note);
    }
    public void setOnItemClickListenerDelete(OnItemClickListener listener){
        this.listenerDelete=listener;
    }
    public void setOnItemClickListenerUpdate(OnItemClickListener listener){
        this.listenerUpdate=listener;
    }
}
