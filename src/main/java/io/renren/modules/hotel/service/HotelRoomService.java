package io.renren.modules.hotel.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.hotel.entity.HotelMemberLevelEntity;
import io.renren.modules.hotel.entity.HotelRoomEntity;
import io.renren.modules.hotel.entity.HotelRoomMoneyEntity;
import io.renren.modules.hotel.form.SettingRoomStatusForm;
import io.renren.modules.hotel.vo.RoomMoneyVo;
import io.renren.modules.hotel.vo.RoomVO;

/**
 * 房型信息
 *
 * @author taoz
 * @email 18819175397@163.com
 * @date 2019-03-20 12:49:35
 */
public interface HotelRoomService extends IService<HotelRoomEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 酒店房型列表
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @param roomType
	 * @return
	 */
	List<RoomVO> roomList(Long userId, Long sellerId, String startTime, String endTime, int roomType);

	/**
	 * 获取房价
	 * 
	 * @param roomId
	 * @return
	 */
	List<RoomMoneyVo> roomMoneys(HotelMemberLevelEntity memberLevelEntity, List<HotelMemberLevelEntity> hotelMemberLevelEntities, Long roomId, Date startTime, Date endTime);

	/**
	 * 更新房间数量
	 * 
	 * @param hotelRoomEntity
	 * @param hotelRoomMoneyEntity
	 * @param roomNum
	 */
	void updateRoomNum(HotelRoomEntity hotelRoomEntity, HotelRoomMoneyEntity hotelRoomMoneyEntity, Long dateTime, int roomNum);

	/**
	 * 启用房型
	 * 
	 * @param sellerId
	 * @param id
	 */
	void show(Long sellerId, Long id);

	/**
	 * 禁用房型
	 * 
	 * @param sellerId
	 * @param id
	 */
	void hide(Long sellerId, Long id);

	/**
	 * 批量设置房态
	 * @param sellerId
	 * @param settingRoomStatusForms
	 */
	void settingRoomStatusBatch(Long sellerId, SettingRoomStatusForm settingRoomStatusForms);
}
