package kr.or.nextit.common.file.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.nextit.common.file.model.FileItem;
import kr.or.nextit.common.file.service.FileItemService;
import kr.or.nextit.common.util.ConstantUtil;

@Controller
public class DownloadController{

	@Autowired
	FileItemService fileItemService;
	
	@RequestMapping("/common/download")
	public void process(
			@RequestParam(value="file_seq_no", required = true, defaultValue ="0") int file_seq_no,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 문서형태가 다른 형태로 다른 형태로 다운로드 (ex : html -> jpg 등)
		// 헤더정보 리셋
		try {
			
		response.reset();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("file_seq_no", file_seq_no);
		
		FileItem fileItem = fileItemService.getFileItem(paramMap);
		if(fileItem == null) {
			throw new RuntimeException("해당 파일이 존재하지 않습니다.");
		}
		
		// 응답 헤더 정보 변경.(다운로드 할 때)
		// html이 아니다라는 것을 알려줘야함
		response.setHeader("Content-Type", "application/octet-stream"); // octet : 8비트 >> octet-stream = binary stream
		
		// Content-Disposition
		String fileName = URLEncoder.encode(fileItem.getFile_name(), "utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName +"\";");
		System.out.println(fileItem.getFile_name());
		// Content-Transfer-Encoding
		
		response.setHeader("Content-Transfer-Encoding", "binary"); // 2진 데이터(파일)
		
		// 컨텐트 사이즈
		response.setContentLength((int)fileItem.getFile_size());
		
		// 캐쉬관련
		
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		
		// 파일 전송.
		// 상수처리 한 이유는 여러군데에서 쓰고, 바뀔 수 있기 때문
		File saveFile = new File(ConstantUtil.UPLOAD_PATH + "/" + fileItem.getFile_path() + "/" + fileItem.getFile_save_name());
		
		if(!saveFile.exists()) {
			throw new RuntimeException("해당 파일이 존재하지 않습니다.");
		}
		
		// 응답 데이터로 파일 전송.
		FileUtils.copyFile(saveFile, response.getOutputStream());
		// 자원해제
		response.getOutputStream().close();
		
		
		
		
		}catch(Exception e) {
			// 확인 차원에서만 활용하고 삭제해줘야한다.
			e.printStackTrace();
			response.reset(); // 중간에 헤더의 정보가 남아 있을 수 있기 때문
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404에러
		}
		
		// 리턴값이 null이면 dispatcher가 아무일 하지 않음. 다운로드이기 떄문에 다른 곳으로 이동할 필요가 없다. 
		return;

		
	}

}
