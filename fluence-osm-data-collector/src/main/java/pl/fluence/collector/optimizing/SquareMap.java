package pl.fluence.collector.optimizing;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;

public class SquareMap {
	private double latitudeMiddle, longitudeMiddle;
	private float range = 1.0f;
	private float overlapRange = 1.2f;

	public SquareMap(float latitudeMiddle, float longitudeMiddle) {
		this(latitudeMiddle, longitudeMiddle, 1.0f, 1.2f);
	}

	public SquareMap(float latitudeMiddle, float longitudeMiddle, float range, float overlapRange) {
		this.latitudeMiddle = latitudeMiddle;
		this.longitudeMiddle = longitudeMiddle;
		this.range = range;
		this.overlapRange = overlapRange;
	}

	public Bound getBound() {
		return new Bound(latitudeMiddle + range, latitudeMiddle - range, longitudeMiddle + range, longitudeMiddle - range, "OSM");
	}

	@Override
	public String toString() {
		return "Square (" + latitudeMiddle + ", " + longitudeMiddle + ") with Bound: " + getBound();
	}

	public Bound getOverlapBound() {
		return new Bound(latitudeMiddle + overlapRange, latitudeMiddle - overlapRange, longitudeMiddle + overlapRange, longitudeMiddle - overlapRange, "OSM");
	}
}
