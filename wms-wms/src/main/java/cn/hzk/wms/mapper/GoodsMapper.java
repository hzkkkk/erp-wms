package cn.hzk.wms.mapper;

import cn.hzk.entities.Goods;
import cn.hzk.wms.req.GoodsListREQ;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 物资信息表 Mapper 接口
 * </p>
 *
 * @author 梦学谷-www.mengxuegu.com
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 通过物资id查询物资详情及标签
     * @param id
     * @return
     */
    Goods findGoodsAndLabelById(String id);

    /**
     * 通过物资id删除物资标签表数据
     * @param goodsId
     * @return
     */
    boolean deleteGoodsLabel(@Param("goodsId") String goodsId);

    /**
     * 新增物资标签中间表数据
     * @param goodsId 物资id
     * @param labelIds 标签id集合
     * @return
     */
    boolean saveGoodsLabel(@Param("goodsId") String goodsId,
                             @Param("labelIds") List<String> labelIds);

    /**
     * 通过分类id或标签id查询公开且已审核通过的物资列表
     * @param page 分页对象
     * @param req 条件
     * @return
     */
    IPage<Goods> findListByLabelIdOrCategoryId(IPage<Goods> page,
                                                  @Param("req") GoodsListREQ req);

    /**
     * 统计每个分类下的物资数（调用视图）
     * @return
     */
    List<Map<String, Object>> selectCategoryTotal();

    /**
     * 统计近6个月发布的物资数（调用视图）
     * @return
     */
    List<Map<String, Object>> selectMonthGoodsTotal();
}
