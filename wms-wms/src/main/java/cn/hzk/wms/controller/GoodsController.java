package cn.hzk.wms.controller;


import cn.hzk.entities.Goods;
import cn.hzk.wms.req.GoodsREQ;
import cn.hzk.wms.req.GoodsUserREQ;
import cn.hzk.wms.service.IGoodsService;
import cn.hzk.util.base.Result;
import cn.hzk.util.enums.GoodsStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 物资信息表 前端控制器
 * </p>
 *
 * @author hzk
 */
@Api(value = "物资管理接口", description = "物资管理接口, 提供物资的增删改查")
@RestController
@RequestMapping("/goodsManager")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @ApiOperation("根据物资标题和状态查询物资分页列表接口")
    @PostMapping("/search") // localhost:8001/wms/wms/search
    public Result search(@RequestBody GoodsREQ req) {
        return goodsService.queryPage(req);
    }

    @ApiOperation("查询物资详情接口")
    @ApiImplicitParam(name = "id", value = "物资ID", required = true)
    @GetMapping("/{id}")
    public Result view(@PathVariable String id) {
        return goodsService.findGoodsAndLabelById(id);
    }


    @ApiOperation("修改物资信息接口")
    @PutMapping // put 请求 localhost:8001/wms/wms
    public Result update(@RequestBody Goods goods) {
        return goodsService.updateOrSave(goods);
    }

    @ApiOperation("新增物资信息接口")
    @PostMapping
    public Result save(@RequestBody Goods goods) {
        return goodsService.updateOrSave(goods);
    }

    @ApiImplicitParam(name = "id", value = "物资ID", required = true)
    @ApiOperation("删除物资接口")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        // 假删除，只是将状态更新
       return goodsService.updateStatus(id, GoodsStatusEnum.DELETE);
    }

    @ApiImplicitParam(name = "id", value = "物资ID", required = true)
    @ApiOperation("审核通过接口")
    @GetMapping("/audit/success/{id}")
    public Result success(@PathVariable("id") String id) {
        // 审核通过
        return goodsService.updateStatus(id, GoodsStatusEnum.SUCCESS);
    }

    @ApiImplicitParam(name = "id", value = "物资ID", required = true)
    @ApiOperation("审核不通过接口")
    @GetMapping("/audit/fail/{id}")
    public Result fail(@PathVariable("id") String id) {
        // 审核不通过
        return goodsService.updateStatus(id, GoodsStatusEnum.FAIL);
    }

    @ApiOperation("根据用户ID查询公开或未公开的物资列表接口")
    @PostMapping("/user") // /wms/user
    public Result findListByUserId(@RequestBody GoodsUserREQ req) {
        return goodsService.findListByUserId(req);
    }

    @ApiImplicitParams({
       @ApiImplicitParam(name = "id", value = "物资ID", required = true),
       @ApiImplicitParam(name = "count", value = "点赞数", required = true)
    })
    @ApiOperation("更新点赞数")
    @PutMapping("/thumb/{id}/{count}")
    public Result updataThumhup(@PathVariable("id") String id,
                                @PathVariable("count") int count) {
        return goodsService.updateThumhup(id, count);
    }

    @ApiOperation("统计审核通过且公开的物资总记录数")
    @GetMapping("/total")
    public Result getGoodsTotal() {
        return goodsService.getGoodsTotal();
    }

    @ApiOperation("统计各分类下的物资数")
    @GetMapping("/category/total")
    public Result categoryTotal() {
        return goodsService.selectCategoryTotal();
    }

    @ApiOperation("统计近6个月发布的物资数")
    @GetMapping("/month/total")
    public Result monthGoodsTotal() {
        return goodsService.selectMonthGoodsTotal();
    }
}
