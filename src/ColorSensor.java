import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.robotics.Color;

public class ColorSensor extends EV3ColorSensor {

	public HashMap<String, float[]> colors;

	public ColorSensor() {
		super(SensorPort.S1);
		// Port port = LocalEV3.get().getPort("S1");
		colors = new HashMap<String, float[]>();
		initfromfile();
		
	}
	
	public void initfromfile() {
		try (BufferedReader br = new BufferedReader(new FileReader("/home/root/lejos/config/colors.txt"))) {
			//pour chaque ligne du fichier non vide, on lit une ligne avec ';' en tant que séparateur
			for (String line; (line = br.readLine()) != null;) {
				if(!line.isEmpty()) {
					String[] separated = line.split(";");
					String key = separated[0];
					float[] values = new float[separated.length-1];
					for(int i = 1; i<separated.length;i++) {
						values[i-1] = Float.parseFloat(separated[i]);
					}
					colors.put(key, values);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERREUR LECTURE FICHIER");
		}
	}

	public void calibrate() {

		SampleProvider average = new MeanFilter(getRGBMode(), 1);
		setFloodlight(Color.WHITE);

		for (String s : new String[]{"blue", "red", "green", "yellow", "white", "grey", "black"}) {
			System.out.println("Press enter to calibrate " + s);
			Button.ENTER.waitForPressAndRelease();
			float[] res = new float[average.sampleSize()];
			average.fetchSample(res, 0);
			colors.put(s,res);
		}
		
		FileWriter fw;
		try {
			fw = new FileWriter("/home/root/lejos/config/colors.txt");
			
			//pour chaque valeur de la hashmap on écrit une ligne de type "rouge;0.8555;0.23356;0.1552"
			for (HashMap.Entry<String, float[]> entry : colors.entrySet()) {
				String s = entry.getKey();
				float[] values = entry.getValue();
				for (float f : values) {
					s += ";" + f; 
				}
				s+="\n";
				fw.write(s);
			}
			fw.close();

		} catch (Exception e) {
			System.out.println("ERREUR ECRITURE FICHIER");
		}
	}

	public String detectColor() {

		setFloodlight(Color.WHITE);
		SampleProvider average = new MeanFilter(getRGBMode(), 1);
		float[] sample = new float[average.sampleSize()];
		average.fetchSample(sample, 0);

		double minscal = Double.MAX_VALUE;
		String res = "ERROR";

		for (String color : colors.keySet()) {
			double scalaire = scalaire(sample, colors.get(color));
			if (scalaire < minscal) {
				minscal = scalaire;
				res = color;
			}
		}

		return res;
	}

	public static double scalaire(float[] v1, float[] v2) {
		return 	Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
				Math.pow(v1[1] - v2[1], 2.0) +
				Math.pow(v1[2] - v2[2], 2.0));
	}
}
