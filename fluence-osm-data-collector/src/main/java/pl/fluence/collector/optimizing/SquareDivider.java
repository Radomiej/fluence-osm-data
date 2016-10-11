package pl.fluence.collector.optimizing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;

import pl.fluence.reader.importers.OsmImporter;
import pl.fluence.reader.processors.AllElementsProccesor;
import pl.fluence.reader.processors.BoundProccesor;

public class SquareDivider {
	private Bound mapBound;
	private List<SquareMap> squaresMap = new ArrayList<SquareMap>();
	private File mapFile;
	private float sizeSquare = 8;
	
	public SquareDivider(String mapPath){
		mapBound = getMapBound(mapPath);
		
		int top = mapBound.getTop() >= 0 ? (int) Math.ceil(mapBound.getTop()) : (int) Math.floor(mapBound.getTop());
		int right = mapBound.getRight() >= 0 ? (int) Math.ceil(mapBound.getRight()) : (int) Math.floor(mapBound.getRight());
		
		int bottom = mapBound.getBottom() >= 0 ? (int) Math.floor(mapBound.getBottom()) : (int) Math.ceil(mapBound.getBottom());
		int left = mapBound.getLeft() >= 0 ? (int) Math.floor(mapBound.getLeft()) : (int) Math.ceil(mapBound.getLeft());
	
		System.out.println("top: " + top + " bottom: " + bottom + " left: " + left + " right: " + right);
		for(int x = left; x < right + (sizeSquare / 2); x += sizeSquare){
			for(int y = bottom; y < top + (sizeSquare / 2); y += sizeSquare){
				SquareMap square = new SquareMap(x + (sizeSquare / 2), y + (sizeSquare / 2), sizeSquare, sizeSquare * 1.2f);
				squaresMap.add(square);
			}
		}
	}

	private Bound getMapBound(String mapPath) {
		mapFile = new File(mapPath);
		OsmImporter osmImporter = new OsmImporter();
		
		BoundProccesor boundProccesor = new BoundProccesor();
		osmImporter.addProccessor(boundProccesor);
		osmImporter.proccesImport(mapFile);
		System.out.println("bounds: " + boundProccesor.getBounds().size());
		return boundProccesor.getBounds().iterator().next();
	}

	public List<SquareMap> getSquaresMap() {
		return new ArrayList<SquareMap>(squaresMap);
	}

	public File getMapFile() {
		return mapFile;
	}

	public void setMapFile(File mapFile) {
		this.mapFile = mapFile;
	}

}
