/**
 * @Auther: taoz
 * @Date: 14/06/2019 12:13
 * @Description:
 */
package io.renren.modules.app.vo;


import lombok.Data;

@Data
public class GoodsVo {

    private Integer id;

    private String img;

    private String name;

    private String price;

    private int saleCount;
}
