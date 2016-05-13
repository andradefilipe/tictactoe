package ticTacToe;

/**
 * This class aims to manage all different types of messages
 * 
 * @author
 *
 */
public class Messages {

	/**
	 * Welcome message
	 */
	void welcome() {
		System.out.println("N - neues Spiel \nL - Spiel Laden");
	}

	/**
	 * Choose who starts the game
	 */
	void chooseStarterPlayer() {
		System.out.println("\nWer beginnt?");
		System.out.println("1 - Computer");
		System.out.println("2 - Human");
	}

	/**
	 * Prints loading message
	 */
	void loading() {
		System.out.println("\nSpiel laden...\n");
	}

	/**
	 * Printed when the user tries an invalid move
	 */
	void invalid() {
		System.out.println("falsche Eingabe...");
	}

	/**
	 * die Methode die Das Brett auf der Konsole ausgibt
	 */
	public void brettAnzeigen(int[][] brett) {

		for (int i = 0; i < 3; ++i) { /// Line print loop
			for (int j = 0; j < 3; ++j) { /// Columns print loop
				if (brett[i][j] == 1) { /// if the current position was chosen
										/// by X
					if (j != 2) /// Can NOT print X with a bar when it is the
								/// last position
						System.out.print("X" + " | ");
					if (j == 2)
						System.out.print("X" + "  ");
				} else if (brett[i][j] == 2) { /// if the current position was
												/// chosen by O
					if (j != 2) /// Can NOT print O with a bar when it is the
								/// last position
						System.out.print("O" + " | ");
					if (j == 2)
						System.out.print("O" + "  ");
				} else {
					if (j != 2)
						System.out.print(" " + " | ");
				}
			}
			if (i != 2) /// Used to print the lines
				System.out.println("\n----------");

		}
		System.out.print("\n");
	}

	/**
	 * This method is used to inform the user his turn to play
	 */
	public void showUserTurnMessage() {
		System.out.println(" ");
		System.out.println("dein Zug...           (9 drücken um zu speichern)");
		System.out.println("");
	}

	/**
	 * Prints draw result
	 */
	void itsDraw() {
		System.out.println("Unentschieden!");
	}

	/**
	 * Prints the game is saved
	 */
	void gameSaved() {
		System.out.println("\n\nGame saved.");
	}

	/**
	 * Prints human's victory
	 */
	void youWon() {
		System.out.println("Du hast gewonnen!");
	}

	/**
	 * Prints computer's victory
	 */
	void computerWon() {
		System.out.println("Computer hat gewonnen!");
	}

	void noMoreMoves() {
		System.out.println("Minimax: No more possible moves...");
	}

	void analyzingMove(SpielZug zuege) {
		System.out.println("Minimax: Analyzing brett position (x,y) = " + zuege.toString());
	}

	void xMove(SpielZug zuege) {
		System.out.println("Minimax: if X plays: " + zuege.toString());
	}

	void positionAnalyze(int i, int spielStand) {
		System.out.println("Positionsanalyse " + (i) + " = " + spielStand); // ostawit
	}

	void unDoXMove(SpielZug zuege) {
		System.out.println("Minimax: Undoing move " + zuege.toString() + " for X");
	}

	void unDoOMove(SpielZug zuege) {
		System.out.println("Minimax: Undoing move " + zuege.toString() + " for O");
	}

	void goodMove(SpielZug zuege) {
		System.out.println("Minimax: Good place to play! +1 " + zuege.toString());
	}

	void badMove(SpielZug zuege) {
		System.out.println("Minimax: Bad place to play! -1 " + zuege.toString());
	}

	void analyzeOMove(SpielZug zuege) {
		System.out.println("Minimax: if O plays: " + zuege.toString());
	}

	void neutralMove(SpielZug zuege) {
		System.out.println("Minimax: Neutral! 0" + zuege.toString());
	}

	void askCoordinates() {
		System.out.println("dein Zug: ");
	}

}
