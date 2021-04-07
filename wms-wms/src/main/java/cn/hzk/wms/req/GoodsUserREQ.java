package cn.hzk.wms.req;

import cn.hzk.entities.Goods;
import cn.hzk.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="GoodsUserREQ对象", description = "获取指定用户物资的查询条件")
public class GoodsUserREQ extends BaseRequest<Goods> {

    @ApiModelProperty(value = "用户ID", required = true)
    private String userId;

    @ApiModelProperty(value = "是否公开（0：不公开，1：公开）", required = true)
    private Integer isPublic; // P是大写

}
