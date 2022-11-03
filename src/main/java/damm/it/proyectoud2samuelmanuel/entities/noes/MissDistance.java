package damm.it.proyectoud2samuelmanuel.entities.noes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MissDistance {
	@JsonProperty("astronomical")
	private String astronomical;

	@JsonProperty("kilometers")
	private String kilometers;

	@JsonProperty("lunar")
	private String lunar;

	@JsonProperty("miles")
	private String miles;

	public String getAstronomical(){
		return astronomical;
	}

	public String getKilometers(){
		return kilometers;
	}

	public String getLunar(){
		return lunar;
	}

	public String getMiles(){
		return miles;
	}
}