package com.ipower.framework.common.core.session;

import com.ipower.framework.common.core.collection.Lists;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * JWT用户信息
 *
 * @author hcs
 */
@Getter
@Setter
public class JwtUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -685485965611753786L;
    
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户所属部门id列表
     */
    private List<Long> deptIds = Lists.arrayList();

    /**
     * 用户角色编码列表
     */
    private List<String> roleCodes = Lists.arrayList();

    /**
     * 用户角色ID列表
     */
    private List<Long> roleIds = Lists.arrayList();

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 租户ID列表
     */
    private List<Long> tenantIds = Lists.arrayList();

}
