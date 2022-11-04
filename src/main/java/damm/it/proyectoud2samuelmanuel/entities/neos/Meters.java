package damm.it.proyectoud2samuelmanuel.entities.neos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meters {
	@JsonProperty("estimated_diameter_max")
	private double estimatedDiameterMax;

	@JsonProperty("estimated_diameter_min")
	private double estimatedDiameterMin;

	public double getEstimatedDiameterMax(){
		return estimatedDiameterMax;
	}

	public double getEstimatedDiameterMin(){
		return estimatedDiameterMin;
	}
}