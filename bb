package com.treespond.core.filters;

import com.treespond.core.beans.OXSession;
import com.treespond.core.controller.SecurityController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OXSessionFilter  extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);



    public static final String OXSESSION="OXSESSION";
    public static final String REFFERAL_CODE="refCode";
    public static final String BONUS_CODE="bonusCode";
    public static final String GIFT_CODE="giftCode";
    public static final String REFFERAL_ID="refId";

//     @Autowired
    private OXSession oxsesion;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {




            HttpSession session = ((HttpServletRequest)servletRequest).getSession(true);

            if(session==null) return;

            if(session.getAttribute(OXSESSION)==null){
                ServletContext servletContext = servletRequest.getServletContext();
                WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                oxsesion =  new OXSession();

                session.setAttribute(OXSESSION,oxsesion);
            }
                oxsesion = (OXSession) session.getAttribute(OXSESSION);



        logger.info("OX Session Filter was called");


        if(oxsesion!=null){

            logger.info("what we have in oxsession: "+oxsesion.toString());
        }else {
            logger.info("oxsession is null");
        }
        if (servletRequest.getParameterMap().containsKey(REFFERAL_CODE)) {

            logger.info("OX Session Filter was called");
            String ref = servletRequest.getParameter(REFFERAL_CODE);
            oxsesion.setRefCode(ref);
            logger.info("Found Parameter "+REFFERAL_CODE+ " with value : ["+ref+"]");
        }

        if (servletRequest.getParameterMap().containsKey(BONUS_CODE)) {

            logger.info("OX Session Filter was called");
            String bonus = servletRequest.getParameter(BONUS_CODE);
            oxsesion.setBonusCode(bonus);
            logger.info("Found Parameter "+BONUS_CODE+ " with value : ["+bonus+"]");
        }

        if (servletRequest.getParameterMap().containsKey(GIFT_CODE)) {

            logger.info("OX Session Filter was called");
            String bonus = servletRequest.getParameter(GIFT_CODE);
            oxsesion.setGiftCode(bonus);
            logger.info("Found Parameter "+GIFT_CODE+ " with value : ["+bonus+"]");
        }

        if (servletRequest.getParameterMap().containsKey(REFFERAL_ID)) {

            logger.info("OX Session Filter was called");
            String refid = servletRequest.getParameter(REFFERAL_ID);
            oxsesion.setRefId(refid);
            logger.info("Found Parameter "+REFFERAL_ID+ " with value : ["+refid+"]");
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
