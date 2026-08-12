package com.kh.community.common.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/*
 * MultipartFile로 전달된 파일을 서버 디스크에 저장하는 작업
 * - 이미지 업로드 흐름
 * 	1) 브라우저에서 파일을 전송 (form 태그 내에 enctype="multipart/form-data"를 반드시 추가!)
 * 	2) 스프링MVC에서 객체로 받아서 컨트롤러로 전달
 *  3) FileUploadUtil 클래스가 실제 바이트를 특정 폴더(uploads/ 폴더로 지정)에 파일로 저장
 *  4) DB에 파일이 저장된 경로만 저장 (DB에 저장된 경로를 기준으로 파일을 요청)
 *  	=> 파일명을 업로드된 그대로 저장하지 않음!
 */

@Component // Bean으로 등록
public class FileUploadUtil {
	
	/**
	 * 파일 저장 후 경로를 반환하는 메서드
	 * @param file 파일 데이터
	 * @param uploadDir 파일 업로드 경로
	 * @param webPrefix 웹 요청 주소: 실질적으로 DB에 저장될 주소는 얘라고 보면 됨
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public SavedFile save(MultipartFile file, String uploadDir, String webPrefix) 
			throws IllegalStateException, IOException {
		if (file == null || file.isEmpty()) {
			return null;
		}
		
		// 원본 파일명 추출 (파일명 + 확장자)
		String originalName = file.getOriginalFilename();
		
		// 확장자만 따로 추출
		int dotIndex = originalName.lastIndexOf(".");
		String ext = "";
		if (dotIndex > -1) {
			ext = originalName.substring(dotIndex);
		}
		
		// 저장할 파일명을 임의로 변경
		// UUID : 122 비트의 무작위 값으로 겹치지 않게 만들어주는 객체
		String saveName = UUID.randomUUID() + ext;
		
		// File 객체를 사용하여 업로드할 경로 확인
		File dir  = new File(uploadDir).getAbsoluteFile();
		// getAbsoluteFile(): 절대경로를 얻어올 수 있는 메서드
		
		// 해당 경로가 없으면 폴더를 생성 exists(): 있으면 true, 없으면 false를 반환하는 메서드
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File target = new File(dir, saveName);
		// dir(parent) 파일경로에 saveName(child)이라는 이름의 파일을 생성하겠다는 뜻
		file.transferTo(target);  // MultipartFile 형태로 전달 받은 파일을 실제 target 정보로 저장
		
		String path = webPrefix + "/" + saveName ; // 저장된 위치를 의미함
		return new SavedFile(originalName, saveName, path);
	}
	
}
