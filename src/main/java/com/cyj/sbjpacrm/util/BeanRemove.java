package com.cyj.sbjpacrm.util;

import com.cyj.sbjpacrm.entity.ModulesDB;
import com.cyj.sbjpacrm.entity.RolesDB;
import com.cyj.sbjpacrm.entity.UsersDB;

import java.util.List;

public class BeanRemove {
    public static List<ModulesDB> remove(List<ModulesDB> list){
        list.forEach(item -> {
            item.setRolesDBS(null);
        });
        return list;
    }
    public static  List<UsersDB> removeRoles(List<UsersDB> list){
        list.forEach(item->{
            item.setRolesDBS(null);
        });
        return list;
    }
    public  static List<RolesDB> removeModulesDBPermisssonDB(List<RolesDB> list){
        list.forEach(item->{
            item.setUsersDBS(null);
            item.setPermissionDBS(null);
            item.setModulesDBS(null);
        });
        return list;
    }

}
