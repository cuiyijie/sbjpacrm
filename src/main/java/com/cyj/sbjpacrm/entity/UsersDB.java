package com.cyj.sbjpacrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name = "users", schema = "sister", catalog = "")
@Data
public class UsersDB implements Serializable {
    @Id
    @Column(name = "id", nullable = false, length = 100)
    private String id;
    @Column(name = "loginName", nullable = false, length = 50)
    private String loginName;
    @Column(name = "password", nullable = false, length = 50)
    private String password;
    @Column(name = "isLockout", nullable = true, length = 2)
    private String isLockout;
    @Column(name = "lastLoginTime", nullable = true)
    private Timestamp lastLoginTime;
    @Column(name = "psdWrongTime", nullable = true)
    private Integer psdWrongTime;
    @Column(name = "lockTime", nullable = true)
    private Timestamp lockTime;
    @Column(name = "protectEMail", nullable = true, length = 200)
    private String protectEMail;
    @Column(name = "protectMTel", nullable = true, length = 11)
    private String protectMTel;
    @Column(name = "createTime", nullable = true)
    private Timestamp createTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "userroles",joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<RolesDB> rolesDBS;

}
