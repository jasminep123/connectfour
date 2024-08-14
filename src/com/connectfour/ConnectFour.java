package com.connectfour;

import java.util.Scanner;

public class ConnectFour {
    // display the grid
    public static void displayGame(char[][] gameState) {
        System.out.println("---------------");
        for (int row = 0; row < gameState.length; row++) {
            System.out.print("|");
            for (int col = 0; col < gameState[0].length; col++) {
                System.out.print(gameState[row][col]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("---------------");
        }
        System.out.println();
    }

    // Put the players token on the top of the column they selected
    public static char[][] updateBoard(char[][] gameState, int move, char player) {
        for (int row = gameState.length-1; row >= 0; row--){
            if(gameState[row][move] != 'R' && gameState[row][move] != 'Y'){
                gameState[row][move] = player;
                break;
            }
        }
        return gameState;
    }

    // validate the player's move
    public static boolean validateMove(int move, char[][] gameState){
        // check if its placed in a valid column
        if (move < 0 || move > gameState[0].length-1){
            return false;
            // check if column is empty
        }
        if (gameState[0][move] == 'R' || gameState[0][move] == 'Y'){
            return false;
        }
        return true;
    }

    public static boolean checkRow(char[][] gameState, char player, int toWin){
        int numberTokens = 0;
        for(int row = 0; row<gameState.length; row++){
            numberTokens = 0;
            for (int col = 0;col < gameState[0].length;col++){
                if (gameState[row][col] != player){
                    numberTokens = 0;
                }else{
                    numberTokens +=1;
                }
            }
            if (numberTokens >= toWin) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkColumn(char[][] gameState, char player, int toWin){
        // check for column win
        int numberTokens = 0;
        for (int col = 0; col < gameState[0].length; col++) {
            numberTokens = 0;
            for (int row = 0; row < gameState.length; row++) {
                if (gameState[row][col] == player) {
                    numberTokens += 1;
                } else {
                    numberTokens = 0;
                }
            }
            if (numberTokens >= toWin) {
                return true;
            }
        }
        return  false;
    }

    public static  boolean checkDescendingDiagonal(char[][] gameState, char player, int toWin){
        // check for descending diagonal win
        int numberTokens = 0;
        // Loop to start from different diagonals
        for (int row = 0; row <= gameState.length - toWin; row++) {
            for (int col = 0; col <= gameState[0].length - toWin; col++) {
                numberTokens = 0;

                // Check the diagonal starting from (startRow, startCol)
                for (int i = 0; i < toWin; i++) {
                    if (gameState[row + i][col + i] == player) {
                        numberTokens += 1;
                    } else {
                        numberTokens = 0;
                        break;
                    }
                }
                if (numberTokens == toWin) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkAscendingDiagonal(char[][] gameState, char player, int toWin) {
        int numberTokens = 0;

        // Loop to start from different diagonals
        for (int row = toWin - 1; row < gameState.length; row++) {
            for (int col = 0; col <= gameState[0].length - toWin; col++) {
                numberTokens = 0;

                // Check the diagonal starting from (startRow, startCol)
                for (int i = 0; i < toWin; i++) {
                    if (gameState[row - i][col + i] == player) {
                        numberTokens += 1;
                    } else {
                        numberTokens = 0;
                        break;
                    }
                }
                if (numberTokens == toWin) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkWin(char[][] gameState, char player, int toWin) {
        if (checkRow(gameState, player, toWin)) {
            return true;
        } else if (checkColumn(gameState, player, toWin)) {
            return true;
        } else if (checkDescendingDiagonal(gameState, player, toWin)) {
            return true;
        } else if (checkAscendingDiagonal(gameState, player, toWin)){
        return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Connect four game");
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of rows in the board: ");
        int boardSizeRow =  in.nextInt();
        System.out.println("Enter the number of columns in the board: ");
        int boardSizeCol =  in.nextInt();
        System.out.println("Enter the number of tokens to win: ");
        int toWin = in.nextInt();
        char[][] gameState =  new char[boardSizeRow][boardSizeCol];
        displayGame(gameState);
        boolean winner =  false;
        int turn = 1;
        char player =  'R';
        while (winner == false && turn <= boardSizeRow*boardSizeCol){
            boolean validMove;
            int move;
            do{
                System.out.println("Player "+ player + "'s turn: ");
                move = in.nextInt();
                validMove =  validateMove(move, gameState);
                if (validMove == false){
                    System.out.println("Invalid Move please try again");
                }
            } while (validMove == false);
            gameState =  updateBoard(gameState, move, player);
            winner = checkWin(gameState, player, toWin);
            if (winner){
                System.out.println("Player " + player + " wins!");
                displayGame(gameState);
                break;
            }
            turn += 1;
            if (player ==  'R'){
                player = 'Y';
            }else{
                player = 'R';
            }
            displayGame(gameState);
        }
    }
}
