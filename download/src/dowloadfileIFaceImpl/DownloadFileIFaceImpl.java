package dowloadfileIFaceImpl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.ZipOutputStream;
import constent.DownloadConstent;
import dowloadfile.DownFileUrl;
import dowloadfile.DownFileZipURl;
import dowloadfile.DownloadFile;
import dowloadfileutil.DownloadUtil;

/**
 * 
 * @Description: TODO(下载文件的实现类)
 * @author 董景帅
 * @ClassName: DownloadFileIfaceImpl
 * @date 2015-12-23 下午5:27:22 
 * @version V1.0
 *
 */
public class DownloadFileIFaceImpl {
	
	
	/**
	 * 单个url下载
	 * @param url url
	 * @param filePath 指定文件夹
	 */
	public void downFileFromUr(String url, String filePath) {
		DownloadFile downloadFile = new DownloadFile();
		downloadFile.downloadFromUrl(url, filePath);
	}
	
	
	/**
	 * 多个url链接下载
	 * 
	 * @param urlList
	 *            url集合
	 * @param filePath
	 *            指定路径
	 * @param threadNum
	 *            线程数
	 */
	public void downFileFromUrlList(List<String> urlList, String filePath,int threadNum){
		filePath = DownloadUtil.creatFile(filePath);
		//开启默认线程池
		ExecutorService pool = Executors.newFixedThreadPool(threadNum);
		List<String> urls = new ArrayList<String>();
		Future<List<String>> future = null;
		for (String url : urlList) {
			future = pool.submit(new DownFileUrl(url, filePath,urls));
		}
		try {
			List<String> list = future.get();
			if(list != null && list.size()>0){
				//下载失败后再继续下载
				downFileFromUrlList(urls,filePath,threadNum);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.shutdown();
		while(!pool.isTerminated()){}
	}
	
	/**
	 * 多个url连接下载
	 * 
	 * @param urlList
	 *            url集合
	 * @param filePath
	 *            指定文件夹
	 */
	public void downFileFromUrlList(List<String> urlList, String filePath) {
		
		downFileFromUrlList(urlList, filePath,DownloadConstent.THREADNUMBER);
	}

	/**
	 * 
	 * @param urlList
	 *            url集合
	 * @param filePath
	 *            指定文件夹
	 * @param threadNum
	 *            线程数
	 * @return 下载失败的url集合
	 */
	public List<String> downFileZipFromUrlList(List<String> urlList, 
			String filePath,int threadNum) {
		
		filePath = DownloadUtil.creatFile(filePath);
		//定义线程数
		ExecutorService pool = Executors.newFixedThreadPool(threadNum);
		
		ZipOutputStream zos = null;
		//存放下载失败的url
		List<String> urls = new ArrayList<String>();
		
		try {
			zos = new ZipOutputStream(
					new BufferedOutputStream(new FileOutputStream(filePath + DownloadUtil.getZipFilename())));
			
			for (String url : urlList) {
				DownFileZipURl z = new DownFileZipURl(url, zos, urls);
				pool.submit(z);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		pool.shutdown();
		while (!pool.isTerminated()) {
		}
		try {
			if (zos != null) {
				zos.closeEntry();
				zos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return urls;
	}
	
	/**
	 * 
	 * @param urlList
	 *            url集合
	 * @param filePath
	 *            指定文件夹
	 * @return 下载失败的url集合
	 */
	public List<String> downFileZipFromUrlList(List<String> urlList, String filePath){
		
		return downFileZipFromUrlList(urlList, filePath, DownloadConstent.THREADNUMBER);
	}
}
