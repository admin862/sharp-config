package com.dafy.config.domain;

import lombok.Data;

import java.util.Date;

@Data
public class AuditTask {

    private long id;

    private long configId;

    private int fromVersion;

    private int toVersion;

    private long fromConfigId;

    private long toConfigId;

    private int state;

    private String submitUsername;

    private String auditUsername;

    private String envName;

    private String groupName;

    private String appName;

    private String configName;

    private String remark;

    private Date createTime;

    private String showStateText;

    public enum AuditState{

        /** 待审核 **/
        WAIT_AUDIT(0),

        /** 审核通过 **/
        AUDIT_SUCCESS(1),

        /** 审核失败 **/
        AUDIT_FAIL(2),

        /** 已完成 **/
        FINISH(3);

        AuditState(int value){
            this.value = value;
        }

        private int value;

        public int getValue() {
            return value;
        }

        public static AuditState from(int value){
            for (AuditState state : values()){
                if(state.getValue() == value){
                    return state;
                }
            }
            return null;
        }
    }

}
