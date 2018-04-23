package dowloadfileIFaceImpl;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DownloadFileIFaceImplTest {

	DownloadFileIFaceImpl d = null;
	List<String> urlList = null;
	String filePath = "e:/qq/3/";
	@Before
	public void setUp() throws Exception {
		d = new DownloadFileIFaceImpl();
		urlList = new ArrayList<String>();
 		urlList.add("file:\\e:/qq/5/哈哈.jpg");
 		urlList.add("file:\\e:/qq/6/哈哈1.jpg");
 		urlList.add("file:\\e:/qq/5/1.txt");
 		urlList.add("file:\\e:/qq/5/7411759_164157418126_2.jpg");
	}

	@Test
	public void testdownFileFromUr() {
		d.downFileFromUr("file:\\e:/qq/5/1.txt", filePath);
	}
	
	
	@Test
	public void testDownFileFromUrlList() {
		d.downFileFromUrlList(urlList, filePath);
	}
	@Test
	public void testdownFileZipFromUrlList() {
		List<String> list = d.downFileZipFromUrlList(urlList, filePath);
		System.out.println(list);
	}

}
