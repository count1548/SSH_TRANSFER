import java.io.*;

public class findFile {
	File dir; 
	File[] fileList;
	int fileCount = 0;
	
	public findFile(String source){
		dir = new File(source);
		fileList = dir.listFiles();
		fileCount = fileList.length;
	}
	String[] folderList;
	public String[] getFolderList() {
		folderList = new String[fileList.length];
		for(int i = 0 ; i < fileList.length ; i++) {
			if(fileList[i].isFile()) folderList[i] = "f-";
			else folderList[i] = "d-";
			folderList[i] += fileList[i].getName();
		}
		return folderList;
	}
	public int getFileCount() { return fileCount; }
}
