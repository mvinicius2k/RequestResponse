package main.merged;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;


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
	 * @throws IOException 
	 */
	public MessageRequest(RemoteObjectRef remoteObjectRef, int methodId, Object[] args) throws IOException {
		super();
		
		this.remoteObjectRef = remoteObjectRef;
		this.methodId = methodId;
		this.args = toByteArray(args);
		
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
		return (MessageRequest) bytesToObject(bytes);
	}
	
	private static Object bytesToObject(byte[] bytes) {
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
			return obj;
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
	
	/**
	 * 
	 * @return Argumentos convertidos em array de objeto
	 */
	public Object[] getObjectsArgs() {
		return (Object[]) bytesToObject(args);
	}

	public void setArgs(byte[] args) {
		this.args = args;
	}
	
	public void setArgs(String[] args) throws IOException {
		this.args = toByteArray(args);
	}
	
	private byte[] toByteArray(Object[] obj) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		byte[] outputBytes = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(obj);
		  out.flush();
		  outputBytes = bos.toByteArray();
		  
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
		  try {
		    bos.close();
		  } catch (IOException ex) {
			  System.out.println(ex.getMessage());
		  }
		}
		
		return outputBytes;
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
