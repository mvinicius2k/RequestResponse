package main.client;

import java.io.Serializable;
import java.net.InetAddress;

import main.merged.IMessage;

public class RemoteObjectRef implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7191446966524923708L;


	private InetAddress ip;
	
	
	private int port, objectId;
	private long time;
	

	private Class<IMessage> mInterface;
	
	
	
	
	/**
	 * @param ip
	 * @param port
	 * @param objectId
	 * @param time
	 * @param mInterface
	 */
	public RemoteObjectRef(InetAddress ip, int port, int objectId, long time, Class<IMessage> mInterface) {
		super();
		this.ip = ip;
		this.port = port;
		this.objectId = objectId;
		this.time = time;
		this.mInterface = mInterface;
	}

	public int getPort() {
		return port;
	}

	public InetAddress getIp() {
		return ip;
	}
	

	@Override
	public String toString() {
		return "RemoteObjectRef [ip=" + ip + ", port=" + port + ", objectId=" + objectId + ", time=" + time
				+ ", mInterface=" + mInterface + "]";
	}

	public Class getmInterface() {
		return mInterface;
	}

	public void setmInterface(Class mInterface) {
		this.mInterface = mInterface;
	}
	
	
	
	
	
	
}
