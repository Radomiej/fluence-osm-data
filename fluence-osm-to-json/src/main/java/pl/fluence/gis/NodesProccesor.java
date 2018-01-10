package pl.fluence.gis;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import pl.fluence.reader.processors.OsmElementProccesor;

public class NodesProccesor implements OsmElementProccesor {

	private ConcurrentMap<Long, byte[]> nodes;

	public NodesProccesor() {
		DB db = DBMaker.fileDB("nodes.db").make();
		nodes = db.hashMap("nodes", Serializer.LONG, Serializer.BYTE_ARRAY_DELTA).createOrOpen();
	}

	public void complete() {
		System.out.println("size: " + nodes.size());
	}

	public void proccesWay(Way way) {

	}

	public void proccesRelation(Relation relation) {

	}

	public void proccesBound(Bound bound) {

	}

	public void proccesNode(Node node) {
		byte[] positionBytes = toByteArrayPosition((float) node.getLatitude(), (float) node.getLongitude());
		long position = toLong(positionBytes);
		nodes.put(node.getId(), positionBytes);
	}

	public static byte[] toByteArrayPosition(float latitude, float longitude) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putFloat(latitude).putFloat(longitude);
		return bytes;
	}

	public static byte[] toByteArray(double value) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putDouble(value);
		return bytes;
	}

	public static byte[] toByteArray(long value) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putLong(value);
		return bytes;
	}

	public static double toDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}

	public static long toLong(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getLong();
	}

	public static float toLatitude(long position) {
		byte[] longValue = toByteArray(position);
		return ByteBuffer.wrap(longValue).getFloat();
	}

	public static float toLongitudee(long position) {
		byte[] longValue = toByteArray(position);
		return ByteBuffer.wrap(longValue).getFloat(4);
	}

}
