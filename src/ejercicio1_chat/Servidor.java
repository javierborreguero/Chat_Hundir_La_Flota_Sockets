package ejercicio1_chat;

import java.net.*;
import java.util.Scanner;
import java.io.*;

class ServidorUDP {
	private DatagramSocket socketUDP;
	private DatagramPacket recibido;

	public ServidorUDP(int puerto) {
		try {
			this.socketUDP = new DatagramSocket(puerto);
			this.recibido = null;
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public String recibirMsg() {
		try {
			byte[] buffer = new byte[1000];
			recibido = new DatagramPacket(buffer, buffer.length);
			socketUDP.receive(recibido);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(recibido.getData()).trim();
	}

	public void enviarMsg(String msg) {

		try {
			DatagramPacket respuesta = new DatagramPacket(msg.getBytes(), msg.length(), recibido.getAddress(),
					recibido.getPort());
			socketUDP.send(respuesta);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeServidorUDP() {
		socketUDP.close();
		System.out.println("-> Servidor Terminado");
	}

}

public class Servidor {
	public static void main(String[] args) {
		int puerto = 5555;
		Scanner sc = new Scanner(System.in);
		System.out.println("Servidor escucha por el puerto " + puerto
				+ "\n1 - INICIA EL RUN PRIMERO DESDE SERVIDOR Y LUEGO EL CLIENTE.\n2 - Empieza a escribir desde Cliente:\n3 - Para terminar escribe -> Fin");
		ServidorUDP canal = new ServidorUDP(puerto);
		String linea;
		do {
			linea = canal.recibirMsg();
			System.out.println("Cliente: " + linea);
			if (!linea.equals("Fin")) {
				linea = sc.nextLine();
				canal.enviarMsg(linea);
			}

		} while (!linea.equals("Fin"));
		canal.closeServidorUDP();
	}
}
