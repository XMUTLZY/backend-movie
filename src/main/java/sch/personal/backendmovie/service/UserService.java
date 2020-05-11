package sch.personal.backendmovie.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sch.personal.backendmovie.document.UserDocument;
import sch.personal.backendmovie.http.request.UserRequest;
import sch.personal.backendmovie.http.response.BaseResponse;
import sch.personal.backendmovie.http.response.LayerResponse;
import sch.personal.backendmovie.http.vo.User;
import sch.personal.backendmovie.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public LayerResponse userList(Pageable pageable, Integer uid, String userName) {
        LayerResponse response = new LayerResponse();
        List<User> userList = new ArrayList<>();
        UserDocument document = new UserDocument();
        if (uid != null) {
            document.setUid(uid);
        }
        if (StringUtils.hasText(userName)){
            document.setUserName(userName);
        }
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.contains());  //模糊查询userName
        Example<UserDocument> userDocumentExample = Example.of(document, matcher);   //查询条件设置Example
        Page<UserDocument> userDocumentPage = userRepository.findAll(userDocumentExample, pageable);    //条件分页查询
        response.setCount((int) userRepository.count(userDocumentExample));    //条件查询总量
        for (UserDocument userDocument : userDocumentPage) {
            User user = new User();
            BeanUtils.copyProperties(userDocument, user);   //把userDocument的值赋值给user
            userList.add(user);
        }
        response.setData(userList);
        return response;
    }

    //添加用户
    public BaseResponse addUser(UserRequest request) {
        UserDocument userDocument = new UserDocument();
        BeanUtils.copyProperties(request, userDocument);
        userDocument.setFirst(false);
        userDocument.setGenres(Arrays.asList(request.getGenres().split("、")));
        userDocument.setTimestamp(new Date().getTime());
        userRepository.save(userDocument);
        return new BaseResponse();
    }

    //获取指定用户
    public BaseResponse getUser(Integer uid) {
        BaseResponse response = new BaseResponse();
        UserDocument userDocument = userRepository.findByUid(uid);
        User user = new User();
        BeanUtils.copyProperties(userDocument, user);
        response.setVo(user);
        return response;
    }

    //修改用户信息
    public BaseResponse updateUser(UserRequest request) {
        UserDocument userDocument = userRepository.findByUid(request.getUid());
        userDocument.setUserName(request.getUserName());
        userDocument.setPassword(request.getPassword());
        userDocument.setGenres(Arrays.asList(request.getGenres().split("、")));
        userRepository.save(userDocument);
        return new BaseResponse();
    }

    //删除用户
    public BaseResponse deleteUser(Integer uid) {
        userRepository.deleteByUid(uid);
        return new BaseResponse();
    }
}
