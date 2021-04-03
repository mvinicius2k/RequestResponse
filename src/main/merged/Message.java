package main.merged;

public class Message implements IMessage{

	
	
	
	
	
	public Message() {
		super();
	}

	/**
	 * Imprime text todo em maiusculo
	 */
	@Override
	public void upper(String text) {
		if(text != null)
			System.out.println(text.toUpperCase());
		else
			System.out.println("Null");
		
	}

	/**
	 * Imprime text todo em minusculo
	 */
	@Override
	public void lower(String text) {
		if(text != null)
			System.out.println(text.toLowerCase());
		else
			System.out.println("Null");
		
	}


	

	
	
	
	
	
}
