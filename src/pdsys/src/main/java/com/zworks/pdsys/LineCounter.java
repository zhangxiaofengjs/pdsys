package com.zworks.pdsys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LineCounter {
	    List<File> list = new ArrayList<File>();
	    public List<String> ignorelist = new ArrayList<String>();
	    int linenumber = 0;
	     
	    FileReader fr = null;
	    BufferedReader br = null;
	 
	    public void counter(String path) {
//	        String path = System.getProperty("user.dir");
	        System.out.println("项目路径"+path);
	        File file = new File(path);
	        File files[] = null;
	        files = file.listFiles();
	        addFile(files);
	        addDirectory(files);
	        readLinePerFile();
	        System.out.println("Totle:" + linenumber + "行");
	    }
	 
	    // 判断是否是目录
	    public void addDirectory(File[] files) {
	        for (File s : files) {
	            if (s.isDirectory()) {
	                File file[] = s.listFiles();
	                addFile(file);
	                addDirectory(file);
	                continue;
	            }
	        }
	    }
	 
	    //将src下所有文件组织成list
	    public void addFile(File file[]) {
	        for (int index = 0; index < file.length; index++) {
	            list.add(file[index]);
	            // System.out.println(list.size());
	        }
	    }
	     
	    //读取非空白行
	    @SuppressWarnings("unlikely-arg-type")
		public void readLinePerFile() {
	        try {
	            for (File s : list) {
	                int yuan = linenumber;
	                if (s.isDirectory()) {
	                    continue;
	                }
	                if(s.getName().lastIndexOf(".java")>0||s.getName().lastIndexOf(".html")>0||s.getName().lastIndexOf(".js")>0||s.getName().lastIndexOf(".css")>0)
	                {
	                	if(ignorelist.indexOf(s.getAbsoluteFile())>=0)
		            		continue;
	                }else{
	                	continue;
	                }
	                fr = new FileReader(s);
	                br = new BufferedReader(fr);
	                String i = "";
	                while ((i = br.readLine()) != null) {
	                    if (isBlankLine(i))
	                        linenumber++;
	                }
	                System.out.print(s.getAbsolutePath());
	                System.out.println("\t" + (linenumber - yuan));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (Exception e) {
	                }
	            }
	            if (fr != null) {
	                try {
	                    fr.close();
	                } catch (Exception e) {
	                }
	            }
	        }
	    }
	 
	    //是否是空行
	    public boolean isBlankLine(String i) {
	        if (i.trim().length() == 0) {
	            return false;
	        } else {
	            return true;
	        }
	    }
	     
	    public static void main(String args[]) {
	        LineCounter lc = new LineCounter();
	        //lc.ignorelist.add("D:\\dev\\pdsys\\src\\mian\\java");
	        lc.counter("D:\\dev\\pdsys\\src"); //$NON-NLS-1$
	    }
}
