package main.client;

import java.io.IOException;

import main.merged.Constants;
import main.merged.Message;


public class ClientExec {
	
	private static Message message;
	private static Client client;

	public static void main(String[] args) {
		
		
		
		try {
			if(args.length > 1)
				createClient(args);
			else {
				System.out.println("Nenhum m�todo digitado, usando padr�o 'upper Escrevendo mensagem no cliente'");
				String port;
				if(args.length == 0) {
					System.out.println("Nenhuma porta digitada, usando padr�o " + Constants.SERVER_PORT);;
					port = String.valueOf(Constants.SERVER_PORT);
				} else 
					port = args[0];
				
				main(new String[] {
						port,
						"upper",
						"Escrevendo mensagem no cliente"
				});
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println("M�todo "  + args[0] + " n�o existe");
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		
		

	}
	
	private static void createClient(String[] args) throws NoSuchMethodException, IOException {
		message = new Message();
		
		client = new Client(message, Integer.valueOf(args[0]));
		
		String[] arguments = new String[args.length-2];
		
		//if(args.length == 0)
			
		
		for(int i = 0; i < arguments.length; i++) 
			arguments[i] = args[i+2];
		
				
		client.doOperation(args[1],  arguments);
	}

}
