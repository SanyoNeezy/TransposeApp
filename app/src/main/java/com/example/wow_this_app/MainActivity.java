package com.example.wow_this_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wow_this_app.model.CapoProgression;
import com.example.wow_this_app.model.Chord;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView textViewUserInput;
    TextView textViewSuggestion;
    EditText editText;
    Spinner spinnerChord;
    Spinner spinnerRoot;

    CapoProgression progression = new CapoProgression();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button buttonAdd = findViewById(R.id.buttonAdd);
        final Button buttonDelete = findViewById(R.id.buttonDelete);
        textViewUserInput = findViewById(R.id.textViewUserInput);
        textViewSuggestion = findViewById(R.id.textViewSuggestion);
        spinnerChord = findViewById(R.id.spinnerChordType);
        spinnerRoot = findViewById(R.id.spinnerRootNote);

//        Snackbar mySnackbar = Snackbar.make(buttonAdd, "addedChordProgression", BaseTransientBottomBar.LENGTH_SHORT);

        String[] itemsRoot = new String[]{"A", "A#", "B", "C", "C#", "D#", "D", "E", "F", "F#", "G#", "G"};
        String[] itemsChord = new String[]{"maj", "min", "maj7", "min7"};

        ArrayAdapter<String> adapterRoot = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsRoot);
        spinnerRoot.setAdapter(adapterRoot);
        ArrayAdapter<String> adapterChord = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsChord);
        spinnerChord.setAdapter(adapterChord);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                String selRoot = spinnerRoot.getSelectedItem().toString();
                String selChord = spinnerChord.getSelectedItem().toString();
                System.out.println("Selected root note: " + selRoot);
                System.out.println("Selected chord type: " + selChord);

                progression.add(new Chord(selRoot, selChord));
                textViewUserInput.setText("Your Progression: \n" + progression);

                ArrayList<CapoProgression> suggestions =  Chord.getPossibleCapoProgressions(progression.progression);

                String sugStr = "Best would be to try these: \n";
                for(int i = 0; i < suggestions.size(); i++){
                    sugStr += suggestions.get(i) + "\n";
                }
                textViewSuggestion.setText(sugStr);

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                progression = new CapoProgression();
                String txt = "Your Progression: \n";
                if(progression != null && progression.progression.size() > 0)
                    txt += progression;
                textViewUserInput.setText(txt);
                textViewSuggestion.setText("");
            }
        });
    }
}