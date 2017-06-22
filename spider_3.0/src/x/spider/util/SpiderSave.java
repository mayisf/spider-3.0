package x.spider.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 处理线程池中各线程得到的返回值，将结果存储到本地文件中
 * @author Administrator
 *
 */
public class SpiderSave extends Thread{
	
	private List<Future<String>> future_list = null;
	
	private BufferedWriter bw = null;
	
	public SpiderSave(List<Future<String>> future_list, BufferedWriter bw) {
		super();
		this.future_list = future_list;
		this.bw = bw;
	}

	public void run(){
		
		StringBuffer sb = new StringBuffer();
		
		for (Future<String> future : future_list) {
			
			try {
				sb.append(future.get());
			} catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		try {
			bw.write(SpiderUtils.spiderCheck(sb.toString()));
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SpiderUtils.setLog("下载完成");
	}
	
}
