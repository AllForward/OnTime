package com.sln.ontime.service;

import com.sln.ontime.model.dto.ResultBean;
import com.sln.ontime.model.vo.UserVo;
import com.sln.ontime.shiro.token.WechatToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface WechatService {

    UserVo login(WechatToken wechatToken) throws IOException;



}
