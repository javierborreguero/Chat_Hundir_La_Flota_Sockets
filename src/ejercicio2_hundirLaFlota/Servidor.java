package ejercicio2_hundirLaFlota;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class ServidorTCP {
	private Socket socketCliente;
	private ServerSocket socketServidor;
	private BufferedReader entrada;
	private PrintWriter salida;

	public ServidorTCP(int puerto) {
		this.socketCliente = null;
		this.socketServidor = null;
		this.entrada = null;
		this.salida = null;
		try {
			socketServidor = new ServerSocket(puerto);
			System.out.println("Esperando conexi�n...");
			socketCliente = socketServidor.accept();
			System.out.println("Conexi�n acceptada: " + socketCliente);
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
		} catch (IOException e) {
			System.out.println("No puede escuchar en el puerto: " + puerto);
			System.exit(-1);
		}
	}

	public void closeServidorTCP() {
		try {
			salida.close();
			entrada.close();
			socketCliente.close();
			socketServidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-> Servidor Terminado");
	}

	public String receiveMessage() {
		String linea = "";
		try {
			linea = entrada.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return linea;
	}

	public void sendMessage(String linea) {
		salida.println(linea);
	}

}

public class Servidor {
	public static void main(String[] args) throws IOException {
		boolean fin = false;
		Scanner sc = new Scanner(System.in);
		ServidorTCP myServer = new ServidorTCP(55555);
		Board mBoard = new Board();
		System.out.println("\n---- COMIENA EL JUEGO -----\n");
		mBoard.placeBoats();
		mBoard.showBoard();
		do {
			String message = myServer.receiveMessage();
			System.out.println("¡EL ENEMIGO HA ATACADO EN LA POSICION " + message);
			if (mBoard.atackBoard(message)) {
				System.out.println("¡HAS PERDIDO!");
				fin = true;
				myServer.sendMessage("FIN");
			}

			else {

				myServer.sendMessage("AGUA");

				do {
					System.out.print("¡AL ATAQUE!\nEscribe la coordenada, por ejemplo -> (B4): ");
					message = sc.nextLine();
				} while (!message.matches("^[A-J][0-9]*$"));
				System.out.println("¡HAS REALIZADO EL ATAQUE!");
				myServer.sendMessage(message);
				message = myServer.receiveMessage();
				System.out.println(message);
				if (message.contains("FIN")) {
					System.out.println("¡HAS GANADO!");
					fin = true;
				}
			}

		} while (!fin);

	}
}