package com.soccer.web.channel.play.controller;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.soccer.web.channel.play.service.PlayService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class VideoManagement {
	
	@Autowired
	private PlayService playservice;
	
	@RequestMapping(value="/form")
	public String form() {
		
		return "form";
	}
	
	@PostMapping(value = "/test")
	public String upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest httpservletreq) {
		System.out.println("##upload");
		System.out.println(multipartFile.getOriginalFilename());
		
		String root_path = httpservletreq.getSession().getServletContext().getRealPath("/");
		String attach_path = "resources/upload/";
	
		
		File targetFile = new File(root_path+attach_path + multipartFile.getOriginalFilename());
		Map<String, String> fileInfo = new HashMap<>();
		fileInfo.put("root", root_path+attach_path);
		fileInfo.put("title", multipartFile.getOriginalFilename());
		playservice.insertVideo(fileInfo);
		InputStream fileStream;
		try {
			fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
		return "test";
	}
	
	@RequestMapping(value="/channel/play")
	public String viewVideo(@RequestParam String path, HttpServletRequest request, HttpServletResponse response ) throws IOException {
		//String movieName = (Strimg)mpa.get("moveiName");
		
		String fileRoot = "/resources/upload/";
		String fileName = "bandicam 2021-05-13 14-38-51-365.mp4";
		
		//progressbar 에서 특정 위치를 클릭하거나 해서 임의의 위치의 내용을 요청할 수 있으므로
		//파일 임의의 위치에서 읽어 오기 위해 RandomAccessFile 클래스를 사용
		//해당 파일이 없을 경우 예외 발생
		RandomAccessFile randomFile = new RandomAccessFile(fileRoot+fileName, "r");
		
		try {
			long rangeStart = 0; //요청 범위의 시작 위치
			long rangeEnd = 0; //요청 범위의 끝 위치
			boolean isPart=false; //부분 요청일 경우 true, 

			//randomFile을 클로즈 하기 위해 try~finally 사용
			long movieSize =randomFile.length(); //동영상 파일의 크기 
			String range = request.getHeader("range"); //스트림 요청 범위, request의 헤더에서 range를 읽는다.
			
			//기본 range 형식 bytes={start}-{end}
			//range가 null, requestStart가 0이고, end가 없을 경우 전체 요청
			if(range!=null) {
				if(range.endsWith("-")) {
					range = range + (movieSize -1 );
				}
				int idxm = range.trim().indexOf("-"); //"-" 위치
				rangeStart = Long.parseLong(range.substring(6, idxm));
				rangeEnd = Long.parseLong(range.substring( idxm+1));
				if(rangeStart>0) {
					isPart=true;
				}
				
			}else { //range가 null인 경우 동영상 전체 크기로 초기값을 넣어줌 
				rangeStart = 0;
				rangeEnd = movieSize-1; //0부터 시작하므로 -1
			}
			//전송 파일 크기
			long partSize = rangeEnd -rangeStart+1;
			
			//전송 시작
			response.reset();
			
			//전체 요청일 경우 200, 부분 요청일 경우 206을 반환상태 코드로 지정
			response.setStatus(isPart? 206: 200);
			
			//mime type
			response.setContentType("video/mp4");
			
			//전송 내용을 헤드에 넣어줌, 마지막에 파일 전체 크기를 넣어줌
			response.setHeader("Content-Range", "bytes "+rangeStart+"-"+rangeEnd+"/"+movieSize);
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-Length",""+partSize);
			
			OutputStream out = response.getOutputStream();
			//동영상 파일의 전송시작 위치 지정
			randomFile.seek(rangeStart);
			
			//파일 전송... java io는 1회 전송 byte수가 int로 지정됨
			//동영상 파일의 경우 int형으로는 처리 안되는 크기의 파일이 있으므로
			//8kb로 잘라서 파일의 크기가 크더라도 문제가 되지 않도록 구현
			int bufferSize=8*1024;
			byte[] buf = new byte[bufferSize];
			
			do {
				int block=partSize>bufferSize ? bufferSize : (int)partSize;
				int len=randomFile.read(buf,0,block);
				out.write(buf, 0, len);
				partSize -= block;
			} while(partSize>0);
	
		}catch(IOException e) {
			
		}finally {
			randomFile.close();
		}
	
		return "channel/play";
	}

}
