package x.spider.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import x.spider.util.SpiderData;
import x.spider.util.SpiderService;

public class SpiderUI implements ActionListener{
	
	private JFrame frame;
	private JPanel topPanel;
	private JPanel center;
	private JPanel bottomPanel;
	
	private JButton search;
	private JTextField searchField;
	
	private JButton download;
	private JButton pathButton;
	private String path = "";
	private JTextField downloadField;
	
	private String url = "";
	private List<Object[]> list;
	
	private SpiderService ss;
	
	private DefaultTableModel tableModel;
	private JTable table;
	
	public static void main(String[] args) throws IOException{
		SpiderUI spider = new SpiderUI();
		
		spider.initUI();
		
		spider.initTop(spider.topPanel);
		
		spider.initBottom(spider.bottomPanel);
		
		spider.initFrame(spider.frame);
	}
	
	public void initUI(){
		
		ss = new SpiderService();
		
		frame = new JFrame("Spider 3.0");
		topPanel = new JPanel();
		center = new JPanel(new BorderLayout());
		bottomPanel = new JPanel();
		
		frame.setLayout(new BorderLayout());
		Container cont = frame.getContentPane();
		cont.add(topPanel, BorderLayout.NORTH);
		cont.add(center,BorderLayout.CENTER);
		cont.add(bottomPanel,BorderLayout.SOUTH);
		
	}
	
	public void initFrame(JFrame frame){
		
		frame.setLocation(200, 200);
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void initTop(JPanel panel){
		
		searchField = new JTextField(14);
		
		search = new JButton("搜索");
		search.addActionListener(this);
		
		panel.add(searchField);
		panel.add(search);
		
	}
	
	public void initCenter(JPanel panel,String key) throws IOException{
		if(table!=null){
			table.removeAll();
		}
		
		list = SpiderData.data(key);
		
		Object[][] tableData = new Object[10][];
		
		for(int i=0;i<10;i++){
			
			tableData[i] = (Object[]) list.get(i); 
			
			tableData[i][0] = i;
		}
		
		Object[] columnTitle = {"代号","书名","作者","类别","更新时间"};
		
		tableModel = new DefaultTableModel(tableData, columnTitle);
		
		table = new JTable(tableModel);
		
		table.setFont(new Font("Menu.Font", Font.PLAIN, 12));
		
		table.setRowHeight(15);
		
		JScrollPane jsp = new JScrollPane(table);
		
		panel.add(jsp, BorderLayout.CENTER);
		
		list = SpiderData.data(key);
		
	}
	
	public void initBottom(JPanel panel){
		
		downloadField = new JTextField(3);
		
		pathButton = new JButton("保存路径");
		pathButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showDialog(null, null);
				File fi = jfc.getSelectedFile();
				path = fi.getAbsolutePath();
				
			}
		});
		
		
		download = new JButton("下载");
		download.addActionListener(this);
		
		JLabel label = new JLabel("代号：");
		
		panel.add(pathButton);
		panel.add(label);
		panel.add(downloadField);
		panel.add(download);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == search){
			String text = searchField.getText();
			if(text.equals("")){
				return;
			}
			
			try {
				initCenter(center, text);
				center.validate();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		}else if(e.getSource() == download){
			url = (String) list.get(Integer.parseInt(downloadField.getText()))[0];
			if(path.equals("")||url.equals("")){
				return ;
			}
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					try {
						ss.spider(path, url);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			}).start();
			
		}
		
	}
	
}
