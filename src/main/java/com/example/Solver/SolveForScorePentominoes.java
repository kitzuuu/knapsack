package com.example.Solver;

import com.example.Data.*;



public class SolveForScorePentominoes {

    public static int currentValue = 0;
    public static void addPiece(int[][][] matrix, int[][][] piece, int pieceX, int pieceY, int pieceZ) {
        for (int x = 0; x < piece.length; x++) {
            for (int y = 0; y < piece[x].length; y++) {
                for (int z = 0; z < piece[x][y].length; z++) {
                    if (piece[x][y][z]!=0) {
                        matrix[x + pieceX][y + pieceY][z + pieceZ] = piece[x][y][z];
                    }
                }
            }
        }
    }

    public static boolean canPlacePiece(int[][][] matrix, int[][][] piece, int fieldX, int fieldY, int fieldZ) {
        for (int x = 0; x < piece.length; x++) {
            for (int y = 0; y < piece[x].length; y++) {
                for (int z = 0; z < piece[x][y].length; z++) {
                    if (fieldX + x  >= matrix.length || fieldY + y >= matrix[y].length ||
                            fieldZ + z >= matrix[x][y].length) {
                        return false; // out of bounds
                    }
                    if (matrix[fieldX + x][fieldY + y][fieldZ + z] != 0) {
                        return false; // target position already occupied
                    }
                }
            }
        }
        return true;
    }

    public static void solve(int[][][] field) {
        int[] values = {3,4,5};
        int[][][][][] parcels = PentominoDatabase.intPentoDB;
        for(int parcel=0;parcel<3;parcel++)
        {
            int biggestValueI = 0;
            for(int i=0;i<3;i++)
            {
                if(values[i]>values[biggestValueI])
                {
                    biggestValueI = i;
                }
            }
            for(int rotation = 0;rotation<parcels[biggestValueI].length;rotation++)
            {
                int[][][] currentPiece = parcels[biggestValueI][rotation];
                {

                    {
                        for (int x = 0; x < field.length; x++) {
                            for (int y = 0; y < field[x].length; y++) {
                                for (int z = 0; z < field[x][y].length; z++) {
                                    if (canPlacePiece(field, currentPiece ,x,y,z )) {
                                        addPiece(field,currentPiece,x,y,z);
                                        currentValue += values[biggestValueI];

                                    }
                                }
                            }
                        }

                    }
                }
            }
            values[biggestValueI] = 0;
        }
    }

}

