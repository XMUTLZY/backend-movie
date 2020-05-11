package sch.personal.backendmovie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sch.personal.backendmovie.http.request.AdminRequest;
import sch.personal.backendmovie.http.request.MovieRequest;
import sch.personal.backendmovie.http.request.UserRequest;
import sch.personal.backendmovie.http.response.BaseResponse;
import sch.personal.backendmovie.http.response.LayerResponse;
import sch.personal.backendmovie.service.AdminService;
import sch.personal.backendmovie.service.MovieService;
import sch.personal.backendmovie.service.UserService;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private UserService userService;

    //管理员登录  用户名：超级管理员 密码：123456
    @PostMapping("/login")
    @ResponseBody
    public BaseResponse login(@RequestBody AdminRequest request, HttpServletRequest httpServletRequest) {
        return adminService.login(request, httpServletRequest);
    }

    //用户列表
    @GetMapping("/user-list")
    @ResponseBody
    public LayerResponse userList(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page,
                                  @RequestParam(required = false) Integer uid, @RequestParam(required = false, name = "user_name") String userName) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "uid");
        return userService.userList(pageable, uid, userName);
    }

    //新增用户
    @PostMapping("/add-user")
    @ResponseBody
    public BaseResponse addUser(@RequestBody UserRequest request) {
        return userService.addUser(request);
    }

    //获取指定用户
    @GetMapping("/get-user")
    @ResponseBody
    public BaseResponse getUser(@RequestParam Integer uid) {
        return userService.getUser(uid);
    }

    //修改用户
    @PostMapping("/update-user")
    @ResponseBody
    public BaseResponse updateUser(@RequestBody UserRequest request) {
        return userService.updateUser(request);
    }

    //删除用户
    @GetMapping("/delete-user")
    @ResponseBody
    public BaseResponse deleteUser(@RequestParam Integer uid) {
        return userService.deleteUser(uid);
    }

    //电影列表
    @GetMapping("/movie-list")
    @ResponseBody
    public LayerResponse movieList(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page,
                                   @RequestParam(required = false) Integer mid, @RequestParam(required = false) String name) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "uid");
        return movieService.movieList(pageable, mid, name);
    }

    //新增电影
    @PostMapping("/add-movie")
    @ResponseBody
    public BaseResponse addMovie(@RequestBody MovieRequest request) {
        return movieService.addMovie(request);
    }

    //删除电影
    @GetMapping("/delete-movie")
    @ResponseBody
    public BaseResponse deleteMovie(@RequestParam Integer mid) {
        return movieService.deleteMovie(mid);
    }

    //获取指定电影
    @GetMapping("/get-movie")
    @ResponseBody
    public BaseResponse getMovie(@RequestParam Integer mid) {
        return movieService.getMovie(mid);
    }

    //修改
    @PostMapping("/update-movie")
    @ResponseBody
    public BaseResponse updateMovie(@RequestBody MovieRequest request) {
        return movieService.updateMovie(request);
    }
}
