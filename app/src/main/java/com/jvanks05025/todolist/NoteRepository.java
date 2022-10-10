package com.jvanks05025.todolist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> notes;
    ExecutorService executors = Executors.newSingleThreadExecutor();

    public NoteRepository (Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao= database.noteDao();
        notes= noteDao.getAllNotes();
    }
    public void insert (Note note){
        executors.execute(() -> noteDao.insert(note));
//        new InsertNoteAsyncTask(noteDao).execute(note);

    }
    public void update (Note note){
        executors.execute(() -> noteDao.update(note));
//        new UpdateNoteAsyncTask(noteDao).execute(note);
    }
    public void delete (Note note){
        executors.execute(() -> noteDao.delete(note));
//        new DeleteNoteAsyncTask(noteDao).execute(note);
    }
    public LiveData<List<Note>> getAllNotes(){
        return notes;
    }
}
