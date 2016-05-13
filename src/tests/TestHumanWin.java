package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import ticTacToe.Messages;
import ticTacToe.SpielZug;
import ticTacToe.TicTacToe;

public class TestHumanWin {

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
		SpielZug spielerZug = new SpielZug(2, 0);
		ticTacToe.setBrett(matrix); /// Game is loaded with pre-set matrix
		ticTacToe.spielerZug(spielerZug, 2); /// Human performs next move

		assertTrue(ticTacToe.spielEnde()); /// Assert that the game is ended
		assertFalse(ticTacToe.xGewonnen()); /// Assert that X didnt win
		assertTrue(ticTacToe.oGewonnen()); /// Assert that O won

	}

}
