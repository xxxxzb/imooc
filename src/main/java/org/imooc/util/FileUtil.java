package org.imooc.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	/**
	 * 将MultipartFile保存到指定的路径下
	 * @return 保存的文件名，当返回NULL时为保存失败。
	 * @throws IOException
	 * @throws IllegalStateException
	 */	
	public static String save(MultipartFile file,String savePath) throws IllegalStateException, IOException{
		if(file!=null && file.getSize()>0){
			
			File fileFolder = new File(savePath);
			if( !fileFolder.exists()){
				//mkdirs多级目录一并创建
				fileFolder.mkdirs();
			}
			File saveFile = getFile(savePath,file.getOriginalFilename());
			//转存文件
			file.transferTo(saveFile);
			return saveFile.getName();
		}
		return null;	
	}
	
	private static File getFile(String savePath,String originalFilename){
		String fileName = System.currentTimeMillis() + "_" + originalFilename;
		File file = new File(savePath+fileName);
		if(file.exists()){
			return getFile(savePath, originalFilename);
		}
		return file;
	}
	
	public static boolean delete(String filePath){
		File file=new File(filePath);
		if(file.exists()){
			file.delete();
			return true;
		}
		return false;
	}
}
