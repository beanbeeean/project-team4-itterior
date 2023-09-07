package com.office.house.util.upload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class UploadFileService {

	public String upload(String u_id, MultipartFile file) {

		
		boolean result = false;
		
		// File 저장
		String fileOriName = file.getOriginalFilename();
		String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());
		String uploadDir = "c:\\semipjt\\upload\\" + u_id;
		
		UUID uuid = UUID.randomUUID();
		String uniqueName = uuid.toString().replaceAll("-", "");
		
		File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);
		if (!saveFile.exists())
			saveFile.mkdirs();
		
		try {
			
			file.transferTo(saveFile);
			result = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		if (result) {

			
			return uniqueName + fileExtension;
			
		} else {

			
			return null;
			
		}
		
	}

	public String uploadBoard(String u_id, MultipartFile file) {


		boolean result = false;

		// File 저장
		String fileOriName = file.getOriginalFilename();
		String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());
		String uploadDir = "c:\\semipjt\\upload\\board\\" + u_id ;

		UUID uuid = UUID.randomUUID();
		String uniqueName = uuid.toString().replaceAll("-", "");

		File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);
		if (!saveFile.exists())
			saveFile.mkdirs();

		try {

			file.transferTo(saveFile);
			result = true;

		} catch (Exception e) {
			e.printStackTrace();

		}

		if (result) {


			return uniqueName + fileExtension;

		} else {


			return null;

		}

	}

	public String uploadBoardThumbnail(String u_id, MultipartFile file) {


		boolean result = false;

		// File 저장
		String fileOriName = file.getOriginalFilename();
		String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());
		String uploadDir = "c:\\semipjt\\upload\\thumbnail\\" + u_id ;

		UUID uuid = UUID.randomUUID();
		String uniqueName = uuid.toString().replaceAll("-", "");

		File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);
		if (!saveFile.exists())
			saveFile.mkdirs();

		try {

			file.transferTo(saveFile);
			result = true;

		} catch (Exception e) {
			e.printStackTrace();

		}

		if (result) {

			return uniqueName + fileExtension;

		} else {


			return null;

		}

	}
	
}
