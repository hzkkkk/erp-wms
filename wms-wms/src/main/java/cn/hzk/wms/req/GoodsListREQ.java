package cn.hzk.wms.req;

import cn.hzk.entities.Goods;
import cn.hzk.util.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 物资列表查询条件
 */
@Data
@Accessors(chain = true)
@ApiModel(value="GoodsListREQ对象", description = "物资列表查询条件")
public class GoodsListREQ extends BaseRequest<Goods> {

    @ApiModelProperty(value = "分类ID")
    private String categoryId;

    @ApiModelProperty(value = "标签ID")
    private String labelId;
}
