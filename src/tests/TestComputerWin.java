package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import ticTacToe.Messages;
import ticTacToe.TicTacToe;

public class TestComputerWin {

	private TicTacToe ticTacToe;
	int[][] matrix = { { 2, 0, 1 }, { 0, 1, 1 }, { 0, 2, 2 } }; /// Creates a
																/// loaded
																/// scenario for
																/// tests

	@Before
	public void setUp() {
		try {
			ticTacToe = new TicTacToe();
		} catch (IOException e) {
			fail("Exception should not be throwed: " + e.getMessage());
		}
	}

	@Test
	public void test() throws IOException {

		ticTacToe.setBrett(matrix); /// Game is loaded with pre-set matrix
		ticTacToe.moveComputer(0, 1); /// Computer performs next move

		assertTrue(ticTacToe.spielEnde()); /// Assert that the game is ended
		assertTrue(ticTacToe.xGewonnen()); /// Assert that x won
		assertFalse(ticTacToe.oGewonnen()); /// Assert that O didnt win

	}

}
