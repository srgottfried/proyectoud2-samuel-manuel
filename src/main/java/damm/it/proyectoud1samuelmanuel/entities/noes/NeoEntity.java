package damm.it.proyectoud1samuelmanuel.entities.noes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NeoEntity {
	@JsonProperty("estimated_diameter")
	private EstimatedDiameter estimatedDiameter;

	@JsonProperty("neo_reference_id")
	private String neoReferenceId;

	@JsonProperty("nasa_jpl_url")
	private String nasaJplUrl;

	@JsonProperty("is_potentially_hazardous_asteroid")
	private boolean isPotentiallyHazardousAsteroid;

	@JsonProperty("is_sentry_object")
	private boolean isSentryObject;

	@JsonProperty("name")
	private String name;

	@JsonProperty("absolute_magnitude_h")
	private double absoluteMagnitudeH;

	@JsonProperty("links")
	private Links links;

	@JsonProperty("id")
	private String id;

	@JsonProperty("close_approach_data")
	private List<CloseApproachDataItem> closeApproachData;

	public EstimatedDiameter getEstimatedDiameter(){
		return estimatedDiameter;
	}

	public String getNeoReferenceId(){
		return neoReferenceId;
	}

	public String getNasaJplUrl(){
		return nasaJplUrl;
	}

	public boolean isIsPotentiallyHazardousAsteroid(){
		return isPotentiallyHazardousAsteroid;
	}

	public boolean isIsSentryObject(){
		return isSentryObject;
	}

	public String getName(){
		return name;
	}

	public double getAbsoluteMagnitudeH(){
		return absoluteMagnitudeH;
	}

	public Links getLinks(){
		return links;
	}

	public String getId(){
		return id;
	}

	public List<CloseApproachDataItem> getCloseApproachData(){
		return closeApproachData;
	}
}