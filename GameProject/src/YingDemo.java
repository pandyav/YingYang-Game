import java.awt.Color;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
//Ying Yang Game. 
//We worked in pairs
//Vaibhav Pandya, Justin Babel

public class YingDemo extends JApplet {
	

	public static void main(String s[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Hit The Yings");
		JApplet applet = new YingDemo();
		applet.init();	
		frame.getContentPane().add(applet);
		frame.pack();		
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void init() {
		JPanel panel = new YingYang();
		panel.setBackground(Color.black);	
		getContentPane().add(panel);
		

	}
}