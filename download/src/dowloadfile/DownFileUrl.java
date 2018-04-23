package dowloadfile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;

import dowloadfileutil.DownloadUtil;
import execption.MyException;

public class DownFileUrl implements Callable<List<String>> {

	
	// 单个链接
	private String url;
	// 指定保存文件的路径
	private String filePath;
	// 下载失败后保存的list
	private List<String> urlList = new ArrayList<String>();
	
	public DownFileUrl(String url, String filePath,List<String> urlList) {
		super();
		this.url = url;
		this.filePath = filePath;
		this.urlList = urlList;
	}



	@Override
	public List<String> call() throws Exception {
		downLoadFile();
		return urlList;
	}
	
	/**
	 * 通过url链接下载的方法
	 */
	public void downLoadFile() {
		
		try {
			URL myUrl = new URL(url);
			String fileName = DownloadUtil.getFileName(url);
			File file = new File(filePath + fileName);
			try {
				FileUtils.copyURLToFile(myUrl, file);
			} catch (IOException e) {
				// 下载失败后把url添加到list中 并返回到实现类中
				urlList.add(url);
				MyException ex = new MyException(e);
				ex.process(e, "下载失败");
			}
		} catch (MalformedURLException e) {
			MyException ex = new MyException(e);
			ex.process(e, url);
		}
	}

}
