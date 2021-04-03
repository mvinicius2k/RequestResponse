package main.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import main.merged.Constants;
import main.merged.Message;
import main.merged.MessageRequest;

public class Server {

	
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
		byte[] buffer = new byte[Constants.BUFFER_SIZE];
		DatagramPacket request = new DatagramPacket(
				buffer,
				Constants.BUFFER_SIZE
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
			System.out.println("Mandando resposta para " + lastClientIp + ":" + lastClientPort);
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
			System.out.println("Argumento recebido de " + lastClientIp.toString());
					
			if(message.getRemoteObjectRef().getmInterface().equals(Message.class)) {
				
				
				
				MessageRequest response = new MessageRequest(message.getMethodId(), message.getArgs());
				
				for(Method m : message.getRemoteObjectRef().getmInterface().getDeclaredMethods()) {
					System.out.print(m.getName() + " | id=" + m.hashCode());
					if(m.hashCode() == message.getMethodId() ) {
						System.out.println(" <---- Foi chamado {\n---------------------------------");
						Object[] args = message.getObjectsArgs();
						
						Message instance = new Message();
						m.invoke(instance, args);
						
						System.out.print("\n---------------------------------\n}");
						
						
					}
					System.out.print("\n");
					
					
					
				}
				
				sendReply(response.toBytes());
				
			} else {
				System.out.println(message.getRemoteObjectRef().getmInterface().toString());
			}
			
			
			
			
			
		}
					
		
		
	}
	
	

}
