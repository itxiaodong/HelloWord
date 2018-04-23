package dowloadfileutil;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @Description: TODO(获取url中的文件名和生成zip包名)
 * @author 董景帅
 * @ClassName: DownloadUtil
 * @date 2015-12-23 下午5:38:19 
 * @version V1.0
 *
 */
public class DownloadUtil {
	
	/**
	 * 创建指定的文件或文件夹
	 * 
	 * @param filePath
	 */
	public static String creatFile(String filePath) {
		File file = new File(filePath);
		if (filePath.contains(".")) {// 单个文件下载重命名
			File parentFile = file.getParentFile();
			System.out.println(parentFile);
			if (parentFile != null) {
				parentFile.mkdirs();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (!file.exists()) {
				file.mkdirs();
			}
			if (!filePath.endsWith("\\")) {// 新建文件夹
				filePath = file.toString() + File.separator;
			}
		}
		return filePath;
	}
	
	

	/**
	 * 按日期自定义生成zip名字
	 * 
	 * @return
	 */
	public static String getZipFilename() {
		Date date = new Date();
		String s = date.getTime() + ".zip";
		return s;
	}
	
	/**
	 * 获取url中的文件名
	 * 
	 * @param url
	 *            url链接
	 * @return
	 */
	public static String getFileName(String url) {
		// 文件名
		String fileName = null;
		URLConnection conn = null;
		URL myUrl = null;
		try {
			myUrl = new URL(url);
			try {
				conn = myUrl.openConnection();
				if (conn == null) {
					return null;
				}
				Map<String, List<String>> map = conn.getHeaderFields();
				if (map == null) {
					return null;
				}
				Set<String> keys = map.keySet();
				if (keys == null) {
					return null;
				}
				for (String key : keys) {
					List<String> values = map.get(key);
					for (String value : values) {
						String result;
						result = new String(value.getBytes("ISO-8859-1"),
								"UTF-8");
						int index = result.indexOf("filename=");
						if (index >= 0) {
							fileName = result.substring(index
									+ "filename=".length());
						}
					}
					if (fileName != null) {
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (fileName == null || "".equals(fileName)) {
			try {
				URI u = new URI(myUrl.getProtocol(), myUrl.getHost(),
						myUrl.getPath(), myUrl.getQuery(), null);
				String path = u.getRawPath();
				fileName = path.substring(path.lastIndexOf("/") + 1);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}
}
