package com.pt.pires.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.util.FileCopyUtils;

import com.pt.pires.domain.exceptions.ImpossibleSaveFileException;

public final class FileUtil {

	private static final String ROOT = "files-dir";
	
	private static final String PORTRAIT = "portrait";
	
	private static final String FILES_FOLDER = "files";
	
	public static final String getRootPath(){
		return ROOT;
	}
	
	public static final String getPortraitFilePath(String vehicleName){
		return ROOT + File.separator + vehicleName + File.separator + PORTRAIT;
	}
	
	public static final String getFilePath(String vehicleName,String fileName){
		return ROOT + File.separator + vehicleName + File.separator + FILES_FOLDER
				+ File.separator + fileName;
	}
	
	public static final String getVehicleFolderPath(String vehicleName){
		return ROOT + File.separator + vehicleName;
	}
	
	public static final String getVehicleFileFolderPath(String vehicleName){
		return ROOT + File.separator + vehicleName + File.separator + FILES_FOLDER;
	}
	
	/**
	 * Create a directory if it doesn't exist.
	 * @param dirPath Directory path.
	 * @return True if directory was created, False otherwise.
	 */
	public static boolean makeDir(String dirPath){
		File dir = new File(dirPath);
		if((dir.exists() && !dir.isDirectory()) || !dir.exists()){
			return dir.mkdir();
		}
		return true;
	}
	
	/**
	 * Remove directory and all the files and folders inside recursively.
	 * @param dirPath Directory path
	 * @return True if directory and its content was removed, False otherwise.
	 */
	public static boolean removeDirRecursively(String dirPath){
		File dir = new File(dirPath);
		if(!dir.exists() || !dir.isDirectory()){
			return true;
		}
		try {
			FileUtils.deleteDirectory(new File(dirPath));
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Write a byte array to a specified file
	 * @param filePath File path
	 * @param fileBytes File content
	 * @throws ImpossibleSaveFileException Error writing to the file.
	 */
	public static void writeFile(String filePath,byte[] fileBytes) throws ImpossibleSaveFileException{
		try{
			File file = new File(filePath);
			FileOutputStream fileOs = new FileOutputStream(file);
			BufferedOutputStream oStream = new BufferedOutputStream(fileOs);
			FileCopyUtils.copy(fileBytes, oStream);
			oStream.close();
		}catch(FileNotFoundException e){
			throw new ImpossibleSaveFileException();
		} catch (IOException e) {
			try {
				Files.deleteIfExists(Paths.get(filePath));
			} catch (IOException e1) {
				throw new ImpossibleSaveFileException();
			}
			throw new ImpossibleSaveFileException();
		}
	}
	
	/**
	 * Read file's content.
	 * @param filePath File path
	 * @return File content
	 */
	public static byte[] readFile(String filePath){
		try {
			return Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Verify if file provided is a image (.JPG .PNG etc).
	 * It uses org.apache.Tika library.
	 * @param file
	 * @return Image extension (JPEG , PNG etc) if not ant image
	 * return null
	 */
	public static String isImage(byte[] file){
		Tika tika = new Tika();
		String mediaType =  tika.detect(file);
		String[] res = mediaType.split("/");
		if(res[0].equals("image")){
			return res[1];
		}
		return null;
	}
}
