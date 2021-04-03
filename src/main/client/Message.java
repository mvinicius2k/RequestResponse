package main.client;

import main.merged.IMessage;

public class Message implements IMessage{

	private String text;
	
	
	
	/**
	 * @param text
	 */
	public Message(String text) {
		super();
		this.text = text;
	}

	/**
	 * Transforma a mensagem em maiúsculo
	 */
	@Override
	public void toUpper() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Concatenda no final da string o {@link text}
	 */
	@Override
	public void append(String text) {
		
		this.text += "\n----------------------------------------\n";
		this.text += text;
		
	}

	@Override
	public String toString() {
		return "Message [text=" + text + "]";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
}
