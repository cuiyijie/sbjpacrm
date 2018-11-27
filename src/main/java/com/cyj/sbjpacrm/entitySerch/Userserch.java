package com.cyj.sbjpacrm.entitySerch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userserch {
    private String userName;
    private String userIsLockout;
    private String orderBy;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd")
    private Date dateStart;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd")
    private Date dateEnd;
    private  Integer page;
    private  Integer rows;
}
