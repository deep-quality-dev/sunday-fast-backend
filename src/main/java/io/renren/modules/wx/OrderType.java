package io.renren.modules.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderType {
	private int type;
	private String formId;

	public OrderType(int type) {
		this.type = type;
	}
}
