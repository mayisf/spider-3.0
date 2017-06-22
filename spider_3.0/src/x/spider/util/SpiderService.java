package x.spider.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import x.spider.daemon.SpiderDaemon;

public class SpiderService {

	public void spider(String path,String url) throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(path+"/"+SpiderUtils.spiderBookName(url)+".txt"));
		
		ExecutorService es = Executors.newFixedThreadPool(25);
		
		List<String> menu_list = SpiderUtils.spiderList(url);
		
		List<Future<String>> future_list = new ArrayList<>();
		
		for (String menu : menu_list) {
			
			SpiderDaemon sd = new SpiderDaemon(menu);
			
			Future<String> future = es.submit(sd);
			
			future_list.add(future);
			
		}
		
		new SpiderSave(future_list, bw).start();
		
		es.shutdown();
		
		while(true){
			
			if(es.isTerminated()){
				
				break;
				
			}
			
		}
		
	}
	
}
