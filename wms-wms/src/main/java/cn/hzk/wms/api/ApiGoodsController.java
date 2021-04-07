package cn.hzk.wms.api;

import cn.hzk.wms.req.GoodsListREQ;
import cn.hzk.wms.service.IGoodsService;
import cn.hzk.util.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "物资管理API接口", description = "物资管理API接口，不需要通过身份认证就可以访问下面的接口")
@RestController
@RequestMapping("/api/goodsManager")
public class ApiGoodsController {

    @Autowired
    private IGoodsService goodsService;

    @ApiOperation("查询物资详情接口")
    @ApiImplicitParam(name = "id", value = "物资ID", required = true)
    @GetMapping("/{id}") // localhost:8001/wms/api/wms/xxx
    public Result view(@PathVariable String id) {
        return goodsService.findGoodsAndLabelById(id);
    }

    @ApiImplicitParam(name = "id", value = "物资ID", required = true)
    @ApiOperation("更新物资浏览次数")
    @PutMapping("/viewCount/{id}") // /api/wms/viewCount/{id}
    public Result updateViewCount(@PathVariable("id") String id) {
        return goodsService.updateViewCount(id);
    }

    @ApiOperation("公开且已审核的物资列表接口")
    @PostMapping("/list") // /api/wms/list
    public Result list(@RequestBody GoodsListREQ req) {
       return goodsService.findListByLabelIdOrCategoryId(req);
    }
}
