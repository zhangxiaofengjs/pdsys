package com.zworks.pdsys.common.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.zworks.pdsys.common.exception.PdsysException;

public class PdSysFileWriter {
	private FileWriter fw;
	private PrintWriter pw;
	
	public void open(String filePath) {
		fw = null;
		try {
			//如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f=new File(filePath);
			fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		pw = new PrintWriter(fw);
	}
	
	public void write(String str, Object... args) {
		pw.write(String.format(str, args));
	}
	
	public void writeLine(String str, Object... args) {
		write(str, args);
		write("\n");
	}
	
	public void close() {
		pw.flush();
		pw.close();
		try {
			fw.close();
		} catch (IOException e) {
			throw new PdsysException(e.getMessage());
		}
	}
}
