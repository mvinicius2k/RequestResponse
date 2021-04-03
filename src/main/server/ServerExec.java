package main.server;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;

import main.merged.Constants;


public class ServerExec {

	private static Server server = null;
	
	public static void main(String[] args) {
		
		
		
		try {
			createServer(Integer.valueOf(args[0]));
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Usando porta predefinida -> " + Constants.SERVER_PORT);
			main(new String[] {
					String.valueOf(Constants.SERVER_PORT)
				});
		} catch (IllegalAccessException e) {
			System.out.println("Digite uma porta como inteiro");
			
		} 
		
		
		
		

	}
	
	private static void createServer(int port) throws SocketException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
		server = new Server(port);
		server.listener(500);
	}

}
