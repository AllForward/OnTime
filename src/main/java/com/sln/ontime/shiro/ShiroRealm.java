package com.sln.ontime.shiro;

import com.alibaba.druid.util.StringUtils;
import com.sln.ontime.exception.ErrorException;
import com.sln.ontime.model.po.UserPo;
import com.sln.ontime.model.vo.UserVo;
import com.sln.ontime.service.WechatService;
import com.sln.ontime.shiro.token.WechatToken;
import com.sln.ontime.util.VerifyUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @description
 * @author guopei
 * @date 2020-04-30 23:45
 */
@Log4j2
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private WechatService wechatService;

    /**
     * shiro授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AuthorizationInfo info = new SimpleAuthorizationInfo();
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        log.info("primaryPrincipal:{}", primaryPrincipal);
        info.getStringPermissions().add("all");
        return info;
    }

    /**
     * shiro鉴权  openid 判断是否用户是否已经绑定微信
     * @param token
     * @return
     * @throws AuthenticationException
     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        if (VerifyUtil.isNull(token)) {
//            log.info("前端传过来的token为空");
//            throw new AuthenticationException("网络连接异常，请稍后重试");
//        }
//        if (token instanceof WechatToken) {
//
//            WechatToken wechatToken = (WechatToken) token;
//            if (VerifyUtil.isNull(token)) {
//                throw new AuthenticationException("网络连接异常，请稍后重试");
//            }
//            log.info("进入shiro执行方法，用户通过微信登录,code为 " + wechatToken.getCode());
//            try {
//                UserVo userVo = wechatService.login(wechatToken.getCode());
//                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(userVo, "ok", this.getClass().getSimpleName());
//                return authcInfo;
//            } catch (ErrorException e) {
//                throw new AuthenticationException(e.getMessage());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    /**
     * shiro鉴权
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (VerifyUtil.isNull(token)) {
            log.info("前端传过来的token为空");
            throw new AuthenticationException("网络连接异常，请稍后重试");
        }

        if (token instanceof WechatToken) {

            WechatToken wechatToken = (WechatToken) token;
            log.info("进入shiro执行方法，用户通过了前端微信授权");
            try {
                UserPo userPo = wechatService.login(wechatToken);
                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(userPo, "ok", this.getClass().getSimpleName());
                return authcInfo;
            } catch (ErrorException e) {
                throw new AuthenticationException(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
