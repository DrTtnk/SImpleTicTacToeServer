package com.company;

public class TicTacToeService {
    private static TicTacToeGame ticTacToeGame;

    private TicTacToeService() { }

    public static TicTacToeGame getGame() {
        if (ticTacToeGame == null) {
            ticTacToeGame = new TicTacToeGame();
        }
        return ticTacToeGame;
    }
}
