package com.ipower.framework.common.core.exception.entity;

import com.ipower.framework.common.core.exception.checked.CheckedException;
import com.ipower.framework.common.core.exception.unchecked.UncheckedException;
import com.ipower.framework.common.core.lang.ObjectUtil;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Class description goes here.
 *
 * @author kris
 */
@Data
public class ExceptionEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1881343016888477702L;
    /**
     * 异常时间
     */
    private Long timestamp;

    /**
     * 请求的url
     */
    private String url;

    /**
     * 资源标识
     */
    private String uri;

    /**
     * 请求的ip
     */
    private String ip;

    /**
     * 请求方法
     */
    private String method;

    /**
     * sessionId
     */
    private String sessionId;

    /**
     * 请求参数
     */
    private Map<String, String[]> params;

    /**
     * 是否ajax请求
     */
    private boolean ajaxRequest;

    /**
     * 异常编号
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String message;

    public ExceptionEntity() {

    }

    public ExceptionEntity(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        this.timestamp = System.currentTimeMillis();
        this.url = request.getRequestURL().toString();
        this.uri = request.getRequestURI();
        this.ip = request.getRemoteHost();
        this.method = request.getMethod();
        this.params = request.getParameterMap();
        this.sessionId = request.getSession().getId();
        this.ajaxRequest = request.getHeader("accept").contains("application/json") || (request
                .getHeader("X-Requested-With") != null && "XMLHttpRequest"
                .equalsIgnoreCase(request.getHeader("X-Requested-With")));
        if (exception instanceof UncheckedException) {
            this.code = ((UncheckedException) exception).getCode();
        } else if (exception instanceof CheckedException) {
            this.code = ((CheckedException) exception).getCode();
        } else {
            this.code = 0;
        }
        this.message = exception.getMessage();
    }

    public String getRequestInfo() {
        return "{timestamp=" + timestamp + ", url='" + url + '\'' + ", uri='" + uri + '\'' + ", ip='" + ip + '\'' + ", method='" + method + '\'' + ", sessionId='" + sessionId + '\'' + ", params=" + paramsToString() + ", ajaxRequest=" + ajaxRequest + "}";
    }

    @Override
    public String toString() {
        return "ExceptionEntity{" + "timestamp=" + timestamp + ", url='" + url + '\'' + ", uri='" + uri + '\'' + ", ip='" + ip + '\'' + ", method='" + method + '\'' + ", sessionId='" + sessionId + '\'' + ", params=" + paramsToString() + ", ajaxRequest=" + ajaxRequest + ", code=" + code + ", message='" + message + '\'' + '}';
    }

    private String paramsToString() {
        StringBuilder sb = new StringBuilder("[");
        if (ObjectUtil.isNotEmpty(params)) {
            int i = 0;
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                sb.append("{").append(entry.getKey()).append("=[");
                String[] values = ObjectUtil.nullToDefault(entry.getValue(), new String[0]);
                for (int k = 0; k < values.length; k++) {
                    sb.append("'").append(values[k]).append("'");
                    if (k < values.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]}");
                if (i < params.size() - 1) {
                    sb.append(", ");
                }
                i++;
            }

        }
        return sb.append("]").toString();
    }
}
