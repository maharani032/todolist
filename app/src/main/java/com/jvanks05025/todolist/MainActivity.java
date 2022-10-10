package com.jvanks05025.todolist;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
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
    ActivityResultLauncher<Intent> activityResultLauncherForAddNote;
    ImageView addButton;
    TextView saveButton, cancelButton;
    EditText inputToDo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RegisterActivityForAddNote();

        addButton=findViewById(R.id.addButton);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteAdapter adapter= new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel= new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);
        //                UPDATE RECYCLER VIEW
        noteViewModel.getAllNotes().observe(this, adapter::setNotes);

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

                Intent i =new Intent();
                i.putExtra("titletodo",noteTitle);
                setResult(RESULT_OK,i);
                popupWindow.dismiss();
            }
        });

    }

    public void RegisterActivityForAddNote(){
        activityResultLauncherForAddNote = registerForActivityResult(new ActivityResultContracts
                .StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Log.i("informasi","in registeractivityforaddnote");
                int ResultCode = result.getResultCode();
                Intent data= result.getData();

                if (ResultCode==RESULT_OK && data !=null){
                    String title=data.getStringExtra("titletodo");
                    Note note= new Note(title);
                    noteViewModel.insert(note);
                }
            }
        });
    }
}
