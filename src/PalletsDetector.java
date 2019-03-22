import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class PalletsDetector {

	public final static int MAX_PALLETS = 9;
	private final static int port = 8888;
	private DatagramSocket dsocket;

	public PalletsDetector() {
		super();
	}

	public void update(Coord [] coords) {
		try {
			dsocket = new DatagramSocket(port);

			// Create a buffer to read datagrams into. If a
			// packet is larger than this buffer, the
			// excess will simply be discarded!
			byte[] buffer = new byte[2048];

			// Create a packet to receive data into the buffer
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			// Wait to receive a datagram
			dsocket.receive(packet);

			String msg = new String(buffer, 0, packet.getLength());
			String[] lines = msg.split("\n");

			for (int i = 0; i < lines.length; i++) {
				String[] values = lines[i].split(";");
				coords[i] = new Coord(Integer.parseInt(values[2]), Integer.parseInt(values[1]));
//				System.out.println(coords[i]);
			}
			for (int i = lines.length; i < MAX_PALLETS; i++) coords[i] = null;

			// Reset the length of the packet before reusing it.
			packet.setLength(buffer.length);

		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
