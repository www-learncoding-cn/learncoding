package cn.learncoding.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author learncoding.cn
 */
@Controller
@RequestMapping("/file")
public class FileController {
	
	private static final String FILE_PATH = File.separator + "file";

	@PostMapping("/upload")
	public ResponseEntity<?> fileUpload(MultipartFile file, HttpSession session) {
		String dirPathUrl = session.getServletContext().getRealPath(FILE_PATH);
		Path dirPath = Paths.get(dirPathUrl);
		String name = UUID.randomUUID().toString().replace("-", "") 
				+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		Path filePath = Paths.get(dirPathUrl, name);
		try {
			if (!Files.exists(dirPath)) {
				Files.createDirectories(dirPath);
			}
			file.transferTo(filePath);
		} catch (Exception e) {
			throw new RuntimeException(file.getOriginalFilename() + "文件上传时，保存失败",e);
		}
		return new ResponseEntity<>(FILE_PATH + File.separator + name, HttpStatus.OK);
	}
	
	@GetMapping("/download")
	public ResponseEntity<?> fileDownload(String filePath, HttpSession session) {
		String rootPath = session.getServletContext().getRealPath(File.separator);
		Path path = Paths.get(rootPath, filePath);
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
		HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名字乱码问题
        String downloadFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        //byte数组方式下载，不直接显示内容，弹出一个”文件下载”的对话框
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //确保浏览器弹出下载对话框
        headers.setContentDispositionFormData("attachment", downloadFileName);
		try {
			return new ResponseEntity<>(Files.readAllBytes(path),headers,HttpStatus.OK);
		} catch (IOException e) {
			throw new RuntimeException(filePath + "文件下载时，读取失败",e);
		}
	}
}
