package com.cyj.sbjpacrm.serviceImpl;

import com.cyj.sbjpacrm.entity.ModulesDB;
import com.cyj.sbjpacrm.entity.RolesDB;
import com.cyj.sbjpacrm.entity.UsersDB;
import com.cyj.sbjpacrm.repository.ModuleRepository;
import com.cyj.sbjpacrm.repository.RolesRepository;
import com.cyj.sbjpacrm.repository.UserRepository;
import com.cyj.sbjpacrm.service.RolesService;
import com.cyj.sbjpacrm.util.BeanRemove;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;




import java.util.*;
import java.util.stream.Collectors;

@Service
public class RolesServiceImpl implements RolesService {
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private UserRepository userRepository;

    /***
     * 根据角色名获取模块
     * @param name
     * @return
     */
    public List<ModulesDB> queryModuleByroled(String name) {
        List<RolesDB> rolesDBS = rolesRepository.findByNameIn(name.split(","));
        List<ModulesDB> modulesDBS=moduleRepository.findByRolesDBSIn(rolesDBS);
        BeanRemove.remove(modulesDBS);
          Set<ModulesDB> set=new HashSet<ModulesDB>(modulesDBS);
          List list=new ArrayList(set);
          list.sort(Comparator.comparing(ModulesDB::getId));
        return list;
    }

    /***
     * 根据名字查询角色
     * @param name
     * @return
     */
    @Override
    public RolesDB findByName(String name) {
        return rolesRepository.findByName(name);
    }

    /***
     * 获取用户已经拥有的角色
     * @param loginName
     * @return
     */
    @Override
    public List<RolesDB> queryAllRoleByLoginName(String loginName) {
        UsersDB usersDB=userRepository.findByLoginName(loginName);
        List<RolesDB> rolesDB = usersDB.getRolesDBS();
        List<String> rolesNames = rolesDB.stream().map(RolesDB::getName).collect(Collectors.toList());
        List<RolesDB> rolesDBS=new ArrayList<>();
        for (String rolesName : rolesNames) {
            RolesDB rolesDB1=rolesRepository.findByName(rolesName);
            rolesDB1.setModulesDBS(null);
            rolesDB1.setPermissionDBS(null);
            rolesDB1.setUsersDBS(null);
            rolesDBS.add(rolesDB1);
        }
        return rolesDBS;
    }

    /***
     * 获取角色未拥有的角色
     * @param loginName
     * @return
     */
    @Override
    public List<RolesDB> queryAllRole(String loginName) {
        List<RolesDB> rolesDBS=rolesRepository.findAll();
        List<RolesDB> rolesDBList= BeanRemove.removeModulesDBPermisssonDB(rolesDBS);
        List<RolesDB>rolesDBSYY= this.queryAllRoleByLoginName(loginName);
        for (int i = 0; i < rolesDBSYY.size(); i++) {
            RolesDB rolesDB=rolesDBSYY.get(i);
            for (int j = 0; j < rolesDBList.size(); j++) {
                RolesDB rolesDB1=rolesDBList.get(j);
                if(rolesDB.getName().equals(rolesDB1.getName())){
                    rolesDBList.remove(j);
                }
            }

        }

        return rolesDBList;
    }

    /***
     * 角色分页显示
     * @param name
     * @param page
     * @param rows
     * @return
     */
    public Page<RolesDB> findByNameLike(String name, Integer page, Integer rows) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest pageable= PageRequest.of(page-1,rows,sort);
        return rolesRepository.findByNameLike("%"+name+"%",pageable);
    }





}
