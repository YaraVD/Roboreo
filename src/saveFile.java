import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class saveFile {
	private File f;

	public saveFile(String path) {
		f = new File(path);
	}

	public void writeColor(HashMap<String, float[]> h) {
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			
			//pour chaque valeur de la hashmap on écrit une ligne de type "rouge;0.8555;0.23356;0.1552"
			for (HashMap.Entry<String, float[]> entry : h.entrySet()) {
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

	public HashMap<String, float[]> readColor() {
		HashMap<String, float[]> h = new HashMap<String, float[]>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			//pour chaque ligne du fichier non vide, on lit une ligne avec ';' en tant que séparateur
			for (String line; (line = br.readLine()) != null;) {
				if(!line.isEmpty()) {
					String[] separated = line.split(";");
					String key = separated[0];
					float[] values = new float[separated.length-1];
					for(int i = 1; i<separated.length;i++) {
						values[i-1] = Float.parseFloat(separated[i]);
					}
					h.put(key, values);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERREUR LECTURE FICHIER");
		}
		return h;
	}
	

}
