package com.cyj.sbjpacrm.controller;


import com.cyj.sbjpacrm.entity.ModulesDB;
import com.cyj.sbjpacrm.entity.RolesDB;
import com.cyj.sbjpacrm.entity.UsersDB;
import com.cyj.sbjpacrm.repository.RolesRepository;
import com.cyj.sbjpacrm.repository.UserRepository;
import com.cyj.sbjpacrm.result.Result;
import com.cyj.sbjpacrm.serviceImpl.RolesServiceImpl;
import com.cyj.sbjpacrm.util.BeanRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/roles")
@RestController
public class RolesController {
    @Autowired
    private RolesServiceImpl rolesServiceImpl;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    /***
     * 根据角色获取模块
     * @param name
     * @return
     */
    @RequestMapping("/queryModuleByroled")
    public List<ModulesDB> queryModuleByroled(String name) {
        return rolesServiceImpl.queryModuleByroled(name);
    }

    /***
     * 用户所拥有的角色
     * @param loginName
     *s
     *
     * @return*/
    @RequestMapping("/queryAllRoleByLoginName")
    public Object queryAllRoleByLoginName(String loginName) {

        List<RolesDB> rolesDBS = rolesServiceImpl.queryAllRoleByLoginName(loginName);
        return rolesDBS;
    }

    /***
     * 获取用户未拥有的角色
     * @param loginName
     * @return
     */
    @RequestMapping("/queryRoledSzByName")
    public Object queryRoledSzByName(String loginName) {
        List<RolesDB> rolesDBS = rolesServiceImpl.queryAllRole(loginName);
        return rolesDBS;
    }

    /***
     * 角色分页显示
     * @param name
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/findByNameLikeFy")
    public Object findByNameLikeFy(String name, Integer page, Integer rows) {
        Page<RolesDB> pagess = rolesServiceImpl.findByNameLike(name, page, rows);
        Long total = pagess.getTotalElements();
        List<RolesDB> list = pagess.getContent();
        BeanRemove.removeModulesDBPermisssonDB(list);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        return map;
    }

    /***
     * 修改角色
     * @param rolesDB
     * @return
     */
    @RequestMapping("/updateRoles")
    public  Object updateRoles(RolesDB rolesDB){
        System.out.println("角色id==>"+rolesDB.getId());
        RolesDB rolesDB1=rolesRepository.findById(rolesDB.getId());
        rolesDB1.setName(rolesDB.getName());
        rolesDB1.setString0(rolesDB.getString0());

        try {
            rolesRepository.save(rolesDB1);
            return new Result("修改成功",1);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("修改失败",0);
        }

    }

    /***
     * 删除角色
     * @param rolesDB
     * @return
     */
    @RequestMapping("/deleteRoles")
    public Object deleteRoles(RolesDB rolesDB){
        RolesDB rolesDB1=rolesRepository.findById(rolesDB.getId());
        if (rolesDB1.getUsersDBS().size()!=0){
            return  new Result("用户正在使用此角色,删除失败",0);
        }
        try {
            rolesRepository.delete(rolesDB);
            return  new Result("删除成功",1);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("删除失败",0);
        }
    }
}
