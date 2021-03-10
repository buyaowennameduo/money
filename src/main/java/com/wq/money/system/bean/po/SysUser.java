package com.wq.money.system.bean.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
@ApiModel(value = "sys_user", description = "用户表")
public class SysUser extends Model<SysUser> {
    @ApiModelProperty(value = "用户名")
    @TableId(value = "user_name")
    private String userName;
    @ApiModelProperty(value = "用户密码")
    @TableField("user_pass")
    private String userPass;
    @ApiModelProperty(value = "用户手机号")
    @TableField("user_phone")
    private String userPhone;
    @ApiModelProperty(value = "用户邮箱")
    @TableField("user_email")
    private String userEmail;
    @ApiModelProperty(value = "用户地址")
    @TableField("user_address")
    private String userAddress;
    @ApiModelProperty(value = "删除标识")
    @TableField("del_flag")
    @TableLogic(value = "0", delval = "1")
    private int delFlag;
}
