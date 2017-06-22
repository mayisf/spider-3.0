package x.spider.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderUtils {
	
	private static List<String> url_list = null;
	
	private static String log = "开始缓存";
	
	private static Document doc = null;
	
	
	/**
	 * 根据目录url，得到各章节url，以列表形式返回
	 * @param url 目录链接
	 * @return
	 */
	public static List<String> spiderList(String url){
		
		url_list = new ArrayList<>();
		
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			setLog("目录链接错误！");
			e.printStackTrace();
		}
		
		Element menu = doc.getElementById("list");
		
		Elements menu_list = menu.getElementsByTag("a");
		
		String head = url.substring(0, url.indexOf("/", 7));
		
		for (Element element : menu_list) {
			
			url_list.add(head+element.attr("href"));
			
		}
		
		return url_list;
		
	}
	
	
	/**
	 * 根据章节地址，缓存章节名称和内容
	 * @param url 章节地址
	 * @return
	 */
	public static String spiderContent(String url){
		
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			setLog(url+"章节连接失败！");
			e.printStackTrace();
		}
		
		String title = doc.title();
		
		title = title.substring(0, title.indexOf("_"));
		
		String temp = doc.getElementById("content").html();
		
		setLog(title+"缓存完成！");
		
		return title+"\r\n"+temp+"\r\n";
		
	}
	
	
	/**
	 * 通过目录链接得到书名
	 * @param url 目录链接
	 * @return
	 */
	public static String spiderBookName(String url){
		
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			setLog(url+"章节连接失败！");
			e.printStackTrace();
		}
		
		String bookName = doc.title();
		
		bookName = bookName.substring(0, bookName.indexOf("最新章节列表"));
		
		return bookName;
		
	}
	
	public static String spiderCheck(String str){
		
		str = str.replace("<br>","");
		
		str = str.replace("<script type=\"text/javascript\" src=\"/js/chaptererror.js\"></script>", "");
		
		str = str.replace("&nbsp;","");
		
		str = str.replace("&nbsp;","");
		
		return str;
	}
	

	public static String getLog() {
		return log;
	}

	public static void setLog(String log) {
		SpiderUtils.log = log;
	}

	
}
