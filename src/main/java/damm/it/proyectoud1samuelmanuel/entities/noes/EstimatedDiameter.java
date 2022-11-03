package damm.it.proyectoud1samuelmanuel.entities.noes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstimatedDiameter {
	@JsonProperty("feet")
	private Feet feet;

	@JsonProperty("kilometers")
	private Kilometers kilometers;

	@JsonProperty("meters")
	private Meters meters;

	@JsonProperty("miles")
	private Miles miles;

	public Feet getFeet(){
		return feet;
	}

	public Kilometers getKilometers(){
		return kilometers;
	}

	public Meters getMeters(){
		return meters;
	}

	public Miles getMiles(){
		return miles;
	}
}