package ejercicio2_hundirLaFlota;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Board {
	private ServerSocket server;
	private Socket client;
	private final int PORT = 0;
	private BufferedReader input;
	private PrintWriter output;
	private final int BOARD_SIZE = 10;
	private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
	private int[] boats = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	public void placeBoats() {
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = 0;
			}
		}

		for (int b : boats) {
			boolean colocado = false;
			while (!colocado) {
				int fila = r.nextInt(10);
				int columna = r.nextInt(10);
				if (fila + b < BOARD_SIZE) {
					if (board[fila][columna] == 0) {
						board[fila][columna] = 1;
						colocado = true;
					}
				}
			}

		}

	}

	public void showBoard() {
		char[] cordY = new char[BOARD_SIZE];
		System.out.print("  ");
		for (int cordX = 0; cordX < BOARD_SIZE; cordX++) {
			System.out.print(cordX + " ");
		}
		System.out.println("");
		for (int i = 0; i < board.length; i++) {
			cordY[i] = (char) ('A' + i);
			System.out.print(cordY[i]);
			for (int j = 0; j < board.length; j++) {
				System.out.print(" " + board[i][j]);
			}
			System.out.println("");
		}
	}

	public boolean atackBoard(String message) {
		int cordY = message.charAt(0) - 'A';
		int cordX = message.charAt(message.length() - 1) - '0';
		if (board[cordY][cordX] == 0) {
			return false;

		}
		return true;

	}
}
