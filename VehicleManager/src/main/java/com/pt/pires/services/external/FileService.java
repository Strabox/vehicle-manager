package com.pt.pires.services.external;

import org.springframework.stereotype.Service;

import com.pt.pires.domain.exceptions.ImpossibleDeleteDirectoryException;
import com.pt.pires.domain.exceptions.ImpossibleSaveFileException;
import com.pt.pires.util.FileUtil;

@Service
public class FileService implements IFileService{

	@Override
	public void addPortraitImage(String vehicleName,byte[] imageBytes) throws ImpossibleSaveFileException{
		String imageExtension = FileUtil.isImage(imageBytes);
		if(imageExtension == null){
			throw new ImpossibleSaveFileException();
		}
		if(!FileUtil.makeDir(FileUtil.getVehicleFolderPath(vehicleName))){
			throw new ImpossibleSaveFileException();
		}
		FileUtil.writeFile(FileUtil.getPortraitFilePath(vehicleName), imageBytes);
	}
	
	@Override
	public void addFileToVehicle(String vehicleName,String fileName,byte[] fileBytes) throws ImpossibleSaveFileException{
		if(!FileUtil.makeDir(FileUtil.getVehicleFileFolderPath(vehicleName))){
			throw new ImpossibleSaveFileException();
		}
		FileUtil.writeFile(FileUtil.getFilePath(vehicleName, fileName), fileBytes);
	}

	@Override
	public void removeVehicleFiles(String vehicleName) throws ImpossibleDeleteDirectoryException {
		if(!FileUtil.removeDirRecursively(FileUtil.getVehicleFolderPath(vehicleName))){
			throw new ImpossibleDeleteDirectoryException();
		}
	}
	
}
