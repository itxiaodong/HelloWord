package dowloadfile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import dowloadfileutil.DownloadUtil;


/**
 * 
 * @Description: TODO(一个url链接下载)
 * @author 董景帅
 * @ClassName: DownloadFile
 * @date 2015-12-23 下午5:31:35 
 * @version V1.0
 *
 */
public class DownloadFile {
	/**
	 * 单url下载文件
	 * @param url
	 * @param filePath
	 */
	public void downloadFromUrl(String url, String filePath) {
		
			filePath = DownloadUtil.creatFile(filePath);
			URL httpurl;
			try {
				httpurl = new URL(url);
				File file = null;
				if(!filePath.contains(".")){//判断用户是否重命名文件
					String fileName = DownloadUtil.getFileName(url);
					file = new File(filePath + fileName);
				}else {
					file = new File(filePath);
				}
				try {
					FileUtils.copyURLToFile(httpurl, file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	}
}
