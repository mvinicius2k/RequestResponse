package main.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import main.merged.IMessage;

public class MessageRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1323580670025815215L;
	
	public static final int REQUEST = 0;
	public static final int REPLY = 1;
	
	private static long next = 0;
	private int type;
	private long requestId;
	private RemoteObjectRef remoteObjectRef;
	private int methodId;
	private byte args[];
	
	
	
	
	/**
	 * Requisitar
	 * 
	 * @param remoteObjectRef
	 * @param methodId
	 * @param args
	 */
	public MessageRequest(RemoteObjectRef remoteObjectRef, int methodId, byte[] args) {
		super();
		
		this.remoteObjectRef = remoteObjectRef;
		this.methodId = methodId;
		this.args = args;
		
		this.type = REQUEST;
		this.requestId = next++;
	}


	/**
	 * Responder
	 * 
	 * @param methodId
	 * @param args
	 */
	public MessageRequest(int methodId, byte[] args) {
		super();
		this.methodId = methodId;
		this.args = args;
		
		this.type = REPLY;
	}
	
	/**
	 * Converte o objeto atual em um array de bytes. Retorna nulo se
	 * a operação falhar.
	 */
	public byte[] toBytes() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		byte[] byteArray;
		try {
			out = new ObjectOutputStream(bytes);
			out.writeObject(this);
			out.flush();
			byteArray =  bytes.toByteArray();
			bytes.close();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());;
			e.printStackTrace();
			return null;
		}
		
		
		
		return byteArray;
	}
	
	/**
	 * Converte um array de bytes num objeto {@link RequisicaoMensagem}.
	 * Retorna null se houver falha.
	 */
	public static MessageRequest fromBytes(byte[] bytes) {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput input = null;
		Object obj;
		try {
			input = new ObjectInputStream(bis);
			obj = input.readObject();
		} catch (Exception e) {
			System.err.println(e.getMessage());;
			e.printStackTrace();
			return null;
		}
		
		try {
			if(input != null)
				input.close();
			return (MessageRequest) obj;
		} catch (Exception e) {
			System.err.println(e.getMessage());;
			e.printStackTrace();
			return null;
		}
	}
	
	

	public int getMethodId() {
		return methodId;
	}

	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}

	public byte[] getArgs() {
		return args;
	}

	public void setArgs(byte[] args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return "MessageRequest [type=" + type + ", requestId=" + requestId + ", remoteObjectRef=" + remoteObjectRef
				+ ", methodId=" + methodId + ", args=" + Arrays.toString(args) + "]";
	
	}

	public RemoteObjectRef getRemoteObjectRef() {
		return remoteObjectRef;
	}

	public void setRemoteObjectRef(RemoteObjectRef remoteObjectRef) {
		this.remoteObjectRef = remoteObjectRef;
	}
	
	
	
	

	
	
	
	
	
	
}
