package org.plane_helper.plane_helper.controller;

import org.apache.ibatis.javassist.NotFoundException;
import org.plane_helper.plane_helper.common.ApiResponse;
import org.plane_helper.plane_helper.dao.UserMapper;
import org.plane_helper.plane_helper.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-02-25
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @GetMapping("/{id}")
    public ApiResponse<User> getUser(@PathVariable Integer id) throws NotFoundException {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return ApiResponse.success(user);
    }
}
