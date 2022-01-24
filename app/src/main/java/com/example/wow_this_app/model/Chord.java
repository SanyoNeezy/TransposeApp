package com.example.wow_this_app.model;

import static com.example.wow_this_app.model.CapoProgression.numberOfStandardChords;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.wow_this_app.exception.InvalidChordException;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Chord {
    Note root;
    ChordType type;

    public Chord(String root, String type){
        this.root = new Note(root);
        switch (type){
            case "min":
                this.type = ChordType.min;
                break;
            case "maj":
                this.type= ChordType.maj;
                break;
            case "min7":
                this.type= ChordType.min7;
                break;
            case "maj7":
                this.type = ChordType.maj7;
                break;
            case "minmaj7":
                this.type = ChordType.minMaj7;
                break;
        }
    }

    public boolean isStandardChord() {//throws InvalidChordException {
        switch (Note.getNoteName(root.index)) {
            case "A":
                return true;
            case "A#":
                return false;
            case "B":
                return type == ChordType.majMin7;
            case "C":
                return (type == ChordType.maj || type == ChordType.maj7);
            case "C#":
                return false;
            case "D":
                return true;
            case "D#":
                return false;
            case "E":
                return true;
            case "F":
                return type == ChordType.maj7;
            case "F#":
                return false;
            case "G":
                return type == ChordType.maj;
            case "G#":
                return false;
        }
//        throw new InvalidChordException();
        return false;
    }


    public static ArrayList<Chord> copyProgression(ArrayList<Chord> progression){
        ArrayList<Chord> copy =  new ArrayList<>();
        for (Chord chord : progression) {
            copy.add(new Chord(chord.root.toString(), chord.type.toString()));
        }
        return copy;
    }

    // raises every Chord by 1 half step
    public static ArrayList<Chord> incrementChordProgression(ArrayList<Chord> progression) {
        ArrayList<Chord> incrementedProgression = copyProgression(progression);

        for (Chord chord : incrementedProgression) {
            chord.root.increment();
        }
        return  incrementedProgression;
    }




    // returns a list of Chord progressions where the first item in the list is the progression passed
    // as a function argument and all the other ones are the same progresssion, but  incremented by
    // their index -1
    private static ArrayList<ArrayList<Chord>> getAllProgressions(ArrayList<Chord> progression) {
        ArrayList<ArrayList<Chord>> allProgressions = new ArrayList<ArrayList<Chord>>();
;
        allProgressions.add(progression);
        for (int i = 0; i < 11; i++) {
            ArrayList<Chord> p = allProgressions.get(allProgressions.size()-1);
            allProgressions.add(incrementChordProgression(p));
        }
        
        return allProgressions;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<CapoProgression> getPossibleCapoProgressions(ArrayList<Chord> progression) {// throws InvalidChordException {
        ArrayList<ArrayList<Chord>> allProgressions = getAllProgressions(progression);
        ArrayList<CapoProgression> goodProgressions = new ArrayList<CapoProgression>();
        ArrayList<CapoProgression> badProgressions = new ArrayList<CapoProgression>();

        for (int i = 0; i < 12; i++) {
            ArrayList<Chord> p = allProgressions.get(i);

            int fret = (12 - i) % 12;
            if (numberOfStandardChords(p) > 0)
                goodProgressions.add(new CapoProgression(fret, allProgressions.get(i)));
            else
                badProgressions.add(new CapoProgression(fret, allProgressions.get(i)));
        }

        Comparator<CapoProgression> comparator = (c1, c2) -> numberOfStandardChords(c2.progression) - numberOfStandardChords(c1.progression) ;
        goodProgressions.sort(comparator);

        String goodString = "";
        for (int i = 0; i < goodProgressions.size(); i++) {
            goodString += goodProgressions.get(i) + "(" + goodProgressions.get(i).numberOfStandardChords() +" standard chord(s))\n";
        }
        System.out.println("Try these Progressions: \n" + goodString);

        String badString = "";
        for (int i = 0; i < badProgressions.size(); i++) {
            badString += badProgressions.get(i) + "\n";
        }
        System.out.println("Don't try these Progressions: \n" + badString);
        return goodProgressions;
    }

    @Override
    public String toString() {
        return root.toString() + type;
    }

    public static String progressionToString(ArrayList progression) {
        String outStr = progression.get(0).toString();

        for (int i = 1; i < progression.size(); i++) {
            outStr += " - " + progression.get(i);
        }
        return outStr;
    }
}

enum ChordType {
    min,
    maj,
    min7,
    maj7,
    majMin7,
    minMaj7
}

