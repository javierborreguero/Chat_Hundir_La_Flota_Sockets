package ejercicio2_hundirLaFlota;

import java.net.*;
import java.util.Scanner;
import java.io.*;

class ClienteTCP {
	private Socket socketCliente = null;
	private BufferedReader entrada = null;
	private PrintWriter salida = null;

	public ClienteTCP(String ip, int puerto) {
		try {
			socketCliente = new Socket(ip, puerto);
			System.out.println("Conexi�n establecida: " + socketCliente);
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
		} catch (IOException e) {
			System.err.printf("Imposible conectar con ip:%s / puerto:%d", ip, puerto);
			System.exit(-1);
		}
	}

	public void closeClienteTCP() {
		try {
			salida.close();
			entrada.close();
			socketCliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-> Cliente Terminado");
	}

	public void sendMessage(String linea) {
		salida.println(linea);
	}

	public String receiveMessage() {
		String msg = "";
		try {
			msg = entrada.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}
}

public class Cliente {
	public static void main(String[] args) throws IOException {
		boolean fin = false;
		ClienteTCP myClient = new ClienteTCP("localhost", 55555);
		Board mBoard = new Board();
		System.out.println("\n----- COMIENZA EL JUEGO -----\n");
		String message = "";
		mBoard.placeBoats();
		mBoard.showBoard();
		do {
			// Se usa expresión regular que comprueba que las letras coordenadas estén
			// correctamente.
			do {
				Scanner sc = new Scanner(System.in);
				System.out.print("¡AL ATAQUE!\nEscribe la coordenada, por ejemplo (B4): ");
				message = sc.nextLine();
			} while (!message.matches("^[A-J][0-9]*$"));
			System.out.println("¡HAS REALIZADO EL ATAQUE!");
			myClient.sendMessage(message);
			message = myClient.receiveMessage();
			System.out.println(message);
			if (!message.contains("FIN")) {
				message = myClient.receiveMessage();
				System.out.println("¡EL ENEMIGO HA ATACADO EN LA POSICION " + message);

				if (mBoard.atackBoard(message)) {
					System.out.println("¡HAS PERDIDO!");
					fin = true;
					myClient.sendMessage("FIN");

				}

				else {

					myClient.sendMessage("AGUA");

				}
			} else {
				System.out.println("¡HAS GANADO!");
				fin = true;
			}

		} while (!fin);

	}
}