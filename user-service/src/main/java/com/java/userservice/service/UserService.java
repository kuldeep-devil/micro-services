package com.java.userservice.service;

import com.java.userservice.VO.Department;
import com.java.userservice.VO.ResponseTemplateVO;
import com.java.userservice.entity.User;
import com.java.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("inside UserService saveUser" ,user);
        return userRepository.save(user);
    }

    public ResponseTemplateVO findUserWithDepartment(Long userId) {
        log.info("inside UserService getUserWithDepartment" ,userId);
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);

        Department department = restTemplate.getForObject("http://localhost:9001/departments/"+user.getDepartmentId(), Department.class);

        restTemplate.getForObject("http://localhost:9001/departments/"+user.getDepartmentId(), Department.class);

        responseTemplateVO.setUser(user);
        responseTemplateVO.setDepartment(department);
        return responseTemplateVO;
    }
}
