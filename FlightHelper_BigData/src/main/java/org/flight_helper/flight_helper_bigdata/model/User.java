package org.flight_helper.flight_helper_bigdata.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Getter
@Setter
@ToString
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ç”¨æˆ·ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * ç”¨æˆ·å��
     */
    @TableField("user_name")
    private String userName;

    /**
     * å¯†ç �
     */
    @TableField("password")
    private String password;

    /**
     * è§’è‰²
     */
    @TableField("role")
    private String role;
}
