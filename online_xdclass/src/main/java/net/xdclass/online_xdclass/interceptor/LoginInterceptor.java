package net.xdclass.online_xdclass.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.xdclass.online_xdclass.enums.ResultEnum;
import net.xdclass.online_xdclass.util.JWTUtils;
import net.xdclass.online_xdclass.util.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName:LoginInterceptor Package:com.xdclass.online_xdclass.interceptor Description:
 *
 * @Date:2021/1/28 17:32
 * @Author:sunzheng@bmrj.com
 */
public class LoginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    try {
      String accesToken = request.getHeader("token");
      if (accesToken == null) {
        accesToken = request.getParameter("token");
      }
      if (StringUtils.isNotBlank(accesToken)) {
        Claims claims = JWTUtils.checkJWT(accesToken);
        if (claims == null) {
          //告诉登录过期，重新登录
          sendJsonMessage(response, ResultVO.failed(ResultEnum.TOKEN_CUSTOMEXCEPTION.getCode(),
              ResultEnum.TOKEN_CUSTOMEXCEPTION.getMsg()));
          return false;
        }
        Integer id = (Integer) claims.get("id");
        String name = (String) claims.get("name");
        request.setAttribute("user_id", id);
        request.setAttribute("name", name);
        return true;
      }
    } catch (Exception e) {
    }
    sendJsonMessage(response, ResultVO.failed(ResultEnum.TOKEN_CUSTOMEXCEPTION.getCode(),
        ResultEnum.TOKEN_CUSTOMEXCEPTION.getMsg()));
    return false;
  }

  public static void sendJsonMessage(HttpServletResponse response, Object obj) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      response.setContentType("application/json; charset=utf-8");
      PrintWriter writer = response.getWriter();
      writer.print(objectMapper.writeValueAsString(obj));
      writer.close();
      response.flushBuffer();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {

  }
}
