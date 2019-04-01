package com.nbd.ocp.core.repository.crud;
/*
                       _ooOoo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      O\  =  /O
                   ____/`---'\____
                 .'  \\|     |//  `.
                /  \\|||  :  |||//  \
               /  _||||| -:- |||||-  \
               |   | \\\  -  /// |   |
               | \_|  ''\---/''  |   |
               \  .-\__  `-`  ___/-. /
             ___`. .'  /--.--\  `. . __
          ."" '<  `.___\_<|>_/___.'  >'"".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `-.   \_ __\ /__ _/   .-` /  /
    ======`-.____`-.___\_____/___.-`____.-'======
                       `=---='
    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
             佛祖保佑       永无BUG
*/


import com.fasterxml.jackson.annotation.JsonInclude;
import com.nbd.ocp.core.repository.base.IOcpBaseDo;

import javax.persistence.MappedSuperclass;
import java.time.Instant;

/**
 * 基本do 平台和业务所有的do都要继承于此do
 * @author jhb
 */
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface IOcpCrudBaseDo extends IOcpBaseDo {
    /**
     * id
     *     @Id
     *     @GenericGenerator(name="system-uuid", strategy = "uuid.hex")
     *     @GeneratedValue(generator="system-uuid")
     *     @Column(name="id",length = 32)
     * @return id
     */
    String getId();
    /**
     * 版本号
     * @Column(name="version")
     * @return 版本号
     */
    Integer getVersion();
    void setVersion(Integer version);

    /**
     * 状态
     * @Column(name="status")
     * @return 状态
     */
    Integer getStatus();

    void setStatus(Integer status);

    /**
     * 更新时间戳
     * @Column(name="ts")
     * @UpdateTimestamp
     * @return 时间戳
     */
    Instant getTs();
    void setTs(Instant ts);

    /**
     * 创建时间
     * @Column(name="create_time")
     * @CreationTimestamp
     * @return 创建时间
     */
    Instant getCreateTime();
    void setCreateTime(Instant createTime);

    /**
     * 创建人
     * @Column(name="creator")
     * @return 创建人
     */
    String getCreator();
    void setCreator(String creator);


    /**
     * 系统id
     * @Column(name="sys_id")
     * @return 系统id
     */
    String getSysId();
    void setSysId(String sysId);

}
