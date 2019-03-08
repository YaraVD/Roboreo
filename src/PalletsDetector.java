import java.net.DatagramSocket;
import java.lang.Thread;

public class PalletsDetector extends Thread {

	private final static int port = 8888;
	private DatagramSocket dsocket;
	private boolean stop;

	public PalletsDetector() {
		super();
	}

	public void run() {
		try {

			dsocket = new DatagramSocket(port);

			// Create a buffer to read datagrams into. If a
			// packet is larger than this buffer, the
			// excess will simply be discarded!
			byte[] buffer = new byte[2048];

			// Create a packet to receive data into the buffer
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			// Now loop forever, waiting to receive packets and printing them.
			while (!stop) {

				// Wait to receive a datagram
				dsocket.receive(packet);

				String msg = new String(buffer, 0, packet.getLength());
				String[] palets = msg.split("\n");

				for (int i = 0; i < palets.length; i++) {
//					String[] coord = palets[i].split(";");
//					int x = Integer.parseInt(coord[1]);
//					int y = Integer.parseInt(coord[2]);
				}


				// Reset the length of the packet before reusing it.
				packet.setLength(buffer.length);
			}

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void end() {
		stop = true;
	}
}
