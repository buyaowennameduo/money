package com.wq.money.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wq.money.system.bean.po.SysUser;
import com.wq.money.system.mapper.SysUserMapper;
import com.wq.money.system.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

}
