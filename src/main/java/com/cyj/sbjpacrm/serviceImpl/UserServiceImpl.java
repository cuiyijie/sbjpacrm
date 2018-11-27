package com.cyj.sbjpacrm.serviceImpl;

import com.cyj.sbjpacrm.entity.RolesDB;
import com.cyj.sbjpacrm.entitySerch.Userserch;
import com.cyj.sbjpacrm.password.PasswordEncoder;
import com.cyj.sbjpacrm.repository.UserRepository;
import com.cyj.sbjpacrm.entity.UsersDB;
import com.cyj.sbjpacrm.result.Result;
import com.cyj.sbjpacrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    /***
     * 用户 登录
     * @param loginName
     * @return
     */
    @Override
    public UsersDB findByLoginName(String loginName) {

        return userRepository.findByLoginName(loginName);
    }

    /***
     * 用户登录实现方法
     * @param loginName
     * @param password
     * @return
     */
    public Object userLongin(String loginName, String password) {
        UsersDB users = userRepository.findByLoginName(loginName);
        if (users == null) {
            return new Result("用户名不正确", 0);
        } else {
            PasswordEncoder encoderMd5 = new PasswordEncoder(loginName, "MD5");
            String encode = encoderMd5.encode(password);
            if (users.getPassword().equals(encode)) {
                if (users.getIsLockout().equals("是")) {
                    return new Result("用户已被锁定,请联系管理员!", 0);
                }
            } else if (users.getPsdWrongTime() == 5) {
                users.setIsLockout("是");
                userRepository.save(users);
                return new Result("用户已被锁定,请联系管理员!", 0);
            } else {
                users.setPsdWrongTime(users.getPsdWrongTime() + 1);
                userRepository.save(users);
                return new Result("密码不正确!" + (5 - users.getPsdWrongTime()) + "次之后将锁定用户！", 0);
            }

        }
        users.setIsLockout("否");
        users.setPsdWrongTime(0);
        userRepository.save(users);
        List<RolesDB> rolesDB = users.getRolesDBS();
        List<String> rolesNames = rolesDB.stream().map(RolesDB::getName).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleIds", rolesNames);
        map.put("name", users.getLoginName());
        map.put("uid", users.getId());
        return new Result(map);
    }

    /***
     * 用户管理的分页显示
     * @param userserch
     * @return
     */
    @Override
    public Page<UsersDB> queryByDynamicSQLPageUser(Userserch userserch) {

        if (userserch.getOrderBy()==null||userserch.getOrderBy().equals("")){
             userserch.setOrderBy("id");
        }
        Sort sort=new Sort(Sort.Direction.DESC,userserch.getOrderBy());
        PageRequest pageable=PageRequest.of(userserch.getPage()-1,userserch.getRows(),sort);

        return userRepository.findAll(this.getWhereClause(userserch),pageable);
    }

    /***
     * 修改用户
     * @param usersDB
     * @return
     */
    @Override
    public UsersDB updateUser(UsersDB usersDB) {
      return   userRepository.save(usersDB);
    }

    /***
     * 根据id删除用户
     * @param usersDB
     */
    @Override
    public void deleteUser(UsersDB usersDB) {
        userRepository.delete(usersDB);
    }

    /***
     * 重置密码
     * @param usersDB
     * @return
     */
    @Override
    public UsersDB updateCzPwd(UsersDB usersDB) {
        return  userRepository.save(usersDB);
    }

    /***
     * 用户多条件分页显示的where sql 拼装
     * @param userserch
     * @return
     */
    private Specification<UsersDB> getWhereClause(final  Userserch userserch){
        return  new Specification<UsersDB>() {
            @Override
            public Predicate toPredicate(Root<UsersDB> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
               Predicate predicate=criteriaBuilder.conjunction();
               List exceptionList=predicate.getExpressions();
                if (userserch.getUserName()!=null&&!"".equals(userserch.getUserName())){
                    exceptionList.add(criteriaBuilder.like(root.<String>get("loginName"),"%"+userserch.getUserName()+"%"));
                }
                if (userserch.getDateStart()!=null){
                    System.out.println("开始查询");
                    exceptionList.add(criteriaBuilder.greaterThanOrEqualTo(root.<Timestamp>get("createTime"),userserch.getDateStart()));
                }
                if (userserch.getDateEnd()!=null){
                    exceptionList.add(criteriaBuilder.lessThanOrEqualTo(root.<Timestamp>get("createTime"),userserch.getDateEnd()));
                    System.out.println("结束查询");
                }
                if (userserch.getUserIsLockout()!=null&&!"".equals(userserch.getUserIsLockout())){
                    exceptionList.add(criteriaBuilder.equal(root.<String>get("isLockout"),userserch.getUserIsLockout()));
                }
                //if (userserch.getOrderBy())

                return predicate;
            }


        };
    }

}
