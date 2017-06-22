package x.spider.daemon;

import java.util.concurrent.Callable;

import x.spider.util.SpiderUtils;

public class SpiderDaemon implements Callable<String>{
	
	private String url;
	
	public SpiderDaemon(String url) {
		
		super();
		
		this.url = url;
		
	}

	@Override
	public String call() throws Exception {
		
		String con = SpiderUtils.spiderContent(url);
		
		return con;
		
	}

}
