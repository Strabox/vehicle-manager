package com.pt.pires.services.external;

import java.io.File;
import org.springframework.stereotype.Service;

import com.pt.pires.domain.exceptions.ImpossibleSaveFileException;
import com.pt.pires.util.FileUtil;

@Service
public class FileService implements IFileService{

	@Override
	public void addPortraitImage(String vehicleName,byte[] imageBytes) throws ImpossibleSaveFileException{
		String filePath = FileUtil.ROOT + File.separator + vehicleName;
		String imageExtension = FileUtil.isImage(imageBytes);
		if(imageExtension == null){
			throw new ImpossibleSaveFileException();
		}
		if(!FileUtil.makeDir(filePath)){
			throw new ImpossibleSaveFileException();
		}
		filePath += File.separator + FileUtil.PORTRAIT + "." + imageExtension;
		FileUtil.writeFile(filePath, imageBytes);
	}
	
	@Override
	public void addFileToVehicle(String vehicleName,byte[] file){
		//TODO
	}
	
}
