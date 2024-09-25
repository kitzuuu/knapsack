package com.example.Solver;

import com.example.Data.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolveForFillPentominoes {
    public static String serializeField(int[][][] field) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                for (int k = 0; k < field[i][j].length; k++) {
                    sb.append(field[i][j][k]);
                    if (k < field[i][j].length - 1) sb.append(","); // Separate elements in the same row
                }
                if (j < field[i].length - 1) sb.append("|"); // Separate rows
            }
            if (i < field.length - 1) sb.append(";"); // Separate layers
        }
        return sb.toString();
    }

    public static LocalTime timer = LocalTime.now().plusSeconds(10);


    public static HashMap<String, Boolean> memo = new HashMap<>();
    public static String mostFilled;

    public static int emptyspaces = 1320;
    public static int lowestEmptySpaces = 1320;


    public static boolean checkIfFilled(int[][][] field) {
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                for (int z = 0; z < field[x][y].length; z++) {
                    if (field[x][y][z] == 0)
                        return false;
                }
            }
        }
        return true;
    }

    public static boolean solveForFill(int[][][] field) {
        if(timer.isBefore(LocalTime.now())){
            return true;
        }
        String currentState = getStateRepresentation(field);


        if (memo.containsKey(currentState)) {
            return memo.get(currentState);
        }
        if(!isContiguousSpaceAvailable(field)){
            memo.put(currentState,false);
            return false;
        }
        if(lowestEmptySpaces>emptyspaces){
            lowestEmptySpaces=emptyspaces;
            mostFilled=serializeField(field);
        }
        // Base case: Check if the field is fully packed
        if (checkIfFilled(field)) {
            memo.put(currentState, true);
            return true;
        }

        // Attempt to place each type of parcel in every possible position and orientation
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                for (int z = 0; z < field[0][0].length; z++) {
                    for (int i = 0; i < PentominoDatabase.intPentoDB.length; i++) {
                        for (int rotation = 0; rotation < PentominoDatabase.intPentoDB[i].length; rotation++) {
                            // Your existing logic to try placing a parcel
                            if (canPlacePiece(field, PentominoDatabase.intPentoDB[i][rotation], x, y, z)) {
                                addPiece(field, PentominoDatabase.intPentoDB[i][rotation], x, y, z);
                                if (solveForFill(field)) {
                                    return true; // Success, no need to memoize here since we're returning immediately
                                }
                                removePiece(field, PentominoDatabase.intPentoDB[i][rotation], x, y, z);
                            }
                        }
                    }
                }
            }
        }

        // If no parcel can be placed, mark this state as explored and not solvable
        memo.put(currentState, false);
        return false;
    }

    private static String getStateRepresentation(int[][][] field) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                for (int k = 0; k < field[i][j].length; k++) {
                    sb.append(field[i][j][k]).append(",");
                }
            }
        }
        return sb.toString();
    }

    private static void removePiece(int[][][] field, int[][][] piece, int fieldX, int fieldY, int fieldZ) {
        for (int x = 0; x < piece.length; x++) {
            for (int y = 0; y < piece[x].length; y++) {
                for (int z = 0; z < piece[x][y].length; z++) {
                    if (piece[x][y][z] != 0) {
                        field[x + fieldX][y + fieldY][z + fieldZ] = 0;
                        emptyspaces++;
                    }
                }
            }
        }
    }

    public static void addPiece(int[][][] matrix, int[][][] piece, int pieceX, int pieceY, int pieceZ) {

        for (int x = 0; x < piece.length; x++) {
            for (int y = 0; y < piece[x].length; y++) {
                for (int z = 0; z < piece[x][y].length; z++) {
                    if (piece[x][y][z] != 0) {
                        matrix[x + pieceX][y + pieceY][z + pieceZ] = piece[x][y][z];
                        emptyspaces--;
                    }
                }
            }
        }
    }
    private static boolean isContiguousSpaceAvailable(int[][][] field) {
        // Check for a 2x3 or 3x2 space in each possible plane
        for (int x = 0; x < field.length - 2; x++) { // -2 to allow room for 3 units in x direction
            for (int y = 0; y < field[0].length - 2; y++) { // -2 to allow room for 3 units in y direction
                for (int z = 0; z < field[0][0].length; z++) {
                    // Check if a 2x3 area starting from (x, y, z) is empty in xy plane
                    if (checkArea(field, x, y, z, 3, 2) || checkArea(field, x, y, z, 2, 3)) {
                        return true;
                    }
                }
            }
        }
        return false; // No suitable area found
    }

    private static boolean checkArea(int[][][] field, int startX, int startY, int startZ, int dimX, int dimY) {
        for (int x = startX; x < startX + dimX; x++) {
            for (int y = startY; y < startY + dimY; y++) {
                if (field[x][y][startZ] != 0) {
                    return false; // Part of the area is occupied
                }
            }
        }
        return true; // Found an empty area of the required dimensions
    }


    public static boolean canPlacePiece(int[][][] matrix, int[][][] piece, int fieldX, int fieldY, int fieldZ) {
        // Preliminary bounds check to ensure the piece fits in the matrix dimensions
        if (fieldX + piece.length > matrix.length ||
                fieldY + piece[0].length > matrix[0].length ||
                fieldZ + piece[0][0].length > matrix[0][0].length) {
            return false; // The piece would extend outside the matrix
        }

        for (int x = 0; x < piece.length; x++) {
            for (int y = 0; y < piece[x].length; y++) {
                for (int z = 0; z < piece[x][y].length; z++) {
                    // Skip empty parts of the piece
                    if (piece[x][y][z] == 0) continue;

                    // Since we've already checked bounds, we can now check for occupation directly
                    if (matrix[fieldX + x][fieldY + y][fieldZ + z] != 0) {
                        return false; // Target position is already occupied
                    }
                }
            }
        }
        return true;
    }


}
