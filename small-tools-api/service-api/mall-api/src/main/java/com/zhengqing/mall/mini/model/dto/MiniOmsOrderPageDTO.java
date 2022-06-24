package com.zhengqing.mall.mini.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.model.dto.BasePageDTO;
import com.zhengqing.common.core.custom.parameter.ParamCheck;
import com.zhengqing.mall.common.model.enums.MiniOmsOrderTabEnum;
import com.zhengqing.mall.common.model.enums.OmsOrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p> 商城-订单分页列表-查询参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商城-订单分页列表-查询参数")
public class MiniOmsOrderPageDTO extends BasePageDTO implements ParamCheck {

    @ApiModelProperty("搜索关键字（订单号或商品名称）")
    private String keyWord;

    /**
     * {@link OmsOrderStatusEnum}
     */
    @JsonIgnore
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(value = "订单状态", hidden = true)
    private Byte orderStatus;

    /**
     * {@link MiniOmsOrderTabEnum}
     */
    @ApiModelProperty("tab条件")
    private Byte tabValue;

    @NotNull(message = "用戶ID不能为空！")
    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;

    @Override
    public void checkParam() {
        if (this.tabValue != null) {
            MiniOmsOrderTabEnum orderTabEnum = MiniOmsOrderTabEnum.getEnum(this.tabValue);
            switch (orderTabEnum) {
                case ALL:
                    this.orderStatus = null;
                    break;
                case UN_PAY:
                    this.orderStatus = OmsOrderStatusEnum.UN_PAY.getStatus();
                    break;
                case UN_BILL:
                    this.orderStatus = OmsOrderStatusEnum.UN_BILL.getStatus();
                    break;
                case BILL:
                    this.orderStatus = OmsOrderStatusEnum.BILL.getStatus();
                    break;
                case FINISH:
                    this.orderStatus = OmsOrderStatusEnum.FINISH.getStatus();
                    break;
                case AFTER_SALE:
                    throw new MyException("查询售后数据请走单独API！");
                default:
                    break;
            }
        }
    }

}
