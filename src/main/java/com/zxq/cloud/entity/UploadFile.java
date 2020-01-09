package com.zxq.cloud.entity;


import com.zxq.cloud.util.NumberUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zxq
 */
@Data
public class UploadFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fileName;
	
	private Long fileSize;

	private String fileSizeCN;
	
	private Date uploadTime;
	
	private String fileId;
	

	public String formatFileSize(Long fileSize) {
		//转成KB  默认单位为B
		BigDecimal fileSize_KB = NumberUtil.divide(String.valueOf(fileSize),"1024",1);
		String fileSizeCN = "";
		if(fileSize_KB.longValue() < 1024/2) {
			fileSizeCN = fileSize_KB.toString() + "KB";
		}else {
			//转换单位：M
			fileSizeCN = NumberUtil.divide(fileSize_KB.toString(),"1024",1) + "M";
		}
		return fileSizeCN;
	}
	
}
