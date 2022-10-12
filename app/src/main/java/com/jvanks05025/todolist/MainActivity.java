package com.jvanks05025.todolist;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    ImageView addButton;
    TextView saveButton, cancelButton,textView1,textView2;
    EditText inputToDo;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog.Builder dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton=findViewById(R.id.addButton);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteAdapter adapter= new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel= new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);
        //                UPDATE RECYCLER VIEW
        noteViewModel.getAllNotes().observe(this, adapter::setNotes);

        adapter.setOnItemClickListenerDelete(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                noteViewModel.delete(note);
            }
        });
        adapter.setOnItemClickListenerUpdate(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                int id=note.getId();
                dialog= new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater =(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View updateToDo=inflater.inflate(R.layout.add_note,null);

                saveButton=updateToDo.findViewById(R.id.save_button);
                cancelButton=updateToDo.findViewById(R.id.cancel_button);
                inputToDo=updateToDo.findViewById(R.id.inputToDo);
                textView1=updateToDo.findViewById(R.id.textview1);
                saveButton.setText("Update");
                inputToDo.setText(note.getTodo().toString());
                textView1.setText("Update a task");
                dialog.setView(updateToDo);
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title=inputToDo.getText().toString();
                        if(inputToDo.getText().length()!=0){
                            Note note= new Note(title);
                            note.setId(id);
                            noteViewModel.update(note);
                        }
                        alertDialog.dismiss();

                    }
                });

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopUP(view);
            }
        });
    }


    public void onButtonShowPopUP(View view){
        LayoutInflater inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View addPopUpView=inflater.inflate(R.layout.add_note,null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(addPopUpView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        saveButton=addPopUpView.findViewById(R.id.save_button);
        cancelButton=addPopUpView.findViewById(R.id.cancel_button);
        inputToDo=addPopUpView.findViewById(R.id.inputToDo);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("informasi","in save button");

                String noteTitle=inputToDo.getText().toString();
                if(noteTitle.length()!=0){
                    Note note= new Note(noteTitle);
                    noteViewModel.insert(note);
                }

                popupWindow.dismiss();
            }

        });
        if (!popupWindow.isShowing()){
            Log.i("informasi","in finish");
            finish();
        }
    }

}
