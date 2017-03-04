package pl.fluence.collector.optimizing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.fluence.collector.services.WayLink;

public class WayLinkBufferLat {

	private Map<Long, List<WayLink>> wayLinkMap = new HashMap<Long, List<WayLink>>();
	private int scale = 1000;

	public WayLinkBufferLat(int scale) {
		this.scale = scale;
	}

	public void addWayLink(WayLink wayLink) {
		double lon = wayLink.getLine().getCentroid().getY();
		long index = (long) (lon * scale);
		List<WayLink> lonBuffor = wayLinkMap.get(index);
		if(lonBuffor == null) {
			lonBuffor = new ArrayList<WayLink>();
			wayLinkMap.put(index, lonBuffor);
		}
		lonBuffor.add(wayLink);
	}

	public List<WayLink> getWayLinks(double lon) {
		long indexY = (long) (lon * scale);
		List<WayLink> lonBuffor = wayLinkMap.get(indexY);
		if(lonBuffor == null) return Collections.EMPTY_LIST;
		return lonBuffor;
	}

}
