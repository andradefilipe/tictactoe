package ticTacToe;


import java.io.IOException;
import java.util.Random;

public class Main {                        //Exception wegen der spielerInput()
	public static void main(String[] args) throws IOException  {
		 TicTacToe tic = new TicTacToe();				//Objekt der klasse Tictactoe
	     Random zufaellig = new Random();		//Zufallsgenerator, f�r den ersten Zug des Computers
	     int zugEingabe;
	     
	     zugEingabe = tic.spielerInput();   //Anfang des Spiels
			if (zugEingabe != 0)			// falls das Spiel nicht geladen werden muss...
				tic.spiele(zufaellig, zugEingabe); //Methode spiele() ausf�hren
	    
 }
}