package io.renren.modules.hotel.controller.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.modules.hotel.service.HotelMemberService;
import io.renren.modules.oss.cloud.OSSFactory;
import io.renren.modules.oss.entity.SysOssEntity;
import io.renren.modules.oss.service.SysOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "酒店公共接口", tags = { "酒店公共接口" })
@RestController
@RequestMapping("/hotel/common")
public class CommonAPI {

	@Autowired
	private HotelMemberService hotelMemberService;

	@Autowired
	private SysOssService sysOssService;

	@ApiOperation("发送验证码")
	@GetMapping("/sendSmsCode")
	public R sendSmsCode(String mobile) {
		hotelMemberService.sendSmsCode(mobile);
		return R.ok();
	}

	@ApiOperation("文件上传")
	@PostMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		// 上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

		// 保存文件信息
		SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.save(ossEntity);

		return R.ok().put("url", url);
	}
	
}
