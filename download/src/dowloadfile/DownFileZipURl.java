package dowloadfile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import dowloadfileutil.DownloadUtil;
import execption.MyException;

public class DownFileZipURl implements Callable<List<String>>{
	// url链接
	private String url;
	// 输出的流
	private ZipOutputStream zos;
	
	// 下载失败后保存的list
	private List<String> urlList = new ArrayList<String>();
	public DownFileZipURl(String url, ZipOutputStream zos,List<String> urlList) {
		super();
		this.url = url;
		this.zos = zos;
		this.urlList = urlList;
	}
	@Override
	public List<String> call() throws Exception {
		String name = Thread.currentThread().getName();
		System.out.println("线程"+name);
		//调用下载的方法
		downloadFileToZip();
		return urlList;
	}

	/**
	 * 将从url链接中读取的输入流输出到压缩文件中
	 */
	private void downloadFileToZip() {
		try {
			URL myUrl = new URL(url);
			synchronized (zos) {
				try {
					zos.putNextEntry(new ZipEntry(DownloadUtil.getFileName(url)));
					BufferedInputStream bis = new BufferedInputStream(myUrl
							.openConnection().getInputStream());
					int len = 0;
					byte[] b = new byte[1024];
					while ((len = bis.read(b)) != -1) {
						zos.write(b, 0, len);
					}
				} catch (IOException e) {
					// 下载失败后把url添加到list中 并返回到实现类中
					urlList.add(url);
					MyException ex = new MyException(e);
					ex.process(e, "没有找到此文件");
					e.printStackTrace();
				}
			}
		} catch (MalformedURLException e) {
			MyException ex = new MyException(e);
			ex.process(e, url);
			e.printStackTrace();
		}
	}
}
