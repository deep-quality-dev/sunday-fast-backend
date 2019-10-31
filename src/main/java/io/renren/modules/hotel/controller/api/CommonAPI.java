package io.renren.modules.hotel.controller.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONObject;
import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.modules.constants.CommonConstant;
import io.renren.modules.hotel.config.WxMaConfiguration;
import io.renren.modules.hotel.service.HotelMemberService;
import io.renren.modules.hotel.ws.NotificationServer;
import io.renren.modules.oss.cloud.OSSFactory;
import io.renren.modules.oss.entity.SysOssEntity;
import io.renren.modules.oss.service.SysOssService;
import io.renren.modules.sys.service.SysCaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.error.WxErrorException;

@Api(value = "酒店公共接口", tags = { "酒店公共接口" })
@RestController
@RequestMapping("/hotel/common")
public class CommonAPI {

	@Autowired
	private HotelMemberService hotelMemberService;

	@Autowired
	private SysCaptchaService sysCaptchaService;

	@Autowired
	private SysOssService sysOssService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@GetMapping("/testWs")
	@SneakyThrows
	public R testWs(String id) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("orderId", 1);
		NotificationServer.sendInfo(jsonObject.toString(), id);
		return R.ok();
	}

	@ApiOperation("发送验证码")
	@GetMapping("/sendSmsCode")
	public R sendSmsCode(String mobile) {
		ThreadUtil.execute(new Runnable() {
			@Override
			public void run() {
				hotelMemberService.sendSmsCode(mobile);
			}
		});
		return R.ok();
	}

	@ApiOperation("发送验证码")
	@GetMapping("/smsCode")
	public R sendSmsCode(String mobile, String uuid, String captchaCode) {
		boolean captcha = sysCaptchaService.validate(uuid, captchaCode);
		if (!captcha) {
			return R.error("验证码不正确");
		}
		ThreadUtil.execute(new Runnable() {
			@Override
			public void run() {
				hotelMemberService.sendSmsCode(mobile);
			}
		});

		return R.ok();
	}

	@ApiOperation("校验短信验证码")
	@GetMapping("/checkSmsCode")
	public R checkSmsCode(String mobile, String vcode) {
		Object tempCode = redisTemplate.opsForValue().get(CommonConstant.DEFAULT_CODE_KEY + mobile);
		if (tempCode == null || !vcode.equals(tempCode.toString())) {
			throw new RRException("验证码错误");
		}
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

	@ApiOperation("获取小程序码")
	@GetMapping("/{appid}/maQrCode")
	@SneakyThrows
	public R maQrCode(@PathVariable String appid, String path) throws WxErrorException {
		final WxMaService wxService = WxMaConfiguration.getMaService(appid);
		File file = wxService.getQrcodeService().createWxaCode(path);
		String url = OSSFactory.build().uploadSuffix(getBytes(file.getPath()), ".jpg");
		return R.ok().put("data", url);
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
