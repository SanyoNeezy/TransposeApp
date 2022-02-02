package com.example.wow_this_app.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Optional;

public enum Note {
    A(0, "A"),
    A_(1, "A#"),
    B(2, "B"),
    C(3, "C"),
    C_(4, "C#"),
    D(5, "D"),
    D_(6, "D#"),
    E(7, "E"),
    F(8, "F"),
    F_(9, "F#"),
    G(10, "G"),
    G_(11, "G#");

    private final int index;
    private final String name;

    Note(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Note transpose(int halfSteps) {
        int newIndex = (index + halfSteps) % values().length;
        return Note.find(newIndex)
                .orElseThrow(() -> new IllegalArgumentException("Invalid index: " + newIndex));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<Note> find(int index) {
        for (Note value : Note.values()) {
            if (value.index == index) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return name;
    }
}

//public class Note {
//
//    public int index;
//    private static final int n = 12;
//
//    public Note(String note) {
//
//        this.index = getNoteIndex(note);
//    }
//
//    public static int getNoteIndex(String note) {
//        switch (note) {
//            case "A":
//                return 0;
//            case "A#":
//                return 1;
//            case "B":
//                return 2;
//            case "C":
//                return 3;
//            case "C#":
//                return 4;
//            case "D":
//                return 5;
//            case "D#":
//                return 6;
//            case "E":
//                return 7;
//            case "F":
//                return 8;
//            case "F#":
//                return 9;
//            case "G":
//                return 10;
//            case "G#":
//                return 11;
//        }
//        return -1;
//    }
//
//    public static String getNoteName(int index) {
//        switch (index) {
//            case 0:
//                return "A";
//            case 1:
//                return "A#";
//            case 2:
//                return "B";
//            case 3:
//                return "C";
//            case 4:
//                return "C#";
//            case 5:
//                return "D";
//            case 6:
//                return "D#";
//            case 7:
//                return "E";
//            case 8:
//                return "F";
//            case 9:
//                return "F#";
//            case 10:
//                return "G";
//            case 11:
//                return "G#";
//        }
//        return "[EXPLOSION!]";
//    }
//
//    public static int getNoteDifference(String note1, String note2) {
//        int dif = getNoteIndex(note1) - getNoteIndex(note2);
//        return dif > 6 ? dif : n - dif;
//    }
//
//    public String transposeNote(int halfsteps) {
//        return getNoteName((index + halfsteps) % n);
//    }
//
//
//    public void increment() {
//        index = (index + 1) % n;
//    }
//
//    @Override
//    public String toString() {
//        return getNoteName(index);
//    }
//}