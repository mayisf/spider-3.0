package x.spider.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderData {

	public static List<Object[]>  data(String key) throws IOException {
		
		List<Object[]> list = new ArrayList<>();
		
		Document doc = Jsoup.connect("http://zhannei.baidu.com/cse/search?s=1393206249994657467&q="+key).get();
		
		Elements el = doc.getElementsByClass("result-game-item-detail");
		
		for (Element element : el) {
			
			list.add(data(element));
			
		}
		
		return list;
		
	}
	
	private static Object[] data(Element e){
		
		List<String> list = new ArrayList<>();

		Elements b = e.getElementsByTag("span");
		
		Element el = e.getElementsByTag("a").get(0);
		
		list.add(el.attr("href"));

		list.add(el.attr("title"));
		
		for (int i=0;i<b.size();i++) {
			
			if(i==1||i==3||i==5){
				
				String temp = b.get(i).text();
				
				list.add(temp);
			}
			
		}
		
		return list.toArray();
		
	}
	
}
