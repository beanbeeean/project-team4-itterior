package com.office.house.user.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class UploadFileService {

	public String upload(String u_id, MultipartFile file) {
		System.out.println("[UploadFileService] upload()");
		
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
			System.out.println("[UploadFileService] FILE UPLOAD SUCCESS!!");
			
			return uniqueName + fileExtension;
			
		} else {
			System.out.println("[UploadFileService] FILE UPLOAD FAIL!!");
			
			return null;
			
		}
		
	}

	public String uploadBoard(String u_id, MultipartFile file) {
		System.out.println("[UploadFileService] uploadBoard()");

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
			System.out.println("[UploadFileService] FILE UPLOAD SUCCESS!!");

			return uniqueName + fileExtension;

		} else {
			System.out.println("[UploadFileService] FILE UPLOAD FAIL!!");

			return null;

		}

	}

	public String uploadBoardThumbnail(String u_id, MultipartFile file) {
		System.out.println("[UploadFileService] upload()");

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
			System.out.println("[UploadFileService] FILE UPLOAD SUCCESS!!");

			return uniqueName + fileExtension;

		} else {
			System.out.println("[UploadFileService] FILE UPLOAD FAIL!!");

			return null;

		}

	}
	
}
