import java.util.HashMap;

public class localMain {

	public static void main(String[] args) {
		saveFile f = new saveFile("test.txt");
		HashMap<String, float[]> h = new HashMap<String, float[]>();
		float[] temp = {(float) 0.8,(float) 0.2,(float) 0.2};
		h.put("rouge", temp);
		h.put("bleu", temp);
		h.put("vert", temp);
		
		f.writeColor(h);
		saveFile f2 = new saveFile("test.txt");
		HashMap<String, float[]> h2 = f.readColor();
		System.out.println(h2.get("rouge")[0]); 
	}

}
