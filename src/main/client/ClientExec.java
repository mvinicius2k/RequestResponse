package main.client;

import java.io.IOException;

public class ClientExec {

	public static void main(String[] args) {
		
		Message message = new Message("1 Mensagem");
		
		Client client = new Client(message);
		
		try {
			client.doOperation();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
