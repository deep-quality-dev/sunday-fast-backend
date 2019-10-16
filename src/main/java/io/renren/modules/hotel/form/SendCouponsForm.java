package io.renren.modules.hotel.form;

import java.util.List;

import lombok.Data;

@Data
public class SendCouponsForm {
	private List<Long> memberIds ;
	private List<Long> couponsIds ;
	private int type ;
}
