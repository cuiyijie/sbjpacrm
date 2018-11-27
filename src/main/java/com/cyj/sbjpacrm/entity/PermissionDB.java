package com.cyj.sbjpacrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "permission", schema = "sister", catalog = "")
@Data

public class PermissionDB implements Serializable {
    @Id
    @Column(name = "permissionId", nullable = false, length = 100)
    private String permissionId;
    @Column(name = "permissionValue", nullable = true, length = 50)
    private String permissionValue;
    @Column(name = "permissionModule", nullable = true, length = 50)
    private String permissionModule;
    @Column(name = "permissionName", nullable = true, length = 50)
    private String permissionName;
    @Column(name = "permissionLastUpdateTime", nullable = true)
    private Timestamp permissionLastUpdateTime;
    @JsonIgnore
    @ManyToMany(mappedBy = "permissionDBS",fetch = FetchType.EAGER)
    private List<RolesDB> rolesDBS;


}
