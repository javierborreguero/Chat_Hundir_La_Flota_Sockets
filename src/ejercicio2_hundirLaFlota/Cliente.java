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
			System.err.printf("Imposible conectar con ip:%s / puerto:%d",ip,puerto);
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

	public void enviarMsg(String linea) {
		salida.println(linea);
	}

	public String recibirMsg() {
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
		Scanner sc = new Scanner(System.in);
		String linea;
		ClienteTCP canal = new ClienteTCP("localhost", 5555);
		System.out.println("Comience a escribir ('Adi�s') para terminar");
		do {
			linea = sc.nextLine();
			canal.enviarMsg(linea);
			System.out.println("Servidor: " + canal.recibirMsg());
		} while (!linea.equals("Adi�s"));
		canal.closeClienteTCP();
	}
}