package com.zxq.cloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxq.cloud.constant.GlobalConstant;
import com.zxq.cloud.entity.UploadFile;
import com.zxq.cloud.service.FastDFSClient;
import com.zxq.cloud.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author zxq
 */
@RestController
@RequestMapping("/file")
public class FileController {
	
	@Resource
	private RedisService redisService;
	
	@Resource
	private FastDFSClient fastDFSClient;
	
	/**
	 * 上传文件到fastdfs服务器
     *
	 * @param request
     * @return
     */
	@PostMapping(value = "/uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFileServer(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "0");
        
		MultipartFile file = null;
		/**检验请求格式是否合法*/
    	if(request instanceof MultipartHttpServletRequest){
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    		file = multipartRequest.getFile("file");
    	}else{
    		resultMap.put("status", "-1");
    		resultMap.put("msg", "请求参数不合法");
    		return resultMap;
    	}
    	
    	/**文件是否存在*/
    	if(file == null || file.isEmpty()){
    		resultMap.put("status", "-1");
    		resultMap.put("msg", "文件不存在");
		}else {
			String fileName = file.getOriginalFilename();//获取文件名称
			if(redisService.hHasKey(GlobalConstant.REDIS_FASTDFS_KEY, fileName)) {
				resultMap.put("status", "-1");
	    		resultMap.put("msg", "文件名已存在");
	    		System.out.println("上传文件至fastdfs失败，文件重复");
			}else {
				try {
					String uploadFilePath = fastDFSClient.uploadFile(file);
					System.out.println("上传文件至fastdfs成功，fileId="+uploadFilePath);
					//将生成的文件信息保存在redis中
					UploadFile uploadFile = new UploadFile();
					uploadFile.setFileId(uploadFilePath);
					uploadFile.setFileName(fileName);
					uploadFile.setUploadTime(new Date());
					uploadFile.setFileSize(file.getSize());
					uploadFile.setFileSizeCN(uploadFile.formatFileSize(file.getSize()));
					redisService.hset(GlobalConstant.REDIS_FASTDFS_KEY, fileName, uploadFile);
					resultMap.put("fileId", uploadFilePath);
				} catch (Exception e) {
					e.printStackTrace();
					resultMap.put("status", "-1");
		    		resultMap.put("msg", e.getLocalizedMessage());
				}
			}
		}
    	return resultMap;
	}
	
	/**
	 * 查询已上传的文件列表
	 * @return
	 */
	@RequestMapping(value = "/queryUploadFiles")
	@ResponseBody
	public JSONObject queryUploadFiles() {
		JSONObject res = new JSONObject();
		List<JSONObject> files = new LinkedList<>();
		//从redis查询已经缓存过的文件
		Map<Object, Object> map = redisService.hmget(GlobalConstant.REDIS_FASTDFS_KEY);
		Set<Object> keySet = map.keySet();
		Iterator<Object> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			UploadFile uploadFile = (UploadFile) map.get(iterator.next());
			JSONObject file = (JSONObject)JSON.toJSON(uploadFile);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			file.put("uploadTime", sdf.format(uploadFile.getUploadTime()));
			files.add(file);
		}
		res.put("code", 0);
		res.put("data", files);
		return res;
	}
	
	/**
	 * 删除文件
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public JSONObject delete(String fileId,String fileName,String token) {
		JSONObject res = new JSONObject();
		String TOKEN = (String)redisService.get("TOKEN");
		if(StringUtils.isEmpty(token) || !token.equals(TOKEN)) {
			res.put("code", 1);
			res.put("msg", "TOKEN验证失败");
			return res;
		}
		try {
			fastDFSClient.deleteFile(fileId);
			redisService.hdel(GlobalConstant.REDIS_FASTDFS_KEY, fileName);
			res.put("code",0);
		} catch (Exception e) {
			e.printStackTrace();
			res.put("code", 1);
			res.put("msg", "删除失败");
		}
		return res;
	}
	
}
