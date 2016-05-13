package ticTacToe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*
 * Klasse TicTacToe, samt der Minimax-Algorithmus.
 */

public class TicTacToe {

	public static char x = 'x';
	List<SpielZug> spielZuege;
	Messages message = new Messages();
	Scanner scan = new Scanner(System.in);
	int[][] brett = new int[3][3];
	boolean ticSpeichern = false;

	public boolean spielEnde() {
		// Spiel ist zu Ende wenn eine der folgenden Möglichkeiten eintreffen...
		return (xGewonnen() || oGewonnen() || moeglicheZuege().isEmpty());
	} // keine Züge mehr vorhanden sind

	SpielZug computerZug;

	public TicTacToe() throws IOException { /// Default Tic-Tac-Toe constructor
		message.welcome(); /// Prints the welcome message
		brett = new int[3][3]; /// Creates a new grid
	}

	/**
	 * Minimax
	 */
	public int computer(int tiefe, int zug) {

		if (xGewonnen())
			return +1; // +1 für max

		if (oGewonnen())
			return -1; // -1 für min
		// noch mögliche Züge definieren
		List<SpielZug> zuegeVerfuegbar = moeglicheZuege();
		// Züge werde in einer Array-List gespeichert

		if (zuegeVerfuegbar.isEmpty()) { /// If zuegeVerguegbar is empty, it
											/// means
											/// there are no possible moves
			message.noMoreMoves();
			return 0;
		}
		int min = Integer.MAX_VALUE; // Minimum
		int max = Integer.MIN_VALUE; // Maximum

		// Schleife läuft alle noch verfügbaren Spielzüge ab
		for (int i = 0; i < zuegeVerfuegbar.size(); ++i) {
			SpielZug zuege = zuegeVerfuegbar.get(i);
			message.analyzingMove(zuege);

			if (zug == 1) {
				spielerZug(zuege, 1);

				message.xMove(zuege);
				message.brettAnzeigen(brett);

				// Spielstand kann -1(schlechte Entscheidung), 0(gute
				// Entscheidung),
				// +1(Gewinn) sein.
				int spielStand = computer(tiefe + 1, 2);

				// gibt den grösseren der Werte zurück
				max = Math.max(spielStand, max);

				/*
				 * Minimax untersucht die noch auf dem Brett verbleibende Züge,
				 * analysiert diese (in einer Schleife). -1 wäre eine Situation
				 * wo der Computer verliert. 0 - wo keine Gefahr droht, 1 -
				 * Gewinnsituation in der Perspektive
				 */
				if (tiefe == 0)
					message.positionAnalyze(i, spielStand);

				if (spielStand >= 0) {
					if (tiefe == 0) {

						computerZug = zuege;
					}
				}

				if (spielStand == 1) {

					brett[zuege.x][zuege.y] = 0;
					message.unDoXMove(zuege);
					message.brettAnzeigen(brett);
					break;
				}
				// System.out.println("Protokollierung "+(i)+" = "+spielStand);
				if (i == zuegeVerfuegbar.size() - 1 && max < 0) {
					if (tiefe == 0)
						computerZug = zuege;
				}
				// System.out.println("Protokollierung 2 "+(i)+" =
				// "+spielStand);
			} else if (zug == 2) { // die unterste Stufe abfragen
				spielerZug(zuege, 2);
				message.analyzeOMove(zuege);
				message.brettAnzeigen(brett);

				int spielStand = computer(tiefe + 1, 1);

				if (spielStand == 1) {
					message.goodMove(zuege);
				}
				if (spielStand == -1) {
					message.badMove(zuege);
				}
				if (spielStand == 0)
					message.neutralMove(zuege);

				min = Math.min(spielStand, min);

				if (min == -1) {
					brett[zuege.x][zuege.y] = 0; //
					message.unDoOMove(zuege);
					message.brettAnzeigen(brett);
					break;
				}
			}

			brett[zuege.x][zuege.y] = 0; // neues Brett
			message.unDoXMove(zuege);
			message.brettAnzeigen(brett);
		}
		if (zug == 1) {
			return max;
		} else {
			return min;
		}

	}



	/*
	 * Diese Methode überprüft ob noch freie Stellen auf dem Feld gibt. Wenn
	 * eine leere Stelle gefunden wurde, dann kann man den nächsten Zug machen
	 */
	public List<SpielZug> moeglicheZuege() {
		spielZuege = new ArrayList<>();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (brett[i][j] == 0) { /// Checks all grid possibilities
					spielZuege.add(new SpielZug(i, j)); /// and return the list
														/// of possible moves
				}
			}
		}
		return spielZuege;
	}

	// Spielerzug
	public void spielerZug(SpielZug point, int player) {
		brett[point.x][point.y] = player; // Spieler = 1 for X, 2 for O
	}

	// Spielerzug
	void eingabeSpieler() { /// Asks the user the desired coordinates
		message.askCoordinates();
		int x = scan.nextInt(); /// Gets x coordinate
		int y = scan.nextInt(); /// Gets y coordinate
		SpielZug zug = new SpielZug(x, y); /// Creates a new move
		spielerZug(zug, 2); /// Set 2 for human move
	}


	// Gueltigkeitsbereich definieren
	public boolean gueltigerZug(SpielZug s) {
		if (brett[s.x][s.y] != 0)
			return false;
		else if (s.x >= 0 || s.x >= 2)
			return true;
		return true;
	}

	// zufaelliger Zug generieren
	private SpielZug zufaelligerZug(Random zufaellig) {
		SpielZug zug = new SpielZug(zufaellig.nextInt(3), zufaellig.nextInt(3));

		while (!gueltigerZug(zug)) {
			zug = new SpielZug(zufaellig.nextInt(3), zufaellig.nextInt(3));
		}
		return zug;
	}

	// aus der Klasse Main
	public void spiele(Random zufaellig, int zugEingabe) {
		// 1 - falls der Computer anfängt, dann wird Zufallsgenerator einer der
		// felder Markieren
		if (zugEingabe == 1) {
			// fall man am Anfang eine 1 eingibt, so wird es der Computer am
			// Zug sein und 2 Random-Zahlen
			// zwischen 0 und 2 generiert
			SpielZug zug = zufaelligerZug(zufaellig);
			spielerZug(zug, 1); // 1 steht für "X", und 2 steht für "O"
			message.brettAnzeigen(brett); // Anzeigen des Spielbretts
		}

		while (!spielEnde()) { // solange das Spiel nicht zu Ende ist...
			SpielZug spielerZug = null;
			spielerZug = gueltigeEingabeSpieler(); /// Asks the desired move
													/// from player
			if (spielerZug == null)
				break;

			spielerZug(spielerZug, 2); /// Perform human(2) move in the desired
										/// grid position
			message.brettAnzeigen(brett); // SpielBrett anzeigen

			// Falls das Ende des Spiels erreicht wurde (durch die
			// Überprüfungsmethode)
			// So wird es abgebrochen
			if (spielEnde())
				break;

			moveComputer(0, 1); ///
			message.brettAnzeigen(brett);
		}
		if (xGewonnen()) /// Check if X has won the game
			message.computerWon();

		else if (oGewonnen()) /// Check if O has won the game
			message.youWon();

		else
			message.itsDraw(); /// If it is the end of the game and nobody won,
								/// then it is a draw

	}

	// Spielereingabe
	public int spielerInput() throws IOException {
		int zugEingabe;
		do {
			if (spielAnanfang()) {
				brett = spielLaden();
				message.brettAnzeigen(brett);
				spiele(null, 2);
				return 0;
			} else {
				zugEingabe = spielerEingabe();
			}
		} while (zugEingabe != 1 && zugEingabe != 2); /// Only accepts the value
														/// 1 or 2
		return zugEingabe;
	}

	// User-Interface
	/**
	 * Checks if the user tried to load a game
	 * 
	 * @return true if the user chose to load
	 * @throws IOException
	 */
	public boolean spielAnanfang() throws IOException {

		while (!scan.hasNext("[LlNn]")) { // wenn keine dider Zeichen eingegeben
											// werden
			message.invalid(); // falsche Eingabe
			scan.next();
		}
		String input = scan.next();

		if (input.equals("n") || input.equals("N")) { // n oder N = neues Spiel
			message.chooseStarterPlayer();

		}
		if (input.equals("l") || input.equals("L")) { // l oder L = spiel laden
			message.loading(); /// shows loading message
			return true;
		}
		return false;

	}

	private SpielZug gueltigeEingabeSpieler() {
		int x, y;
		SpielZug spielerZug;

		do {
			message.showUserTurnMessage(); /// inform user that it is time to
											/// play

			x = xKoordinate(); /// variable x is set with the chosen coordinate
			if (x == 9) { /// if the player has chosen 9, it means he wants to
							/// save game
				ticSpeichern = true; /// this flag variable turns true, meaning
										/// the game need to be saved
				return spielSpeichern(brett);
			}
			y = yKoordinate(); /// variable y is set with the chosen coordinate
			if (y == 9) { /// if the player has chosen 9, it means he wants to
							/// save game
				ticSpeichern = true; /// this flag variable turns true, meaning
										/// the game need to be saved
				return spielSpeichern(brett);
			} else /// if the player chose a valid value to play, so spielerZug
					/// is set as the move
				spielerZug = new SpielZug(x, y);

		} while (!gueltigerZug(spielerZug)); /// checks if the option chose is
												/// valid (if the player didnt
												/// chose invalid coordinate or
												/// an used position on grid)
		return spielerZug;
	}

	// diese Funktion gibt die gewünschte X-Koordinate aus
	public int xKoordinate() {
		int x;
		do {
			// System.out.println("wähle X-Koordinate (Eingabe zwischen 0 und
			// 2)");
			x = spielerEingabe();
			// spielerEingabe() solange x ungleich 9 UND x<0 ODER x>2
		} while (x != 9 && (x < 0 || x > 2));
		return x;
	}

	// diese Funktion gibt die gewnschte Y-Koordinate aus
	public int yKoordinate() {
		int y;
		do {
			// System.out.println("wähle Y-Koordinate (Eingabe zwischen 0 und
			// 2)");
			y = spielerEingabe();
			// => spielerEingabe() solange y ungleich 9 UND y<0 ODER y>2
		} while (y != 9 && (y < 0 || y > 2));
		return y;
	}

	// ueberpruefen ob die Eingabe richtig war um Absturz des Programmes zu
	// verhindern
	public int spielerEingabe() {
		int zugEingabe;
		while (!scan.hasNextInt()) { /// while user's input has an invalid value
			message.invalid();
			scan.next();
		}

		zugEingabe = scan.nextInt();
		return zugEingabe;
	}

	// throws IOException wegen dem Speichenr/Laden
	// diese Methode speichert das Spiel in einer .txt-Datei, die nachher
	// geöffnet werden kann
	public void speichern(int[][] brett) throws IOException {
		// Pfad wo die Datei gespeichert werden soll
		FileWriter schreiben = new FileWriter("C:/Users/user/Desktop/TicTacToe.txt"); // Speicherort
		PrintWriter txt = new PrintWriter(schreiben);

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				txt.println(brett[i][j]); /// add brett data in the external
											/// file
		txt.flush(); // Daten übergeben
		txt.close();
		schreiben.close();
	}

	// diese Methode öffnet die gespeicherte Datei und übergibt derer inhalt
	// an die variable brett
	public int[][] spielLaden() throws IOException {
		// Pfad der gespeicherten Datei
		Scanner sc = new Scanner(new File("C:/Users/user/Desktop/TicTacToe.txt")); // gespeicherte
																					// Datei
		int[][] brett = new int[3][3];
		int[] laenge = new int[9]; /// This variable will be loaded with the
									/// external data
		int z = 0;
		while (sc.hasNextInt()) { /// Read the full txt file and load is on
									/// laenge variable
			laenge[z++] = sc.nextInt();
		}
		int zaehler = 0;
		for (int i = 0; i < 3; i++) { /// This is done to convert data in a 1D
										/// array, into a 2D array
			for (int j = 0; j < 3; j++) {
				brett[i][j] = laenge[zaehler];
				zaehler++;
			}
		}
		return brett;
	}

	// Spiel Speichern
	public SpielZug spielSpeichern(int[][] brett) {

		try {
			speichern(brett); /// try to save brett in the external file
		} catch (IOException e) {
			e.printStackTrace(); /// if we get any erros, prints is in the
									/// console
		}
		return null;
	}

	// prüfen ob der Computer (mitetels Minimax-Algorithmus) gewonnen/verloren
	// hat...
	public boolean xGewonnen() {

		// prüfen ob X-Diagonale gewonnen hat
		if ((brett[0][0] == brett[1][1] && brett[0][0] == brett[2][2] && brett[0][0] == 1)
				|| (brett[0][2] == brett[1][1] && brett[0][2] == brett[2][0] && brett[0][2] == 1)) {

			// System.out.println("X-Diagonale hat gewonnen.");
			return true;
		}
		// prüfen ob X-Zeile oder Spalte gewonnen hat
		for (int i = 0; i < 3; ++i) {
			if (((brett[i][0] == brett[i][1] && brett[i][0] == brett[i][2] && brett[i][0] == 1)
					|| (brett[0][i] == brett[1][i] && brett[0][i] == brett[2][i] && brett[0][i] == 1))) {
				return true;
			}
		}
		return false;
	}

	// prüfen ob man gewonnen/verloren hat...
	public boolean oGewonnen() {

		// prüfen ob O-Diagonale gewonnen hat
		if ((brett[0][0] == brett[1][1] && brett[0][0] == brett[2][2] && brett[0][0] == 2)
				|| (brett[0][2] == brett[1][1] && brett[0][2] == brett[2][0] && brett[0][2] == 2)) {

			return true;
		}
		// prüfen ob O-Zeile oder Spalte gewonnen hat
		for (int i = 0; i < 3; ++i) {
			if ((brett[i][0] == brett[i][1] && brett[i][0] == brett[i][2] && brett[i][0] == 2)
					|| (brett[0][i] == brett[1][i] && brett[0][i] == brett[2][i] && brett[0][i] == 2)) {

				return true;
			}
		}

		return false;
	}

	/**
	 * Brett setter.
	 * 
	 * @param brett
	 */
	public void setBrett(int[][] brett) {
		this.brett = brett;

	}

	/**
	 * Gets brett
	 * 
	 * @return brett
	 */
	public int[][] getBrett() {
		return brett;
	}

	/**
	 * Manages computer moves
	 * 
	 * @param tiefe
	 * @param zug
	 */
	public void moveComputer(int tiefe, int zug) {
		computer(tiefe, zug);
		spielerZug(computerZug, 1);
	}

}// Ende der Klasse TicTacToe