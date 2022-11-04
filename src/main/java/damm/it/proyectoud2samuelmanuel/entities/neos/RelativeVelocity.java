package damm.it.proyectoud2samuelmanuel.entities.neos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelativeVelocity {
	@JsonProperty("kilometers_per_hour")
	private String kilometersPerHour;

	@JsonProperty("kilometers_per_second")
	private String kilometersPerSecond;

	@JsonProperty("miles_per_hour")
	private String milesPerHour;

	public String getKilometersPerHour(){
		return kilometersPerHour;
	}

	public String getKilometersPerSecond(){
		return kilometersPerSecond;
	}

	public String getMilesPerHour(){
		return milesPerHour;
	}
}