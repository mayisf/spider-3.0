package x.spider.util;

import javax.swing.JLabel;

/**
 * 日志系统，读取程序错误然后输出到statusLabel中显示
 * @author Administrator
 *
 */
public class SpiderLog extends Thread{
	
	private JLabel label;

	public SpiderLog(JLabel label) {
		super();
		this.label = label;
		this.setDaemon(true);
	}

	public void run(){
		
		while(true){
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			label.setText(SpiderUtils.getLog());
			
			if(SpiderUtils.getLog().equals("缓存完成！")){
				
			}
		}
	}
}
