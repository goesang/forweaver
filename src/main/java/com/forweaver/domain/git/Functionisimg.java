// 파일이 이미지 파일인지 검사
package com.forweaver.domain.git;
import java.io.*;
import java.util.*;

public class Functionisimg {
	public static void main(String[] args)  {   

		String filename = ".jpgaaa.aaa";
		FileInfo inputfile = new FileInfo();
		inputfile.setFilename(filename);
		System.out.println(inputfile.isFileWhat());
	}
}
class FileInfo  {
	private String Filename;
	private boolean FileWhat = false;
	FileInfo(){
		Filename="";
		FileWhat=false;

	}
	public boolean isFileWhat() {
		return FileWhat;
	}

	public void setFileWhat(boolean fileWhat) {
		FileWhat = fileWhat;
	}
	public String getFilename() {

		return Filename;
	}

	public void setFilename(String filename) {
                filename = filename.substring(filename.lastIndexOf(".") + 1,filename.length()).toUpperCase();
                if(filename.equalsIgnoreCase("ANI") || filename.equalsIgnoreCase("BMP") || filename.equalsIgnoreCase("CAL")
        				|| filename.equalsIgnoreCase("CAL") || filename.equalsIgnoreCase("FAX") || filename.equalsIgnoreCase("GIF")
        				|| filename.equalsIgnoreCase("IMG") || filename.equalsIgnoreCase("JPE") || filename.equalsIgnoreCase("JPEG")
        				|| filename.equalsIgnoreCase("JPG") || filename.equalsIgnoreCase("MAC") || filename.equalsIgnoreCase("PBM")
        				|| filename.equalsIgnoreCase("PCD") || filename.equalsIgnoreCase("PCX") || filename.equalsIgnoreCase("PCT")
        				|| filename.equalsIgnoreCase("PGM") || filename.equalsIgnoreCase("PNG") || filename.equalsIgnoreCase("PPM")
        				|| filename.equalsIgnoreCase("PSD") || filename.equalsIgnoreCase("RAS") || filename.equalsIgnoreCase("TGA")
        				|| filename.equalsIgnoreCase("TIF") || filename.equalsIgnoreCase("TIFF") || filename.equalsIgnoreCase("WMF")){
        			setFileWhat(true);
        		}
	}
}

