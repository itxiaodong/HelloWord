package execption;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * TODO 多链接下载自定义异常
 * @ClassName MyException
 * @author 董景帅
 * @date 2015-12-23 下午4:11:13
 *
 */
public class MyException {
	private Exception e;
	
	public MyException(Exception e) {
		super();
		this.e = e;
	}
	public Exception getE() {
		return e;
	}

	public void process(Exception e,String str) {
		String message = e.getMessage();
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("src/1.txt", true)));
			bw.write(str+":"+message+"  "
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			bw.newLine();
			bw.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
