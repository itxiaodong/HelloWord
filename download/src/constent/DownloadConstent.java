package constent;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadConstent {
	
	/**
	 * 默认链接超时时间
	 */
	public static final int CONNECTIONTIMEOUT = 60000;
	
	/**
	 * 默认读取时间
	 */
	public static final int READTIMEOUT = 60000;
	/**
	 * 线程池默认个数
	 */
	public static final int THREADNUMBER = 50;
	public static InputStream getInputStream(String url,int connectTimeout,int readTimeout){
		
		URL myUrl = null;
		URLConnection connection = null;
		try {
			myUrl = new URL(url);
			connection = myUrl.openConnection();
			connection.setConnectTimeout(connectTimeout);
			connection.setReadTimeout(readTimeout);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(connection != null){
			try {
				return connection.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
