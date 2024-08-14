package com.connectfour;

import java.util.Scanner;

public class ConnectFour {
    /**
     * Displays the current state of the game
     * @param gameState - the game board
     */
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

    /**
     * Adds the players token to the bootom of the selected column
     * @param gameState - the game board
     * @param move - the selected column
     * @param player - the current player 'R' Red or 'Y' Yellow
     * @return - the updated game board
     */
    public static char[][] updateBoard(char[][] gameState, int move, char player) {
        for (int row = gameState.length-1; row >= 0; row--){
            if(gameState[row][move] != 'R' && gameState[row][move] != 'Y'){
                gameState[row][move] = player;
                break;
            }
        }
        return gameState;
    }

    /**
     * Check if the move the player wants to do is valid
     * @param inputMove - the selected column
     * @param gameState - the board
     * @return - true if valid or false
     */
    public static boolean validateMove(String inputMove, char[][] gameState){
        try{
            int move =  Integer.parseInt(inputMove);
            if (move < 0 || move > gameState[0].length-1){
                return false;
            }
            return gameState[0][move] != 'R' && gameState[0][move] != 'Y';
        }catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Check if there is a winning row
     * @param gameState
     * @param player - R or Y
     * @param toWin - number of tokens in a row needed to win
     * @return - true if a win or false
     */
    public static boolean checkRow(char[][] gameState, char player, int toWin){
        int numberTokens;
        for(int row = 0; row<gameState.length; row++){
            numberTokens = 0;
            for (int col = 0;col < gameState[0].length;col++){
                if (gameState[row][col] != player){
                    numberTokens = 0;
                }else{
                    numberTokens +=1;
                    if (numberTokens == toWin) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if there is a winning column
     * @param gameState
     * @param player - R or Y
     * @param toWin - number of tokens in a column needed to win
     * @return - true if a win or false
     */
    public static boolean checkColumn(char[][] gameState, char player, int toWin){
        int numberTokens;
        for (int col = 0; col < gameState[0].length; col++) {
            numberTokens = 0;
            for (int row = 0; row < gameState.length; row++) {
                if (gameState[row][col] == player) {
                    numberTokens += 1;
                    if (numberTokens >= toWin) {
                        return true;
                    }
                } else {
                    numberTokens = 0;
                }
            }
        }
        return  false;
    }

    /**
     * Check if there is a win in a descending diagonal
     * @param gameState
     * @param player - R or Y
     * @param toWin - number of tokens in a diagonal needed to win
     * @return - true if a win or false
     */
    public static  boolean checkDescendingDiagonal(char[][] gameState, char player, int toWin){
        int numberTokens;
        for (int row = 0; row <= gameState.length - toWin; row++) {
            for (int col = 0; col <= gameState[0].length - toWin; col++) {
                numberTokens = 0;
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

    /**
     * Check if there is a win in the Ascending diagonal
     * @param gameState
     * @param player - R or Y
     * @param toWin - number of tokens in the diagonal needed to win
     * @return - true if a win or false
     */
    public static boolean checkAscendingDiagonal(char[][] gameState, char player, int toWin) {
        int numberTokens;
        for (int row = toWin - 1; row < gameState.length; row++) {
            for (int col = 0; col <= gameState[0].length - toWin; col++) {
                numberTokens = 0;
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

    /**
     * Checks if there is a winning state by calling each of the check functions (split up for testing and readability)
     * @param gameState - game board
     * @param player - R or Y
     * @param toWin - number of tokens to win
     * @return
     */
    public static boolean checkWin(char[][] gameState, char player, int toWin) {
        if (checkRow(gameState, player, toWin)) {
            return true;
        } else if (checkColumn(gameState, player, toWin)) {
            return true;
        } else if (checkDescendingDiagonal(gameState, player, toWin)) {
            return true;
        } else return checkAscendingDiagonal(gameState, player, toWin);
    }

    /**
     * main function which runs the game
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Connect four game");
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of rows in the board: ");
        int boardSizeRow =  in.nextInt();
        System.out.println("Enter the number of columns in the board: ");
        int boardSizeCol = in.nextInt();
        int toWin = 0;
        // make sure the number of tokens to win is possible
        while (toWin == 0){
            System.out.println("Enter the number of tokens to win: ");
            toWin = in.nextInt();
            if (toWin > boardSizeRow && toWin > boardSizeCol) {
                toWin = 0;
                System.out.println("Invalid number of tokens. Must fit in the board.");
            }
        }
        char[][] gameState =  new char[boardSizeRow][boardSizeCol];
        displayGame(gameState);
        boolean winner =  false;
        int turn = 1;
        char player =  'R';
        while (!winner && turn <= boardSizeRow*boardSizeCol){
            boolean validMove;
            int move = 0;
            do{
                System.out.println("Player "+ player + "'s turn: ");
                String inputMove = in.next();
                validMove =  validateMove(inputMove, gameState);
                if (!validMove) {
                    System.out.println("Invalid Move please try again");
                }else{
                    move =  Integer.parseInt(inputMove);
                }
            } while (!validMove);
            updateBoard(gameState, move, player);
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
