package damm.it.proyectoud1samuelmanuel.entities.noes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Links {
	@JsonProperty("next")
	private String next;

	@JsonProperty("prev")
	private String prev;

	@JsonProperty("self")
	private String self;

	public String getNext(){
		return next;
	}

	public String getPrev(){
		return prev;
	}

	public String getSelf(){
		return self;
	}
}