package cn.hzk.wms.service.impl;

import cn.hzk.entities.Label;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.hzk.wms.req.LabelREQ;
import cn.hzk.wms.mapper.LabelMapper;
import cn.hzk.wms.service.ILabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hzk.util.base.Result;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author hzk
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements ILabelService {

    @Override
    public Result queryPage(LabelREQ req) {
        // 条件分页查询
        IPage<Label> page = baseMapper.queryPage(req.getPage(), req);
        return Result.ok(page);
    }

    @Override
    public boolean updateById(Label label) {
        label.setUpdateDate(new Date());
        return super.updateById(label);
    }
}
