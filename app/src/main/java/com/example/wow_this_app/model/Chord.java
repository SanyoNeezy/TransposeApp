package com.example.wow_this_app.model;

import static com.example.wow_this_app.model.CapoProgression.numberOfStandardChords;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Chord {
//    private static final List<Chord> standardChords = Arrays.asList(
//
//    );

    private final Note root;
    private final ChordType type;

    public Chord(Note root, ChordType type){
        this.root = root;
        this.type = type;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isStandardChord() {//throws InvalidChordException {
        int index = root.getIndex();
        Note note = Note.find(index).orElseThrow(() -> new IllegalArgumentException("Invalid index: "+index));
        switch (note) {
            case A:
                return true;
            case A_:
                return false;
            case B:
                return type == ChordType.MAJ_MIN_7;
            case C:
                return (type == ChordType.MAJ || type == ChordType.MAJ_7);
            case C_:
                return false;
            case D:
                return true;
            case D_:
                return false;
            case E:
                return true;
            case F:
                return type == ChordType.MAJ_7;
            case F_:
                return false;
            case G:
                return type == ChordType.MAJ;
            case G_:
                return false;
        }
//        throw new InvalidChordException();
        return false;
    }


    public static List<Chord> copyProgression(List<Chord> progression){
        List<Chord> copy = new ArrayList<>();
        for (Chord chord : progression) {
            copy.add(new Chord(chord.root, chord.type));
        }
        return copy;
    }

    // raises every Chord by 1 half step
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Chord> incrementChordProgression(List<Chord> progression) {
        List<Chord> incrementedProgression = copyProgression(progression);

        return incrementedProgression.stream()
                .map(chord -> new Chord(chord.root.transpose(1), chord.type))
                .collect(Collectors.toList());
//        for (Chord chord : incrementedProgression) {
//            chord.root.transpose(1);
//        }
//        return  incrementedProgression;
    }




    // returns a list of Chord progressions where the first item in the list is the progression passed
    // as a function argument and all the other ones are the same progresssion, but  incremented by
    // their index -1
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static List<List<Chord>> getAllProgressions(List<Chord> progression) {
        List<List<Chord>> allProgressions = new ArrayList<>();
;
        allProgressions.add(progression);
        for (int i = 0; i < 11; i++) {
            List<Chord> p = allProgressions.get(allProgressions.size()-1);
            allProgressions.add(incrementChordProgression(p));
        }
        
        return allProgressions;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<CapoProgression> getPossibleCapoProgressions(List<Chord> progression) {// throws InvalidChordException {
        List<List<Chord>> allProgressions = getAllProgressions(progression);
        List<CapoProgression> goodProgressions = new ArrayList<CapoProgression>();
        List<CapoProgression> badProgressions = new ArrayList<CapoProgression>();

        for (int i = 0; i < 12; i++) {
            List<Chord> p = allProgressions.get(i);

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

    public static String progressionToString(List<Chord> progression) {
        String outStr = progression.get(0).toString();

        for (int i = 1; i < progression.size(); i++) {
            outStr += " - " + progression.get(i);
        }
        return outStr;
    }
}



