package com.jiaoyushop.controlller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jiaoyushop.util.FastDFSClient;
import com.jiaoyushop.util.JsonUtils;

/**
 * 图片上传
 * @author xuyisheng
 *
 */
@Controller
public class PictureController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		try {
			//取文件的扩展名
			String filename = uploadFile.getOriginalFilename();
			String extName = filename.substring(filename.lastIndexOf(".")+1);
			//2.创建一个fastdfs的客户端
			FastDFSClient fstclient=new FastDFSClient("classpath:client.conf");
			//3.执行上传处理
			String path = fstclient.uploadFile(uploadFile.getBytes(),extName);
			//拼接返回的url和ip地址，拼装成完整的url
			String url=IMAGE_SERVER_URL+path;
			Map result=new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
			
		}catch(Exception e) {
			e.printStackTrace();
			Map result=new HashMap<>();
			result.put("error", 1);
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
			
		}
	
		
	}

}
