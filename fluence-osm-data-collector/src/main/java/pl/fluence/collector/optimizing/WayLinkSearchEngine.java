package pl.fluence.collector.optimizing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.fluence.collector.services.WayLink;

public class WayLinkSearchEngine {
	private Map<Long, WayLinkBufferLat> byLatMap = new HashMap<Long, WayLinkBufferLat>();
	
	private int scale = 1000;
	
	public WayLinkSearchEngine() {
	}
	
	public WayLinkSearchEngine(int scale){
		this.scale = scale;
	}
	
	public void addWayLink(WayLink wayLink){
		double lat = wayLink.getLine().getCentroid().getX();
		long index = (long)(lat * scale);
		WayLinkBufferLat latBuffor = byLatMap.get(index);
		
		if(latBuffor == null) {
			latBuffor = new WayLinkBufferLat(scale);
			byLatMap.put(index, latBuffor);
		}
		
		latBuffor.addWayLink(wayLink);
	}
	
	public List<WayLink> getWayLinks(double lat, double lon){
		long indexX = (long)(lat * scale);
		
		WayLinkBufferLat bufferLat = byLatMap.get(indexX);
		if(bufferLat == null) return Collections.EMPTY_LIST;
		
		return bufferLat.getWayLinks(lon);
	}

	public List<WayLink> getWayLinksRect(double lat, double lon) {
		long indexX = (long)(lat * scale);
		long indexY = (long)(lon * scale);
		
		List<WayLink> ways = new ArrayList<WayLink>();
		ways.addAll(getWayLinks(indexX, indexY));
		
		ways.addAll(getWayLinks(indexX - 1, indexY));
		ways.addAll(getWayLinks(indexX + 1, indexY));
		ways.addAll(getWayLinks(indexX, indexY - 1));
		ways.addAll(getWayLinks(indexX, indexY + 1));
		
		ways.addAll(getWayLinks(indexX - 1, indexY - 1));
		ways.addAll(getWayLinks(indexX - 1, indexY + 1));
		ways.addAll(getWayLinks(indexX + 1, indexY - 1));
		ways.addAll(getWayLinks(indexX + 1, indexY + 1));
		
		return ways;
	}
}
