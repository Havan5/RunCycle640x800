import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Demo1 {
	private static final int WINDOW_WIDTH = 350;
	private static final int WINDOW_HEIGHT = 170;
	public Demo1() {
		JFrame jf = new JFrame("Demo1");
		
		MyCanvas canvas = new MyCanvas();
		jf.add(canvas);
		canvas.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		jf.pack();
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.createBufferStrategy(2);
		canvas.setIgnoreRepaint(true);
		new Timer(1000 / 20, ae -> canvas.draw()).start();
	}
	public static void main(String[] sa) {
		EventQueue.invokeLater(Demo1::new);
	}
	class MyCanvas extends Canvas {
		private static final long serialVersionUID = 1L;
		private static final int FRAME_WIDTH = 215;
		private static final int FRAME_HEIGHT = 100;
		private BufferedImage bi;
		private int index = 0;
		public MyCanvas() {
			try {
				bi = ImageIO.read(new File("./Tiger-run-Poster-640x800.jpg"));
			} catch (Exception ex) {
				System.exit(-1);
			}
		}
		public void draw() {
			BufferStrategy bs = getBufferStrategy();
			Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
			g2d.clearRect(0, 0, getWidth(), getHeight());
			g2d.drawImage(
				getFrame(),
				getWidth() / 2 - FRAME_WIDTH,
				getHeight() / 2 - FRAME_HEIGHT,
				getWidth() / 2 + FRAME_WIDTH,
				getHeight() / 2 + FRAME_HEIGHT,
				0,
				0,
				FRAME_WIDTH,
				FRAME_HEIGHT,
				this
			);
			g2d.dispose();
			bs.show();
			Toolkit.getDefaultToolkit().sync();
		}
		private Image getFrame() {
			BufferedImage biFrame = new BufferedImage(
				FRAME_WIDTH,
				FRAME_HEIGHT,
				BufferedImage.TYPE_INT_BGR
			);
			Graphics2D g2d = biFrame.createGraphics();
			g2d.drawImage(
				bi,
				0,
				0,
				FRAME_WIDTH,
				FRAME_HEIGHT,
				index % 3 * 207,
				index < 3 ? 99 : (index / 3 - 1) * 116 + 199,
				index % 3 * 207 + FRAME_WIDTH,
				index < 3 ? 199 : (index / 3 - 1) * 116 + 199 + FRAME_HEIGHT,
				this
			);
			index = index < 17 ? index + 1 : 0;
			g2d.dispose();
			return biFrame;
		}
	}
}
