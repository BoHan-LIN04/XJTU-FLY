package org.flight_helper.flight_helper_bigdata.dbService.impl;

import org.flight_helper.flight_helper_bigdata.model.User;
import org.flight_helper.flight_helper_bigdata.dao.UserMapper;
import org.flight_helper.flight_helper_bigdata.dbService.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
