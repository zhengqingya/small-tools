### RBAC是什么？

RBAC模型（Role-Based Access Control：基于角色的访问控制）

RBAC认为权限授权的过程可以抽象地概括为：
Who是否可以对What进行How的访问操作，并对这个逻辑表达式进行判断是否为True的求解过程，
也即是将权限问题转换为What、How的问题，Who、What、How构成了访问权限三元组；

### RBAC组成

1. User（用户）：每个用户都有唯一的UID识别，并被授予不同的角色
2. Role（角色）：不同角色具有不同的权限
3. Permission（权限）：访问权限
4. 用户-角色映射：用户和角色之间的映射关系
5. 角色-权限映射：角色和权限之间的映射

```sql
SELECT u.user_id,
       u.username                     "用户名",
       GROUP_CONCAT(DISTINCT r.NAME ) "角色",
       GROUP_CONCAT(DISTINCT p.NAME ) "权限"
FROM t_sys_user u
         INNER JOIN t_sys_user_role ur ON u.user_id = ur.user_id
         INNER JOIN t_sys_role r ON ur.role_id = r.role_id
         INNER JOIN t_sys_role_permission rp ON r.role_id = rp.role_id
         INNER JOIN t_sys_permission p ON rp.permission_id = p.id
GROUP BY u.user_id
```
