package io.renren.modules.hotel.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;
import io.renren.modules.hotel.service.HotelMgtService;
import io.renren.modules.oss.cloud.OSSFactory;

@Service
public class HotelMgtServiceImpl implements HotelMgtService {

	@Autowired
	private HotelMemberLevelDetailService hotelMemberLevelDetailService;

	@Transactional
	@Override
	public void mgrCard() {
		List<HotelMemberLevelDetailEntity> hotelMemberLevelDetailEntitys = hotelMemberLevelDetailService.list();
		File qrCode = null;
		for (HotelMemberLevelDetailEntity hotelMemberLevelDetailEntity : hotelMemberLevelDetailEntitys) {
			JSONObject cardInfo = new JSONObject();
			cardInfo.put("sellerId", hotelMemberLevelDetailEntity.getSellerId());
			cardInfo.put("memberId", hotelMemberLevelDetailEntity.getMemberId());
			qrCode = QrCodeUtil.generate(cardInfo.toJSONString(), 300, 300, FileUtil.file(System.getProperty("java.io.tmpdir") + "/" + hotelMemberLevelDetailEntity.getCardNo() + ".jpg"));
			String url = OSSFactory.build().uploadSuffix(getBytes(qrCode.getPath()), ".jpg");
			hotelMemberLevelDetailEntity.setQrCode(url);
			hotelMemberLevelDetailService.updateById(hotelMemberLevelDetailEntity);
			if (null != qrCode) {
				FileUtil.del(qrCode);
			}

		}
	}

	private byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
}
