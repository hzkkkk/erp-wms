package cn.hzk.wms.service.impl;

import cn.hzk.entities.Goods;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.hzk.wms.req.GoodsListREQ;
import cn.hzk.wms.req.GoodsREQ;
import cn.hzk.wms.req.GoodsUserREQ;
import cn.hzk.wms.mapper.GoodsMapper;
import cn.hzk.wms.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hzk.util.base.Result;
import cn.hzk.util.enums.GoodsStatusEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 物资信息表 服务实现类
 * </p>
 *
 * @author hzk
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Override
    public Result queryPage(GoodsREQ req) {
        QueryWrapper<Goods> wrapper = new QueryWrapper();
        // 标题
        if(StringUtils.isNotEmpty(req.getTitle())) {
            wrapper.like("title", req.getTitle());
        }
        // 状态
        if(req.getStatus() != null) {
            wrapper.eq("status", req.getStatus());
        }
        wrapper.orderByDesc("update_date");
        IPage<Goods> page = baseMapper.selectPage(req.getPage(), wrapper);
        return Result.ok(page);
    }

    @Override
    public Result findGoodsAndLabelById(String id) {
        return Result.ok( baseMapper.findGoodsAndLabelById(id) );
    }

    @Transactional // 事务管理
    @Override
    public Result updateOrSave(Goods goods) {
        // 1. 如果id不为空，则是更新操作
        if(StringUtils.isNotEmpty(goods.getId())) {
            // 更新：先删除物资中间表数据，再新增到中间表
            baseMapper.deleteGoodsLabel(goods.getId());
            // 更新：将更新时间设置当前时间
            goods.setUpdateDate(new Date());
        }

        // 如果物资是ispublic不公开的，直接可借出(status)，否则等待审核
        if(goods.getIspublic() == 0) { // 0：不公开，1：公开
            goods.setStatus(GoodsStatusEnum.SUCCESS.getCode()); // 0: 已删除, 1:已借出，2:可借出，3：审核未通过
        }else {
            goods.setStatus(GoodsStatusEnum.WAIT.getCode());
        }

        // 更新或保存物资信息（新增数据后，会将这条新增数据的主键id值放到id属性中）
        super.saveOrUpdate(goods);

        // 新增标签数据到物资标签中间表中
        if(CollectionUtils.isNotEmpty(goods.getLabelIds())) {
            baseMapper.saveGoodsLabel(goods.getId(), goods.getLabelIds());
        }

        // 返回物资id
        return Result.ok(goods.getId());
    }

    @Override
    public Result updateStatus(String id, GoodsStatusEnum statusEnum) {
        // 先查询当前数据库的数据
        Goods goods = baseMapper.selectById(id);
        // 将状态值 更新
        goods.setStatus(statusEnum.getCode());
        goods.setUpdateDate(new Date());
        baseMapper.updateById(goods);
        return Result.ok();
    }

    @Override
    public Result findListByUserId(GoodsUserREQ req) {
        if(StringUtils.isEmpty(req.getUserId())) {
            return Result.error("无效用户信息");
        }

        QueryWrapper<Goods> wrapper = new QueryWrapper();
        wrapper.eq("user_id", req.getUserId());

        if(req.getIsPublic() != null) {
            // ispublic 字段名都是小写
            wrapper.eq("ispublic", req.getIsPublic());
        }

        // 排序
        wrapper.orderByDesc("update_date");

        IPage<Goods> page = baseMapper.selectPage(req.getPage(), wrapper);

        return Result.ok(page);
    }

    @Override
    public Result updateThumhup(String id, int count) {
        if(count != -1 && count != 1) {
            return Result.error("无效操作");
        }

        if(StringUtils.isEmpty(id)) {
            return Result.error("无效操作");
        }

        // 查询这篇物资现有数据，查询到后，将点赞数进行更新
        Goods goods = baseMapper.selectById(id);
        if(goods == null) {
            return Result.error("物资不存在");
        }

        if(goods.getThumhup() <= 0 && count == -1) {
            return Result.error("无效操作");
        }
        // 更新点赞数
        goods.setThumhup( goods.getThumhup() + count );
        baseMapper.updateById(goods);

        return Result.ok();
    }

    @Override
    public Result updateViewCount(String id) {
        if(StringUtils.isEmpty(id)) {
            return Result.error("无效操作");
        }
        Goods goods = baseMapper.selectById(id);
        if(goods == null){
            return Result.error("物资不存在");
        }
        goods.setViewCount( goods.getViewCount() + 1 );

        baseMapper.updateById(goods);
        return Result.ok();
    }

    @Override
    public Result findListByLabelIdOrCategoryId(GoodsListREQ req) {
        IPage<Goods> page = baseMapper.findListByLabelIdOrCategoryId(req.getPage(), req);
        return Result.ok(page);
    }

    @Override
    public Result getGoodsTotal() {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        // 状态是可借出
        wrapper.eq("status", GoodsStatusEnum.SUCCESS.getCode());
        // 公开
        wrapper.eq("ispublic", 1);

        Integer total = baseMapper.selectCount(wrapper);
        return Result.ok(total);
    }

    @Override
    public Result selectCategoryTotal() {
        List<Map<String, Object>> maps = baseMapper.selectCategoryTotal();

        // 将分类名称单独提取到集合中
        List<Object> nameList = new ArrayList<>();
        for(Map<String, Object> map: maps) {
            nameList.add( map.get("name") );
        }

        // 封装响应数据
        Map<String, Object> data = new HashMap<>();
        data.put("nameAndValueList", maps);
        data.put("nameList", nameList);

        return Result.ok(data);
    }

    @Override
    public Result selectMonthGoodsTotal() {
        List<Map<String, Object>> maps = baseMapper.selectMonthGoodsTotal();

        // 将年月提取到集合中
        List<Object> yearMonthList = new ArrayList<>();
        // 将每个月的物资数提取到集合中
        List<Object> goodsTotalList = new ArrayList<>();

        for(Map<String, Object> map: maps) {
            yearMonthList.add ( map.get("year_month") );
            goodsTotalList.add( map.get("total") );
        }

        // 封装响应的data数据
        Map<String, Object> data = new HashMap<>();
        data.put("yearMonthList", yearMonthList);
//        注意，当前前端要的key就是 aritcleTotalList ，即使key单词有问题也就将错就错
        data.put("aritcleTotalList", goodsTotalList);

        return Result.ok(data);
    }
}
