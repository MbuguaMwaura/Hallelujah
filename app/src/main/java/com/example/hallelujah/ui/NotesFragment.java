package com.example.hallelujah.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hallelujah.R;
import com.example.hallelujah.models.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.saveNoteBtn)
    ImageView saveNoteBtn;
    @BindView(R.id.notetitle)
    EditText noteTitle;
    @BindView(R.id.saveNotes) ImageView saveNotePage;
    @BindView(R.id.noteBody) EditText noteBody;
    private Note mNote;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        ButterKnife.bind(this,view);





        saveNoteBtn.setOnClickListener(this);
        saveNotePage.setOnClickListener(this);
        return view ;
    }

    @Override
    public void onClick(View v) {
        if (v == saveNoteBtn){
            String titleText = noteTitle.getText().toString();
            String bodyText = noteBody.getText().toString();
            boolean validTitle = isValidTitle(titleText);
            boolean validBody = isValidBody(bodyText);
            if (!validBody || !validTitle ) return;
            Note note = new Note(bodyText,titleText);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("notes").child(uid)    ;
            DatabaseReference pushRef = reference.push();
            String pushID = pushRef.getKey();
            note.setPushId(pushID);
            pushRef.setValue(note);
            noteTitle.setText("");
            noteBody.setText("");
            Toast.makeText(getContext(),"Saving your note",Toast.LENGTH_LONG).show();

        }
        if (v == saveNotePage){
            Intent intent = new Intent(getContext(),SavedNotesActivity.class);
            startActivity(intent);
        }
    }

    private boolean isValidTitle(String title) {
        if (title.equals("")) {
            noteTitle.setError("Please write a title for the note");
            return false;
        }
        return true;
    }

    private boolean isValidBody(String body) {
        if (body.equals("")) {
            noteBody.setError("Please write a note");
            return false;
        }
        return true;
    }
}
