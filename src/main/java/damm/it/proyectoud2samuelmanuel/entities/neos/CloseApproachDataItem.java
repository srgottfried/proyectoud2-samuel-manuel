package damm.it.proyectoud2samuelmanuel.entities.neos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloseApproachDataItem {
	@JsonProperty("relative_velocity")
	private RelativeVelocity relativeVelocity;

	@JsonProperty("orbiting_body")
	private String orbitingBody;

	@JsonProperty("close_approach_date")
	private String closeApproachDate;

	@JsonProperty("epoch_date_close_approach")
	private long epochDateCloseApproach;

	@JsonProperty("close_approach_date_full")
	private String closeApproachDateFull;

	@JsonProperty("miss_distance")
	private MissDistance missDistance;

	public RelativeVelocity getRelativeVelocity(){
		return relativeVelocity;
	}

	public String getOrbitingBody(){
		return orbitingBody;
	}

	public String getCloseApproachDate(){
		return closeApproachDate;
	}

	public long getEpochDateCloseApproach(){
		return epochDateCloseApproach;
	}

	public String getCloseApproachDateFull(){
		return closeApproachDateFull;
	}

	public MissDistance getMissDistance(){
		return missDistance;
	}
}