package com.example.wow_this_app.model;

public class Note {

    public int index;
    private static final int n = 12;

    public Note(int index){
        this.index = index;
    }

    public Note(String note){
        this.index = getNoteIndex(note);
    }

    // The note A has the number 0
    private static int A() {
        return 0;
    }

    // The note A# has the number 1
    private static int A_() {
        return 1;
    }

    // The note B has the number 1
    private static int B() {
        return 2;
    }

    // The note C has the number 3
    private static int C() {
        return 3;
    }

    // The note C# has the number 4
    private static int C_() {
        return 4;
    }

    // The note D has the number 5
    private static int D() {
        return 5;
    }

    // The note D# has the number 6
    private static int D_() {
        return 6;
    }

    // The note E has the number 7
    private static int E() {
        return 7;
    }

    // The note F has the number 8
    private static int F() {
        return 8;
    }

    // The note F# has the number 9
    private static int F_() {
        return 9;
    }

    // The note G has the number 10
    private static int G() {
        return 10;
    }

    // The note G# has the number 11
    private static int G_() {
        return 11;
    }


    public static int getNoteIndex(String note) {
        switch (note) {
            case "A":
                return 0;
            case "A#":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "C#":
                return 4;
            case "D":
                return 5;
            case "D#":
                return 6;
            case "E":
                return 7;
            case "F":
                return 8;
            case "F#":
                return 9;
            case "G":
                return 10;
            case "G#":
                return 11;
        }
        return -1;
    }
    public static String getNoteName(int index) {
        switch (index) {
            case 0:
                return "A";
            case 1:
                return "A#";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "C#";
            case 5:
                return "D";
            case 6:
                return "D#";
            case 7:
                return "E";
            case 8:
                return "F";
            case 9:
                return "F#";
            case 10:
                return "G";
            case 11:
                return "G#";
        }
        return "[EXPLOSION!]";
    }

    public static int getNoteDifference(String note1, String note2 ){
        int dif = getNoteIndex(note1) - getNoteIndex(note2);
        return dif > 6 ? dif : n - dif;
    }

    public String transposeNote(int halfsteps){
        return getNoteName((index + halfsteps) % n);
    }


    public void increment() {
        index = (index + 1) % n;
    }

    @Override
    public String toString() {
        return getNoteName(index);
    }
}