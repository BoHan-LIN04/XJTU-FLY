package org.plane_helper.plane_helper.service.impl;

import org.plane_helper.plane_helper.model.User;
import org.plane_helper.plane_helper.dao.UserMapper;
import org.plane_helper.plane_helper.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-02-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
