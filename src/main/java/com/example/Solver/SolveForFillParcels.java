package com.example.Solver;

import com.example.Data.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolveForFillParcels {
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
                    for (int i = 0; i < ParcelDatabase.intParcelDB.length; i++) {
                        for (int rotation = 0; rotation < ParcelDatabase.intParcelDB[i].length; rotation++) {
                            // Your existing logic to try placing a parcel
                            if (canPlacePiece(field, ParcelDatabase.intParcelDB[i][rotation], x, y, z)) {
                                addPiece(field, ParcelDatabase.intParcelDB[i][rotation], x, y, z);
                                if (solveForFill(field)) {
                                    return true; // Success, no need to memoize here since we're returning immediately
                                }
                                removePiece(field, ParcelDatabase.intParcelDB[i][rotation], x, y, z);
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
        // First, check for a 2x2x1 space which satisfies Parcel A and B in some orientations
        boolean is2x2Available = checkForSpace(field, 2, 2, 1);
        // Then, check for a 3x3x1 space required for Parcel C and some orientations of A and B
        boolean is3x3Available = checkForSpace(field, 3, 3, 1);

        return is2x2Available || is3x3Available;
    }

    private static boolean checkForSpace(int[][][] field, int dimX, int dimY, int dimZ) {
        for (int x = 0; x <= field.length - dimX; x++) {
            for (int y = 0; y <= field[0].length - dimY; y++) {
                for (int z = 0; z <= field[0][0].length - dimZ; z++) {
                    if (isAreaEmpty(field, x, y, z, dimX, dimY, dimZ)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isAreaEmpty(int[][][] field, int startX, int startY, int startZ, int dimX, int dimY, int dimZ) {
        for (int x = startX; x < startX + dimX; x++) {
            for (int y = startY; y < startY + dimY; y++) {
                for (int z = startZ; z < startZ + dimZ; z++) {
                    if (field[x][y][z] != 0) {
                        return false; // Part of the area is occupied
                    }
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
