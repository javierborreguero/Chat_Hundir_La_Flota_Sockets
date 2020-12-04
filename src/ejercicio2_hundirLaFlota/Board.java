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
	private int[][] serverBoard = new int[BOARD_SIZE][BOARD_SIZE];
	private int[] boats = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	private void placeBoats(int[][] board) {
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
						board[fila][columna] = b;
						colocado = true;
					}
				}
			}

		}

		this.serverBoard = board;

	}

	public void showBoard() {
		for (int i = 0; i < serverBoard.length; i++) {
			for (int j = 0; j < serverBoard[i].length; j++) {
				System.out.println(serverBoard[i][j]);

			}

		}

	}

	public void atackBoard() {
	
	}
}
