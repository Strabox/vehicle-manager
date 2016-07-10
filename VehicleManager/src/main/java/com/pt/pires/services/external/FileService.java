package com.pt.pires.services.external;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.pt.pires.domain.exceptions.ImpossibleDeleteDirectoryException;
import com.pt.pires.domain.exceptions.ImpossibleSaveFileException;
import com.pt.pires.domain.exceptions.InvalidImageFormatException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.util.FileUtil;

@Service
public class FileService implements IFileService {

	@Override
	public void addOrReplaceVehiclePortraitImage(String vehicleName,byte[] imageBytes) 
			throws ImpossibleSaveFileException, InvalidImageFormatException {
		String imageExtension = FileUtil.isImage(imageBytes);
		if(imageExtension == null) {
			throw new InvalidImageFormatException();
		}
		if(!FileUtil.makeDir(FileUtil.getVehicleFolderPath(vehicleName))) {
			throw new ImpossibleSaveFileException();
		}
		FileUtil.writeFile(FileUtil.getPortraitFilePath(vehicleName), imageBytes);
	}
	
	@Override
	public byte[] getVehiclePortraitImage(String vehicleName) throws VehicleManagerException {
		File vehiclePortrait = new File(FileUtil.getPortraitFilePath(vehicleName));
		 try {
        	return Files.readAllBytes(vehiclePortrait.toPath());
		 }catch(IOException e){
        	Resource resource = new ClassPathResource("/static/images/VehiclePortraitNotFound.png");
        	try {
				return Files.readAllBytes(resource.getFile().toPath());
			} catch (IOException e1) {
				return null;
			}
        }
	}
	
	@Override
	public void addFileToVehicle(String vehicleName,String fileName,byte[] fileBytes) throws ImpossibleSaveFileException {
		if(!FileUtil.makeDir(FileUtil.getVehicleFileFolderPath(vehicleName))) {
			throw new ImpossibleSaveFileException();
		}
		FileUtil.writeFile(FileUtil.getFilePath(vehicleName, fileName), fileBytes);
	}

	@Override
	public void removeVehicleFiles(String vehicleName) throws ImpossibleDeleteDirectoryException {
		if(!FileUtil.removeDirRecursively(FileUtil.getVehicleFolderPath(vehicleName))) {
			throw new ImpossibleDeleteDirectoryException();
		}
	}
	
}
