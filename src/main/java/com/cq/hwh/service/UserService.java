package com.cq.hwh.service;

import com.cq.hwh.domain.User;
import com.cq.hwh.domain.UserExample;
import com.cq.hwh.exception.BusinessException;
import com.cq.hwh.exception.BusinessExceptionCode;
import com.cq.hwh.mapper.UserMapper;
import com.cq.hwh.req.UserLoginReq;
import com.cq.hwh.req.UserQueryReq;
import com.cq.hwh.req.UserResetPasswordReq;
import com.cq.hwh.req.UserSaveReq;
import com.cq.hwh.resp.PageResp;
import com.cq.hwh.resp.UserLoginResp;
import com.cq.hwh.resp.UserQueryResp;
import com.cq.hwh.util.CopyUtil;
import com.cq.hwh.util.SnowFlake;
import com.cq.hwh.util.TokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    public UserLoginResp login(UserLoginReq req) {
        User userDb = selectByLoginName(req.getLoginName());
        if (ObjectUtils.isEmpty(userDb)) {
            //用户名不存在
            LOG.info("用户名不存在,{}", req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (userDb.getPassword().equals(req.getPassword())) {
                //登录成功
                String token = TokenUtil.signToken(userDb);
                UserLoginResp resp = CopyUtil.copy(userDb, UserLoginResp.class);
                resp.setToken(token);
                return resp;
            } else {
                //密码错误
                LOG.info("密码错误，输入密码：{},数据库密码：{}", req.getPassword(), userDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }

    public PageResp<UserQueryResp> list(UserQueryReq req, HttpServletRequest request) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameEqualTo(req.getLoginName());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        /*List<UserResp> respList = new ArrayList<>();
        for (User user : userList) {
            UserResp userResp = CopyUtil.copy(user,UserResp.class);
            respList.add(userResp);
        }*/

        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(CopyUtil.copyList(userList, UserQueryResp.class));
        LOG.info("进行本次操作的用户id为：{}", TokenUtil.getUserByToken(request));

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req, User.class);
        if (ObjectUtils.isEmpty(selectByLoginName(req.getLoginName()))) {
            //新增
            user.setId(snowFlake.nextId() / 10000);
            userMapper.insert(user);
        } else {
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
    }

    /**
     * 修改密码
     */
    public void resetPassword(UserResetPasswordReq req) {
        User user = CopyUtil.copy(req, User.class);
        if (req.getId() != null) {
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            throw new BusinessException(BusinessExceptionCode.RESET_USER_ERROR);
        }
    }

    /**
     * 删除
     */
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询登录名
     */
    public User selectByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }

}
