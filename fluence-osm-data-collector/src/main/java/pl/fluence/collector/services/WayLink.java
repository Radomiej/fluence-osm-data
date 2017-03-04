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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((line == null) ? 0 : line.hashCode());
		result = prime * result + ((way == null) ? 0 : way.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WayLink other = (WayLink) obj;
		if (line == null) {
			if (other.line != null)
				return false;
		} else if (!line.equals(other.line))
			return false;
		if (way == null) {
			if (other.way != null)
				return false;
		} else if (!way.equals(other.way))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WayLink [way=" + way + ", line=" + line + "]";
	}
}
