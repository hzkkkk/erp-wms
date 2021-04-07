package cn.hzk.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章状态枚举类
 *
 */
@Getter
@AllArgsConstructor
public enum GoodsStatusEnum {

    DELETE(0, "已删除"), WAIT(1, "已借出"),
    SUCCESS(2, "可借出"), FAIL(3, "已损坏");

    private Integer code;
    private String desc;
}
