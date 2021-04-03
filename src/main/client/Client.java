package main.client;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;

import main.merged.IMessage;
import main.server.Server;

public class Client {

	
	private Message message;
	
	/**
	 * @param message
	 */
	public Client(Message message) {
		super();
		this.message = message;
	}

	
	public void doOperation() throws IOException {
		
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Class<IMessage> obj = (Class<IMessage>) message.getClass();
		
		RemoteObjectRef ref = new RemoteObjectRef(
				ip,
				1234,
				obj.hashCode(),
				0,
				obj);

		int id = -1;
		
		for(Method m : obj.getMethods()) {
			if(m.getName() == "append") {
				id = m.hashCode();
			}
				
						
		}
		
		
		String args = "Texto";
		
		
		
		
		MessageRequest request = new MessageRequest(ref, id, args.getBytes());
		
		DatagramPacket packet = new DatagramPacket(request.toBytes(), 0, request.toBytes().length, ip, 1234);
		
	
		DatagramSocket socket = new DatagramSocket();
		
		socket.send(packet);
		
		byte[] buffer = new byte[Server.bufferSize];
		
		DatagramPacket response = new DatagramPacket(buffer, buffer.length);
		
		socket.receive(response);
		
		byte[] result = response.getData();
		
		System.out.println(MessageRequest.fromBytes(result).toString());
		
		
		socket.close();
		
	}
	
	
	
}
