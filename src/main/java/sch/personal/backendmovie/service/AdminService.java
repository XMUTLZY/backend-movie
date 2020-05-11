package sch.personal.backendmovie.service;

import org.springframework.stereotype.Service;
import sch.personal.backendmovie.http.request.AdminRequest;
import sch.personal.backendmovie.http.response.BaseResponse;
import javax.servlet.http.HttpServletRequest;

@Service
public class AdminService {
    public BaseResponse login(AdminRequest request, HttpServletRequest httpServletRequest) {
        BaseResponse response = new BaseResponse();
        if ("超级管理员".equals(request.getUserName()) && "123456".equals(request.getPassword())) {
            response.setMessage("登录成功");
            httpServletRequest.getSession().setAttribute("ADMIN_USER_NAME", request.getUserName()); //登录成功，存session
            return response;
        } else if (!"超级管理员".equals(request.getUserName())){
            response.setMessage("用户名错误");
            response.setStatusCode(BaseResponse.FAILD_CODE);
            return response;
        } else {
            response.setMessage("密码错误");
            response.setStatusCode(BaseResponse.FAILD_CODE);
            return response;
        }
    }
}
