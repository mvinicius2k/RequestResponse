package main.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import main.client.Message;
import main.client.MessageRequest;
import main.client.RemoteObjectRef;
import main.merged.IMessage;

public class Server {

	public static final int bufferSize = 1024;
	private InetAddress lastClientIp;
	private int lastClientPort;
	private DatagramSocket socket;
	
	public Server(int serverPort) throws SocketException {
		this.socket = new DatagramSocket(serverPort);
		
		
	}
	
	/**
	 * Verifica se alguma requisição chegou 
	 * @return null se não receber uma requisição
	 */
	public byte[] getRequisicao() {
		byte[] buffer = new byte[bufferSize];
		DatagramPacket request = new DatagramPacket(
				buffer,
				bufferSize
			);
		
		try {
			this.socket.receive(request);
			this.lastClientIp = request.getAddress();
			this.lastClientPort = request.getPort();
			return request.getData();
		} catch (IOException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
			return null;
		}
				
	}
	
	/**
	 * Tenta enviar uma respota à última requisição
	 * @param resposta
	 */
	public void sendReply(byte[] respostaBytes) {
		DatagramPacket resposta = new DatagramPacket(
				respostaBytes,
				0,
				respostaBytes.length,
				lastClientIp,
				lastClientPort);
		try {
			
			socket.send(resposta);
		} catch (IOException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
		
	
		
	
	}
	
	/**
	 * Ficará escutando requisições num tempo definido em milisegundos
	 * @param delay
	 * @throws InterruptedException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public void listener(long delay) throws InterruptedException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Escutando na porta 1234");
		while(true) {
			Thread.sleep(delay);
			
			byte[] requisicao = getRequisicao();
			if(requisicao == null)
				continue;
			
			MessageRequest message = MessageRequest.fromBytes(requisicao);
			
			
			//String messageText = new String(message.getArgs());
			//System.out.println("Argumento recebido de " + lastClientIp.toString() + " -> " + messageText);
					
			if(message.getRemoteObjectRef().getmInterface().equals(Message.class)) {
				
				
				
				MessageRequest request = new MessageRequest(message.getMethodId(), message.getArgs());
				
				for(Method m : message.getRemoteObjectRef().getmInterface().getMethods()) {
					System.out.print(m.getName() + " | " + m.hashCode());
					if(m.hashCode() == message.getMethodId() ) {
						System.out.print(" <---- Foi chamado");
						
					
						
					}
					System.out.print("\n");
					
					
					
				}
				
				sendReply(request.toBytes());
				
			} else {
				System.out.println(message.getRemoteObjectRef().getmInterface().toString());
			}
			
			
			
			
			
		}
					
		
		
	}
	
	

}
