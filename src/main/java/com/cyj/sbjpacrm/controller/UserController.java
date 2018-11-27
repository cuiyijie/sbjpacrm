package com.cyj.sbjpacrm.controller;

import com.cyj.sbjpacrm.entity.RolesDB;
import com.cyj.sbjpacrm.entity.UsersDB;
import com.cyj.sbjpacrm.entitySerch.Userserch;
import com.cyj.sbjpacrm.password.PasswordEncoder;
import com.cyj.sbjpacrm.repository.RolesRepository;
import com.cyj.sbjpacrm.repository.UserRepository;
import com.cyj.sbjpacrm.result.Result;
import com.cyj.sbjpacrm.service.UserService;
import com.cyj.sbjpacrm.serviceImpl.UserServiceImpl;
import com.cyj.sbjpacrm.util.BeanRemove;
import com.sun.org.apache.regexp.internal.RE;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    /***
     * 用户登录
     * @param loginName
     * @param password
     * @return
     */
    @RequestMapping("/userlongin")
    public Object userlongin(String loginName, String password) {

        return userServiceImpl.userLongin(loginName, password);
    }

    /***
     * 用户管理的多条件的分页显示
     * @param userserch
     * @return
     */
    @RequestMapping("/queryDynamicUser")
    public Object queryDynamicUser(Userserch userserch) {

        Page<UsersDB> page = userServiceImpl.queryByDynamicSQLPageUser(userserch);
        Long total = page.getTotalElements();
        List<UsersDB> list = page.getContent();
        BeanRemove.removeRoles(list);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        return map;

    }

    /***
     * 修改用户
     * @param usersDB
     * @return
     */
    @RequestMapping("/upadteUser")
    public Object updateUser(UsersDB usersDB) {
        try {
            UsersDB users = userServiceImpl.findByLoginName(usersDB.getLoginName());
            users.setProtectEMail(usersDB.getProtectEMail());
            users.setProtectMTel(usersDB.getProtectMTel());
            userServiceImpl.updateUser(users);
            return new Result("修改成功", 1);
        } catch (Exception e) {
            System.out.println("异常信息==>" + e.getCause().getMessage());
            return new Result("修改失败", 0);

        }
    }

    /***
     * 删除用户
     * @param usersDB
     * @return
     */
    @RequestMapping("/deleteUser")
    public Object deleteUser(UsersDB usersDB) {
        try {
            userServiceImpl.deleteUser(usersDB);
            return new Result("删除成功", 1);
        } catch (Exception e) {
            System.out.println("异常信息==>" + e.getCause().getMessage());
            return new Result("删除失败", 0);
        }
    }

    @RequestMapping("/updateCzPwd")
    public Object updateCzPwd(UsersDB usersDB) {
        PasswordEncoder encoderMd5 = new PasswordEncoder(usersDB.getLoginName(), "MD5");
        String encode = encoderMd5.encode("ysd123");
        try {
            UsersDB users = userServiceImpl.findByLoginName(usersDB.getLoginName());
            users.setPassword(encode);
            userServiceImpl.updateCzPwd(users);
            return new Result("重置成功", 1);
        } catch (Exception e) {
            System.out.println("异常信息==>" + e.getCause().getMessage());
            return new Result("重置失败", 0);
        }
    }

    @RequestMapping("/updateSuoLockUser")
    public Object updateSuoLockUser(UsersDB usersDB) {
        try {
            UsersDB users = userServiceImpl.findByLoginName(usersDB.getLoginName());
            users.setIsLockout("是");
            userRepository.save(users);
            return new Result("锁定成功", 1);
        } catch (Exception e) {
            System.out.println("异常信息==>" + e.getCause().getMessage());
            return new Result("锁定失败", 1);
        }

    }

    @RequestMapping("/updateJsuoLockUser")
    public Object updateJsuoLockUser(UsersDB usersDB) {
        try {
            UsersDB users = userServiceImpl.findByLoginName(usersDB.getLoginName());
            users.setIsLockout("否");
            userRepository.save(users);
            return new Result("解锁定成功", 1);
        } catch (Exception e) {
            System.out.println("异常信息==>" + e.getCause().getMessage());
            return new Result("解锁定失败", 1);
        }

    }

    @RequestMapping("/addUser")
    public Object addUser(UsersDB usersDB) {
        PasswordEncoder encoderMd5 = new PasswordEncoder(usersDB.getLoginName(), "MD5");
        String encode = encoderMd5.encode("ysd123");
        usersDB.setPassword(encode);
        UUID id = UUID.randomUUID();
        Timestamp time1 = new Timestamp(System.currentTimeMillis());
        usersDB.setCreateTime(time1);
        usersDB.setId(String.valueOf(id));
        try {
            userRepository.save(usersDB);
            return new Result("新增成功", 1);
        } catch (Exception e) {
            System.out.println("异常信息==>" + e.getCause().getMessage());
            return new Result("新增失败", 0);
        }
    }

    /****
     * 用户添加角色
     * @param loginName
     * @param rolesName
     * @return
     */
    @RequestMapping("/addUserRoles")
    public Object addUserRoles(String loginName, String rolesName) {
        UsersDB usersDB1 = userRepository.findByLoginName(loginName);
        RolesDB rolesDB = rolesRepository.findByName(rolesName);
        List<RolesDB> rolesDBS = usersDB1.getRolesDBS();
        rolesDBS.add(rolesDB);
        usersDB1.setRolesDBS(rolesDBS);

        try {
            userRepository.save(usersDB1);
            return new Result("添加成功", 1);
        } catch (Exception e) {
            System.out.println("异常信息:" + e.getCause().getMessage());
            return new Result("添加失败", 0);
        }

    }

    /****
     * 用户删除角色
     * @param loginName
     * @param rolesName
     * @return
     */
    @RequestMapping("/deleteUserRoles")
    public Object deleteUserRoles(String loginName, String rolesName) {
        UsersDB usersDB1 = userRepository.findByLoginName(loginName);
        RolesDB rolesDB = rolesRepository.findByName(rolesName);
        List<RolesDB> rolesDBS = usersDB1.getRolesDBS();
        rolesDBS.remove(rolesDB);
        usersDB1.setRolesDBS(rolesDBS);
        System.out.println("用户id" + usersDB1.getId());
        System.out.println("角色id" + rolesDB.getId());

        try {
            userRepository.save(usersDB1);
            return new Result("删除成功", 1);
        } catch (Exception e) {
            System.out.println("异常信息:" + e.getCause().getMessage());
            return new Result("删除失败", 0);
        }

    }

}
