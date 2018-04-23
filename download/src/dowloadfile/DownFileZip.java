package dowloadfile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import dowloadfileutil.DownloadUtil;
import execption.MyException;

/**
 * 
 * @Description: TODO(url链接打包下载)
 * @author 董景帅
 * @ClassName: DownFileZip
 * @date 2015-12-23 下午5:30:57 
 * @version V1.0
 *
 */
public class DownFileZip implements Runnable{
	
	// url链接
	private String url;
	// 输出的流
	private ZipOutputStream zos;
	public DownFileZip(String url, ZipOutputStream zos) {
		super();
		this.url = url;
		this.zos = zos;
	}

	@Override
	public void run() {
		//调用下载的方法
		downloadFileToZip();
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
					MyException ex = new MyException(e);
					ex.process(e, "没有找到此文件");
					e.printStackTrace();
				}
			}
		} catch (MalformedURLException e) {
			MyException ex = new MyException(e);
			ex.process(e, url);
			e.printStackTrace();
		} finally {
			if (zos != null) {
				try {
					zos.closeEntry();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}