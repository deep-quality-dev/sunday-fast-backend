package io.renren.modules.hotel.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import io.renren.common.utils.R;
import io.renren.modules.hotel.dto.HotelSellerMemberDto;
import io.renren.modules.hotel.entity.HotelMemberLevelDetailEntity;
import io.renren.modules.hotel.service.HotelMemberLevelDetailService;
import io.renren.modules.oss.cloud.OSSFactory;
import io.renren.modules.sys.controller.AbstractController;

/**
 * 会员卡详情
 *
 * @author taoz
 * @email 18819175397@gmail.com
 * @date 2019-08-17 19:46:29
 */
@RestController
@RequestMapping("hotel/hotelmemberleveldetail")
public class HotelMemberLevelDetailController extends AbstractController {
	@Autowired
	private HotelMemberLevelDetailService hotelMemberLevelDetailService;

	@RequestMapping("/registerMember")
	@RequiresPermissions("hotel:hotelmemberleveldetail:registermember")
	public R registerMember(@RequestBody HotelMemberLevelDetailEntity hotelMemberLevelDetail) {
		hotelMemberLevelDetail.setSellerId(getSellerId());
		hotelMemberLevelDetail.setCardNo(DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS") + hotelMemberLevelDetail.getMemberId());
		hotelMemberLevelDetailService.save(hotelMemberLevelDetail);
		return R.ok();
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hotel:hotelmemberleveldetail:list")
	public R list(@RequestParam Map<String, Object> params) {
		params.put("seller_id", 0L);
		if (!isAdmin()) {
			params.put("seller_id", getSellerId());
		}
		IPage<HotelSellerMemberDto> page = hotelMemberLevelDetailService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("hotel:hotelmemberleveldetail:info")
	public R info(@PathVariable("id") Long id) {
		HotelMemberLevelDetailEntity hotelMemberLevelDetail = hotelMemberLevelDetailService.getById(id);

		return R.ok().put("hotelMemberLevelDetail", hotelMemberLevelDetail);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("hotel:hotelmemberleveldetail:save")
	public R save(@RequestBody HotelMemberLevelDetailEntity hotelMemberLevelDetail) {
		hotelMemberLevelDetail.setSellerId(getSellerId());
		hotelMemberLevelDetail.setCreateDate(DateUtil.date());
		hotelMemberLevelDetail.setStatus(1);
		hotelMemberLevelDetail.setCardNo(DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS") + hotelMemberLevelDetail.getMemberId());
		// 生成二维码
		File qrCode = null;
		JSONObject cardInfo = new JSONObject();
		cardInfo.put("sellerId", hotelMemberLevelDetail.getSellerId());
		cardInfo.put("memberId", hotelMemberLevelDetail.getMemberId());
		qrCode = QrCodeUtil.generate(cardInfo.toJSONString(), 300, 300, FileUtil.file(System.getProperty("java.io.tmpdir") + "/" + hotelMemberLevelDetail.getCardNo() + ".jpg"));
		String url = OSSFactory.build().uploadSuffix(getBytes(qrCode.getPath()), ".jpg");
		hotelMemberLevelDetail.setQrCode(url);
		hotelMemberLevelDetailService.save(hotelMemberLevelDetail);
		if (null != qrCode) {
			FileUtil.del(qrCode);
		}

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("hotel:hotelmemberleveldetail:update")
	public R update(@RequestBody HotelMemberLevelDetailEntity hotelMemberLevelDetail) {
		hotelMemberLevelDetailService.updateById(hotelMemberLevelDetail);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("hotel:hotelmemberleveldetail:delete")
	public R delete(@RequestBody Long[] ids) {
		hotelMemberLevelDetailService.removeByIds(Arrays.asList(ids));

		return R.ok();
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
