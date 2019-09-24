package io.renren.modules.constants;

public interface HotelOrderStatus {
	// 1未付款,2已付款，3取消,4完成,5已入住,6申请退款,7退款,8拒绝退款
	public static final int UN_PAY = 1;
	public static final int PAYED = 2;
	public static final int CANCEL = 3;
	public static final int COMPLETE = 4;
	public static final int CHECK_IN = 5;
	public static final int APPLY_REFUND = 6;
	public static final int REFUNDED = 7;
	public static final int REFUSE_REFUND = 8;
	public static final int WAIT_CHECK_IN = 9;
	public static final int WAIT_COMMENT = 10;
}
