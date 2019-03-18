/**
 * www.cdoframework.com 版权所有
 * 作者：邓勇
 * May 22, 2012
 */
package com.dafy.config.demo;

import com.cdoframework.cdolib.base.Return;
import com.cdoframework.cdolib.data.cdo.CDO;
import com.cdoframework.cdolib.servlet.CDOServlet;
import com.dafy.dflib.business.BusinessService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BusinessServlet extends CDOServlet {
    private static final long serialVersionUID = 1L;

    public Return handleTrans(HttpServletRequest request, HttpServletResponse response, CDO cdoRequest, CDO cdoResponse) {
        CDO cdoLoginer = (CDO) request.getAttribute("cdoLoginer");
        if (cdoLoginer != null) {
            cdoRequest.setCDOValue("cdoLoginer", cdoLoginer);
        }
        Return ret = BusinessService.getInstance().handleTrans(cdoRequest, cdoResponse);

        return ret;
    }

    public Return checkToDo(HttpServletRequest request, CDO cdoRequest) {
        return null;
    }

    public void handleEvent(CDO cdoEvent) {
    }

    protected Return checkTrans(HttpServletRequest request, CDO cdoRequest) {
        return Return.OK;
    }

    protected void onTransChecked(HttpServletRequest request, CDO cdoRequest, boolean bAllowed) {
    }

    public void raiseEvent(CDO cdoEvent) {
    }
}
