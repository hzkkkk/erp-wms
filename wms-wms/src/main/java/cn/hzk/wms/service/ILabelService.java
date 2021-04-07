package cn.hzk.wms.service;

import cn.hzk.wms.req.LabelREQ;
import cn.hzk.entities.Label;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.hzk.util.base.Result;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author hzk
 */
public interface ILabelService extends IService<Label> {

    /**
     * 条件分页查询标签列表
     * @param req
     * @return
     */
    Result queryPage(LabelREQ req);
}
