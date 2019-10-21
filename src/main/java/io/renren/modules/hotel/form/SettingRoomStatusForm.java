package io.renren.modules.hotel.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 设置房态
 * 
 * @author taoz
 *
 */
@Data
public class SettingRoomStatusForm {

	//
	private int status;

	private List<String> dates = new ArrayList<String>();

	private List<Long> moneyIds = new ArrayList<Long>();
	
	private List<Long> roomIds = new ArrayList<Long>();
	
	//1.增加 2.减少 3.设置
	private int numType;
	
	private int num;

}
