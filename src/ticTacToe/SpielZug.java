package ticTacToe;

/*
 * Eine Stelle definieren
 */
public class SpielZug {

	int x, y;

	public SpielZug(int x, int y) { // Konstruktor
		this.x = x; // Reihe
		this.y = y; // Zeile
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}