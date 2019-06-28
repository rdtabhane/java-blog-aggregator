package com.blog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingResponseWrapper;

//@Component
public class CustomRequestInterceptor extends HandlerInterceptorAdapter {

  private static final Logger logger = LoggerFactory.getLogger(CustomRequestInterceptor.class);
  private boolean includeResponsePayload = true;
  private int maxPayloadLength = 1000;
  
  @Override
  public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
      final Object handler) throws Exception {
    // Logs here
    // return super.preHandle(request, response, handler);
    long startTime = Instant.now().toEpochMilli();
    logger.info(
        "Request URL::" + request.getRequestURL().toString() + ":: Start Time=" + Instant.now());
    request.setAttribute("startTime", startTime);
    return true;
  }

  @Override
  public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
      final Object handler, final Exception ex) {
    long startTime = System.currentTimeMillis();
    StringBuffer reqInfo = new StringBuffer()
        .append("[")
        .append(startTime % 10000)  // request ID
        .append("] ")
        .append(request.getMethod())
        .append(" ")
        .append(request.getRequestURL());

       String queryString = request.getQueryString();
       if (queryString != null) {
         reqInfo.append("?").append(queryString);
       }

       if (request.getAuthType() != null) {
         reqInfo.append(", authType=")
           .append(request.getAuthType());
       }
       if (request.getUserPrincipal() != null) {
         reqInfo.append(", principalName=")
           .append(request.getUserPrincipal().getName());
       }

       this.logger.debug("=> " + reqInfo);
       
    /*
     * long startTime = (Long) request.getAttribute("startTime");
     * 
     * logger.info("Request URL::" + request.getRequestURL().toString() + ":: Time Taken=" +
     * (Instant.now().toEpochMilli() - startTime));
     * 
     * logger.info("Response ::" + response + ":: Time Taken=" + (Instant.now().toEpochMilli() -
     * startTime));
     */
    
    ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
    long duration = System.currentTimeMillis() - startTime;
    this.logger.info("<= " + reqInfo + ": returned status=" + response.getStatus() + " in "+duration + "ms");
    if (includeResponsePayload) {
      byte[] buf = wrappedResponse.getContentAsByteArray();
      this.logger.info("   Response body:\n"+getContentAsString(buf, this.maxPayloadLength, response.getCharacterEncoding()));
    }

    try {
      wrappedResponse.copyBodyToResponse();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  // IMPORTANT: copy content of response back into original response
  }
  
  private String getContentAsString(byte[] buf, int maxLength, String charsetName) {
    if (buf == null || buf.length == 0) return "";
    int length = Math.min(buf.length, this.maxPayloadLength);
    try {
      return new String(buf, 0, length, charsetName);
    } catch (UnsupportedEncodingException ex) {
      return "Unsupported Encoding";
    }
  }
}
