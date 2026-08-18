package com.kh.community.common.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.community.common.interceptor.LoginInterceptor;

/*
 * WebMvcConfigurer: Spring MVC의 공통 설정 구현체(인터페이스)
 * 
 * 	- 업로드된 이미지 매핑
 * 	  : 업로드된 이미지 파일은 src/main/resources/static 폴더가 아닌 별도 경로로 저장될 예정
 * 		-> 특정 주소(uploads)로 요청했을 때 실제 파일이 저장된 경로로 연결
 * 		   (/**: uploads 파일 아래의 모든 파일과 하위 폴더 내의 모든 파일)
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String uploadDir = "uploads";
		String absoluteDir = new File(uploadDir).getAbsolutePath();
		registry.addResourceHandler("/uploads/**")
				.addResourceLocations("file:" + absoluteDir + File.separator);
//		이쯤에서 application.properties에 사용자정의로 파일경로를 설정했고, @Value()는 이후에 추가함

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// InterceptorRegistry는 스프링에게 
		// "내가 만든 인터셉터를 어떤 URL에 적용하고 어떤 URL을 뺄 건지 설정하는 등록부"
		registry.addInterceptor(new LoginInterceptor())	// 인터셉터 등록
				.addPathPatterns("/member/mypage",
								"/member/withdraw",
								"/board/write");
				// 로그인 해야만 접근 가능한 경로 나열
	}

}
