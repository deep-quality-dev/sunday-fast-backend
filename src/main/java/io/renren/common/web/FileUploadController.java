package io.renren.common.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;

import cn.hutool.core.io.FileUtil;
import io.renren.common.utils.R;
import io.renren.config.FdfsPropertiesConfig;

/**
 * 文件上传
 * 
 * @author taozui
 *
 */
@RestController
public class FileUploadController {

	@Autowired
	private FastFileStorageClient fastFileStorageClient;
	@Autowired
	private FdfsPropertiesConfig fdfsPropertiesConfig;

	/**
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file) {
		String fileExt = FileUtil.extName(file.getOriginalFilename());
		Map<String, String> resultMap = new HashMap<>(1);
		try {
			StorePath storePath = fastFileStorageClient.uploadFile(file.getBytes(), fileExt);
			resultMap.put("filename", fdfsPropertiesConfig.getFileHost() + storePath.getFullPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return R.ok().put("result", resultMap);
	}

	@PostMapping("/ueditorUpload")
	public Map<String, String> ueditorUpload(@RequestParam("file") MultipartFile file) {
//		{
//		    "state": "SUCCESS",
//		    "url": "upload/demo.jpg",
//		    "title": "demo.jpg",
//		    "original": "demo.jpg"
//		}
		Map<String, String> result = new HashMap<String, String>();
		String fileExt = FileUtil.extName(file.getOriginalFilename());
		StorePath storePath = null;
		Map<String, String> resultMap = new HashMap<>(1);
		try {
			storePath = fastFileStorageClient.uploadFile(file.getBytes(), fileExt);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		resultMap.put("state", "SUCCESS");
		resultMap.put("url", fdfsPropertiesConfig.getFileHost() + storePath.getFullPath());
		resultMap.put("title", fdfsPropertiesConfig.getFileHost() + storePath.getFullPath());
		resultMap.put("original", fdfsPropertiesConfig.getFileHost() + storePath.getFullPath());
		return resultMap;
	}
}
