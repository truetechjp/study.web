package jp.truetech.study.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class Controller {
	
	@GetMapping("/help")
	public String help() {
		return "curl -X POST -F file=@/path/to/file http://localhost:8080/upload";
	}
	
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return "no file uploaded";
		}
		
		try (InputStream in = file.getInputStream()){
			String content = getContent(in);
			return content;
		}
		catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private String getContent(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while (true) {
			int c = in.read();
			if (c == -1) {
				break;
			}
			out.write(c);
		}
		out.close();
		return new String(out.toByteArray(), Charset.forName("UTF-8"));
	}
}
