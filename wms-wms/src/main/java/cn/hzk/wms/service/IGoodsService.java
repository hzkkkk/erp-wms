package cn.hzk.wms.service;

import cn.hzk.entities.Goods;
import cn.hzk.wms.req.GoodsListREQ;
import cn.hzk.wms.req.GoodsREQ;
import cn.hzk.wms.req.GoodsUserREQ;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.hzk.util.base.Result;
import cn.hzk.util.enums.GoodsStatusEnum;

/**
 * <p>
 * 物资信息表 服务类
 * </p>
 *
 * @author hzk
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 条件分页查询物资列表
     * @param req
     * @return
     */
    Result queryPage(GoodsREQ req);

    /**
     * 通过物资id查询物资详情及标签
     * @param id 物资id
     * @return
     */
    Result findGoodsAndLabelById(String id);

    /**
     * 修改或新增物资
     * @param goods
     * @return
     */
    Result updateOrSave(Goods goods);

    /**
     * 修改状态：
     * @param id 物资id
     * @param statusEnum 状态枚举类
     * @return
     */
    Result updateStatus(String id, GoodsStatusEnum statusEnum);

    /**
     * 根据用户ID查询公开或未公开的物资列表
     * @param req
     * @return
     */
    Result findListByUserId(GoodsUserREQ req);

    /**
     * 根据物资id更新点赞数
     * @param id 物资id
     * @param count 点赞接收+1，取消点赞 -1
     * @return
     */
    Result updateThumhup(String id, int count);

    /**
     * 更新物资浏览次数
     * @param id 物资id
     * @return
     */
    Result updateViewCount(String id);

    /**
     * 通过分类id或标签id查询公开且已审核通过的物资列表
     * @param req 分类id或标签id、分页对象
     * @return
     */
    Result findListByLabelIdOrCategoryId(GoodsListREQ req);

    /**
     * 统计公开且 审核通过的物资数
     * @return
     */
    Result getGoodsTotal();

    /**
     * 统计每个分类下的物资数
     * @return
     */
    Result selectCategoryTotal();

    /**
     * 统计近6个月发布的物资数
     * @return
     */
    Result selectMonthGoodsTotal();
}
