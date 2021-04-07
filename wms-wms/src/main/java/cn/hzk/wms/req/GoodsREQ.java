package cn.hzk.wms.req;

import cn.hzk.entities.Goods;
import cn.hzk.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "GoodsREQ对象", description = "物资查询条件")
public class GoodsREQ extends BaseRequest<Goods> {

    @ApiModelProperty(value = "物资名称")
    private String title;

    @ApiModelProperty(value = "状态（0: 已删除, 1:可借出，2:已借出，3：已损坏）")
    private Integer status;

}
