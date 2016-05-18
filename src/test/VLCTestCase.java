package test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

public class VLCTestCase {

	//XXX vlc will have errors loading 32 bit vlclib with a 64 bit jre
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

	public static void main(final String[] args) {
		

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:/Program Files/VideoLAN/VLC/");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        LibXUtil.initialise();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VLCTestCase(args);
			}
		});
	}

	private VLCTestCase(String[] args) {
		System.out.println("vlc test");
		JFrame frame = new JFrame("vlcj Tutorial");

		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

		frame.setContentPane(mediaPlayerComponent);

		frame.setLocation(100, 100);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		mediaPlayerComponent.getMediaPlayer().playMedia(
				"C:\\Users\\Jake\\Desktop\\ECLIPSE\\workspace\\MediaManager\\data\\test\\movies\\temp\\Fight Club (1999) [1080p].mp4");

	}

}
