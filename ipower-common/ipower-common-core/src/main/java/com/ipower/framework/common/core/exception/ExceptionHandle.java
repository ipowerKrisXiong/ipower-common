package com.ipower.framework.common.core.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class description goes here.
 *
 * @author kris
 */
public interface ExceptionHandle {

    Object handle(HttpServletRequest request, HttpServletResponse response, Exception exception);

}
