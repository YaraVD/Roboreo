	import java.util.HashMap;

import lejos.hardware.Button;
	import lejos.hardware.ev3.LocalEV3;
	import lejos.hardware.lcd.Font;
	import lejos.hardware.lcd.GraphicsLCD;
	import lejos.hardware.port.Port;
	import lejos.hardware.sensor.EV3ColorSensor;
	import lejos.robotics.SampleProvider;
	import lejos.robotics.filter.MeanFilter;
	import lejos.utility.Delay;
	import lejos.robotics.Color;
	
public class TestCouleur {
	
	private static HashMap<String, float[]> colors = new HashMap<String, float[]>();

	public static boolean goMessage() {
			
			GraphicsLCD g = LocalEV3.get().getGraphicsLCD();
			g.clear();
			g.drawString("Color Sensor", 5, 0, 0);
			g.setFont(Font.getSmallFont());
			 
			g.drawString("The code for this sample     ", 2, 20, 0);
			g.drawString("shows how to work with the ", 2, 30, 0);
			g.drawString("Color Sensor ", 2, 40, 0);
			g.drawString("To run the ", 2, 60, 0);
			g.drawString("code one needs an EV3 ", 2, 70, 0);
			g.drawString("brick with a EV3 color sensor", 2, 80, 0); 
			g.drawString("attached to port 4.", 2, 90, 0);
			  
			// Quit GUI button:
			g.setFont(Font.getSmallFont()); // can also get specific size using Font.getFont()
			int y_quit = 100;
			int width_quit = 45;
			int height_quit = width_quit/2;
			int arc_diam = 6;
			g.drawString("QUIT", 9, y_quit+7, 0);
			g.drawLine(0, y_quit,  45, y_quit); // top line
			g.drawLine(0, y_quit,  0, y_quit+height_quit-arc_diam/2); // left line
			g.drawLine(width_quit, y_quit,  width_quit, y_quit+height_quit/2); // right line
			g.drawLine(0+arc_diam/2, y_quit+height_quit,  width_quit-10, y_quit+height_quit); // bottom line
			g.drawLine(width_quit-10, y_quit+height_quit, width_quit, y_quit+height_quit/2); // diagonal
			g.drawArc(0, y_quit+height_quit-arc_diam, arc_diam, arc_diam, 180, 90);
			
			// Enter GUI button:
			g.fillRect(width_quit+10, y_quit, height_quit, height_quit);
			g.drawString("GO", width_quit+15, y_quit+7, 0,true);
			
			Button.waitForAnyPress();
			if(Button.ESCAPE.isDown()) {
				return false;
			}
			else {
				g.clear();
				return true;
			}
		}

		/**
		 * @param args
		 */
		public static void main(String[] args) {
			try {
				
				boolean again = true;
				
				if (!goMessage()) System.exit(0);
				
				Port port = LocalEV3.get().getPort("S1");
				EV3ColorSensor colorSensor = new EV3ColorSensor(port);
				SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
				colorSensor.setFloodlight(Color.WHITE);
				
				System.out.println("Press enter to calibrate blue...");
				Button.ENTER.waitForPressAndRelease();
				float[] blue = new float[average.sampleSize()];
				average.fetchSample(blue, 0);
				//colors.put("blue",blue);
				System.out.print("bleu : ");
				for (float b : blue) {
					System.out.print(b + " ");
				}
				System.out.println();
				
				
				
				System.out.println("Press enter to calibrate red...");
				Button.ENTER.waitForPressAndRelease();
				float[] red = new float[average.sampleSize()];
				average.fetchSample(red, 0);
				colors.put("red",red);
				System.out.print("rouge : ");
				for (float b : red) {
					System.out.print(b + " ");
				}
				System.out.println();
				
				System.out.println("Press enter to calibrate green...");
				Button.ENTER.waitForPressAndRelease();
				float[] green = new float[average.sampleSize()];
				average.fetchSample(green, 0);
				colors.put("green",green);
				System.out.println("Valeur green : " + green);

				System.out.println("Press enter to calibrate yellow...");
				Button.ENTER.waitForPressAndRelease();
				float[] yellow = new float[average.sampleSize()];
				average.fetchSample(yellow, 0);
				colors.put("yellow",yellow);
				System.out.println("Valeur yellow : " + yellow);
				
				System.out.println("Press enter to calibrate white...");
				Button.ENTER.waitForPressAndRelease();
				float[] white = new float[average.sampleSize()];
				average.fetchSample(white, 0);
				colors.put("white",white);
				System.out.println("Valeur white : " + white);
				
				System.out.println("Press enter to calibrate grey...");
				Button.ENTER.waitForPressAndRelease();
				float[] grey = new float[average.sampleSize()];
				average.fetchSample(grey, 0);
				colors.put("grey",grey);
				System.out.println("Valeur grey : " + grey);
				
				System.out.println("Press enter to calibrate black...");
				Button.ENTER.waitForPressAndRelease();
				float[] black = new float[average.sampleSize()];
				average.fetchSample(black, 0);
				colors.put("black",black);
				System.out.println("Valeur bleu : " + black);
				System.out.println("All colors Calibrated :) PS : T'as pécho hier ?");
				
				saveFile f = new saveFile("/lejos/config/colors.txt");
				f.writeColor(colors); 
				
				while (again) {
					float[] sample = new float[average.sampleSize()];
					System.out.println("\nPress enter to detect a color...");
					Button.ENTER.waitForPressAndRelease();
					average.fetchSample(sample, 0);
					double minscal = Double.MAX_VALUE;
					String color = "";
					
					double scalaire = TestCouleur.scalaire(sample, colors.get("blue"));
					//Button.ENTER.waitForPressAndRelease();
					//System.out.println(scalaire);
					
					if (scalaire < minscal) {
						minscal = scalaire;
						color = "blue";
					}
					
					scalaire = TestCouleur.scalaire(sample, colors.get("red"));
					//System.out.println(scalaire);
					//Button.ENTER.waitForPressAndRelease();
					if (scalaire < minscal) {
						minscal = scalaire;
						color = "red";
					}
					
					scalaire = TestCouleur.scalaire(sample, colors.get("green"));
					//System.out.println(scalaire);
					//Button.ENTER.waitForPressAndRelease();
					if (scalaire < minscal) {
						minscal = scalaire;
						color = "green";
					}
					
					scalaire = TestCouleur.scalaire(sample, colors.get("yellow"));
					//System.out.println(scalaire);
					//Button.ENTER.waitForPressAndRelease();
					if (scalaire < minscal) {
						minscal = scalaire;
						color = "yellow";
					}
					
					scalaire = TestCouleur.scalaire(sample, colors.get("white"));
					//System.out.println(scalaire);
					//Button.ENTER.waitForPressAndRelease();
					if (scalaire < minscal) {
						minscal = scalaire;
						color = "white";
					}
					
					scalaire = TestCouleur.scalaire(sample, colors.get("grey"));
					//System.out.println(scalaire);
					//Button.ENTER.waitForPressAndRelease();
					if (scalaire < minscal) {
						minscal = scalaire;
						color = "grey";
					}
					scalaire = TestCouleur.scalaire(sample, colors.get("black"));
					//System.out.println(scalaire);
					//Button.ENTER.waitForPressAndRelease();
					if (scalaire < minscal) {
						minscal = scalaire;
						color = "black";
					}
					
					System.out.println("The color is " + color + " \n");
					System.out.println("Press ENTER to continue \n");
					System.out.println("ESCAPE to exit");
					Button.waitForAnyPress();
					if(Button.ESCAPE.isDown()) {
						colorSensor.setFloodlight(false);
						again = false;
					}
				}
				
				
			} catch (Throwable t) {
				t.printStackTrace();
				Delay.msDelay(10000);
				System.exit(0);
			}
		}
		
		public static double scalaire(float[] v1, float[] v2) {
			return Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
					Math.pow(v1[1] - v2[1], 2.0) +
					Math.pow(v1[2] - v2[2], 2.0));
		}

	}
