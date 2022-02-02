package com.example.wow_this_app.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CapoProgression implements Comparator<CapoProgression> {
    public int fret;
    public List<Chord> progression;

    public CapoProgression(){
        progression = new ArrayList<>();
        fret = 0;
    }

    public CapoProgression(int fret, List<Chord> progression) {
        this.fret = fret;
        this.progression = progression;
    }

    public void add( Chord chord){
        progression.add(chord);
    }

    public int numberOfStandardChords() { // throws InvalidChordException {
        int i = 0;
        for (Chord chord : progression) {
            if (chord.isStandardChord()) i++;
        }
        return i;
    }

    public static int numberOfStandardChords(List<Chord> progression) { // throws InvalidChordException {
        int i = 0;
        for (Chord chord : progression) {
            if (chord.isStandardChord()) i++;
        }
        return i;
    }

    @Override
    public String toString() {
        return "[" + fret + "] " + Chord.progressionToString(progression) + " (" + numberOfStandardChords() + " ♫)";
    }

    @Override
    public int compare(CapoProgression c1, CapoProgression c2) {
        return numberOfStandardChords(c1.progression) - numberOfStandardChords(c2.progression);
    }
}
