package indi.hadeslock.server.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Author: Hadeslock
 * Created on 2021/12/21 19:44
 * Email: hadeslock@126.com
 * Desc: 自定义的返回消息类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private long code;
    private String message;
    private Object obj;

    /*
     * @author Hadeslock
     * @time 2021/12/21 19:43
     * 成功结果
     */
    public static RespBean success(String message){
        return new RespBean(200, message, null);
    }

    /*
     * @author Hadeslock
     * @time 2021/12/21 19:43
     * 带对象的成功结果
     */
    public static RespBean success(String message, Object obj){
        return new RespBean(200, message, obj);
    }

    /*
     * @author Hadeslock
     * @time 2021/12/21 19:44
     * 服务器错误，失败返回结果
     */
    public static RespBean error(String message){
        return new RespBean(500, message, null);
    }

    /*
     * @author Hadeslock
     * @time 2021/12/21 19:44
     * 服务器错误，失败返回结果，带一个对象
     */
    public static RespBean error(String message, Object obj){
        return new RespBean(500, message, obj);
    }
}
