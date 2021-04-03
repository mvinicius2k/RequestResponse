package main.client;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import main.merged.Constants;
import main.merged.Message;
import main.merged.MessageRequest;
import main.merged.RemoteObjectRef;

public class Client {

	
	private int serverPort;
	
	private Message message;
	
	
	
	/**
	 * @param message
	 */
	public Client(Message message, int serverPort) {
		super();
		this.message = message;
		this.serverPort = serverPort;
	}
	
	private Method getMethod(String methodName, Method[] methods) {
		for(Method m : methods) {
			if(m.getName().equals(methodName)) {
				return m;
				
			}	
						
		}
		
		return null;
	}

	/**
	 * Enviar a mensagem e recebe uma resposta
	 * @throws IOException
	 * @throws NoSuchMethodException 
	 */
	public void doOperation(String methodName, String[] args) throws IOException, NoSuchMethodException {
		System.out.println("Mandando  requisição para localhost:" + serverPort);
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Objeto remoto
		Class<?> obj = null;
		obj = message.getClass();
			
		
		
		
		
		
		RemoteObjectRef ref = new RemoteObjectRef(
				ip,
				this.serverPort,
				obj.hashCode(),
				0,
				obj);

		
		
		
		
		
		Method m = getMethod(methodName, obj.getMethods());
		
		if(m == null)
			throw new NoSuchMethodException();
		
		int id = m.hashCode();
		
		
		
		MessageRequest request = new MessageRequest(ref, id, args);
		
		DatagramPacket packet = new DatagramPacket(request.toBytes(), 0, request.toBytes().length, ip, serverPort);
		
	
		DatagramSocket socket = new DatagramSocket();
		
		socket.send(packet);
		
		byte[] buffer = new byte[Constants.BUFFER_SIZE];
		
		DatagramPacket response = new DatagramPacket(buffer, buffer.length);
		
		socket.receive(response);
		System.out.println("Resposta recebida\n");
		
		byte[] result = response.getData();
		
		System.out.println(MessageRequest.fromBytes(result).toString());
		
		
		socket.close();
		
	}
	
	
	
}
