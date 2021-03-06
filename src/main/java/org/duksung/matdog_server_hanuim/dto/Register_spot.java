package org.duksung.matdog_server_hanuim.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class Register_spot {
    private int userIdx;
    private int registerIdx;
    private int registerStatus;
    private String kindCd;
    private String sexCd;
    private String weight;
    private String age;
    private String careAddr;
    private String findPlace;
    private Date findDate;
    private Date happenDt;
    private String specialMark;
    private String careTel;
    private String email;
    private String dm;
    private String filename;
}
