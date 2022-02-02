package com.example.wow_this_app.model;

public enum ChordType {

    MIN,
    MAJ,
    MIN_7,
    MAJ_7,
    MIN_MAJ_7,
    MAJ_MIN_7;

    public static ChordType of(String type) {
        switch (type) {
            case "min":
                return ChordType.MIN;
            case "maj":
                return ChordType.MAJ;
            case "min7":
                return ChordType.MIN_7;
            case "maj7":
                return ChordType.MAJ_7;
            case "minmaj7":
                return ChordType.MIN_MAJ_7;
            case "majmin7":
                return ChordType.MAJ_MIN_7;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }
}
