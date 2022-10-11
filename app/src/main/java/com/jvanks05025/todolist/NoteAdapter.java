package com.jvanks05025.todolist;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes= new ArrayList<>();
    private OnItemClickListener listener;
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
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(notes.get(position));
                    }
                }
            });

        }
    }
    public interface OnItemClickListener{
        void onItemClick(Note note);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
