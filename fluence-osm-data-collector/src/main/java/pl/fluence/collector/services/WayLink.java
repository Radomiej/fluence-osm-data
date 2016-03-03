package pl.fluence.collector.services;

import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import com.vividsolutions.jts.geom.LineString;

public class WayLink {
	private Way way;
	private LineString line;

	public Way getWay() {
		return way;
	}

	public void setWay(Way way) {
		this.way = way;
	}

	public LineString getLine() {
		return line;
	}

	public void setLine(LineString line) {
		this.line = line;
	}
}
