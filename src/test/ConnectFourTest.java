package test;
import com.connectfour.ConnectFour;
import org.junit.Test;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConnectFourTest {
    @Test
    public void testValidMove(){
        ConnectFour  game =  new ConnectFour();
        char[][] gameState = {{' ', 'R', ' '}
                    ,{'R', 'Y', 'R'} ,
                    {'R', 'R', 'Y'}};
        assertTrue(game.validateMove(0, gameState));
        assertFalse(game.validateMove(4, gameState));
        assertFalse(game.validateMove(1, gameState));
    }

    @Test
    public void testWinRow(){
        ConnectFour  game =  new ConnectFour();
        char[][] gameState = {{'Y', ' ', ' '}
                ,{'Y', ' ', ' '} ,
                {'R', 'R', 'R'}};
        assertTrue(game.checkRow(gameState, 'R', 3));
    }
    @Test
    public void testNoWinRow(){
        ConnectFour  game =  new ConnectFour();
        char[][] gameState = {{'Y', ' ', ' '}
                ,{'Y', ' ', ' '} ,
                {'R', 'Y', 'Y'}};
        assertFalse(game.checkRow(gameState, 'R', 3));
    }

    @Test
    public void  testWinCol(){
        ConnectFour  game =  new ConnectFour();
        char[][] gameState = {{'Y', ' ', ' '}
                ,{'Y', ' ', ' '} ,
                {'Y', 'R', 'R'}};
        assertTrue(game.checkColumn(gameState, 'Y', 3));
    }
}