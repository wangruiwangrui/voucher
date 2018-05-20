import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class CompressTest {

	public static void main(String[] args) {
		
		String fromPic="C:\\Users\\WangJing\\Desktop\\bb\\c.jpg";
		
		String toPic="C:\\Users\\WangJing\\Desktop\\bb\\c2.jpg";
		
		try {
			Thumbnails.of(fromPic).scale(0.35f).outputQuality(0.25f).toFile(toPic);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
