package ejercicio1_chat;

import java.net.*;
import java.util.Scanner;
import java.io.*;

class ClienteUDP {
	private DatagramSocket socketUDP;
	public ClienteUDP() {
		try {
			socketUDP = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void enviarMsg(String msg, String ip, int puerto) {
		InetAddress hostServidor;
		try {
			hostServidor = InetAddress.getByName(ip);
			byte[] mensaje = msg.getBytes();
			DatagramPacket peticion = new DatagramPacket(mensaje, mensaje.length, hostServidor, puerto);
			socketUDP.send(peticion);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String recibirMsg() {
		DatagramPacket respuesta = null;
		try {
			byte[] bufer = new byte[1000];
			respuesta = new DatagramPacket(bufer, bufer.length);
			socketUDP.receive(respuesta);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(respuesta.getData()).trim();
	}

	public void closeClienteUDP() {
		socketUDP.close();
		System.out.println("-> Cliente Terminado");
	}
}

public class Cliente {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		String linea;
		ClienteUDP canal = new ClienteUDP();
		System.out.println("1) Empieza a escribir:\n2) Para terminar escribe -> Fin");
		do {
			linea = sc.nextLine();
			canal.enviarMsg(linea, "localhost", 5555);
			System.out.println("Servidor: " + canal.recibirMsg());
		} while (!linea.equals("Fin"));
		canal.closeClienteUDP();

	}
}
