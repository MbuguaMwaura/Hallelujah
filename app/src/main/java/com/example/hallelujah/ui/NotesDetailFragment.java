package com.example.hallelujah.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hallelujah.R;
import com.example.hallelujah.models.Note;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesDetailFragment extends Fragment {
    @BindView(R.id.noteDetailsBody)
    TextView noteDetailsBody;
    @BindView(R.id.notedetailsTitle) TextView noteDetailsTitle;
    private Note mNote;

    public static NotesDetailFragment newInstance(Note note){
        NotesDetailFragment notesDetailFragment = new NotesDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("note", Parcels.wrap(note));
        notesDetailFragment.setArguments(args);
        return notesDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNote = Parcels.unwrap(getArguments().getParcelable("note"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_notes_detail, container, false);
        ButterKnife.bind(this,view);
        noteDetailsTitle.setText(mNote.getTitle());
        noteDetailsBody.setText(mNote.getNote());
        return view;
    }

}
