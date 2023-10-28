package com.pn.controller;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;
import com.pn.pojo.*;
import com.pn.service.AuthService;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Resource(name = "captchaProducer")
    private Producer producer;

    //注入redis模板
    @Autowired
    private StringRedisTemplate redisTemplate;

    //生成验证码图片
    @RequestMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response){
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);
        redisTemplate.opsForValue().set(text,"",120,TimeUnit.SECONDS);

        ServletOutputStream out = null;
        //响应给前端
        response.setContentType("image/jpeg");
        try {
            out = response.getOutputStream();
            ImageIO.write(image,"jpg",out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    //用户登录
    @RequestMapping("/login")
    public Result login(@RequestBody LoginUser loginUser){
        //校验验证码
        String code = loginUser.getVerificationCode();
        if (!redisTemplate.hasKey(code)){
            return Result.err(Result.CODE_ERR_BUSINESS,"验证码错误！");
        }

        User user = userService.queryUserByCode(loginUser.getUserCode());
        if (user.getUserCode().equals(loginUser.getUserCode())){
            if (user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)){
                String userPwd = loginUser.getUserPwd();
                String pwd = DigestUtil.hmacSign(userPwd);
                if (pwd.equals(user.getUserPwd())){
                    CurrentUser currentUser = new CurrentUser(user.getUserId(),user.getUserCode(),user.getUserName());
                    String token = tokenUtils.loginSign(currentUser, pwd);
                    return Result.ok("登陆成功",token);
                }else{
                    return Result.err(Result.CODE_ERR_BUSINESS,"密码错误！");
                }
            }else{
                return Result.err(Result.CODE_ERR_BUSINESS,"审核未通过！");
            }
        }else{
            return Result.err(Result.CODE_ERR_BUSINESS,"账号错误！");
        }
    }

    //获取用户登录信息
    @RequestMapping("/curr-user")
    public Result currentUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        return Result.ok(currentUser);
    }




    //退出登录
    @RequestMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //从redis中删除当前登录用户的token
        redisTemplate.delete(token);
        //响应
        return Result.ok("退出系统！");
    }





//    //生成验证码图片的接口
//    @RequestMapping("/captcha/captchaImage")
//    public void captchaImage(HttpServletResponse response){
//        ServletOutputStream out = null;
//        try {
//
//            //生成验证码文本
//            String text = producer.createText();
//            //把验证码文本放到验证码图片里 BufferedImage对象 缓存图片
//            BufferedImage image = producer.createImage(text);
//            //把验证码文本存到redis中
//            redisTemplate.opsForValue().set(text,"",120,TimeUnit.SECONDS);
//
//            //把验证码图片响应给前端
//            //设置响应全文的类型image/jpeg
//            response.setContentType("image/jpeg");
//            out = response.getOutputStream();
//            //使用响应对象的字节输出流写入验证码图片，给前端写入
//            ImageIO.write(image,"jpg",out);
//            //刷新
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if (out!=null){
//                try {
//                    //关闭流
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    /**
//     * 登录的URL接口
//     * @param @RequestBody loginUser -- 表示接受并封装前端传过来的登录的用户信息的json数据
//     * 返回值Result对象 -- 表示向前端响应响应结果Result对象转成的json串，包含响应状态码 成功失败响应 响应信息 响应数据
//     */
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private TokenUtils tokenUtils;
//
//    @RequestMapping("/login")
//    public Result login(@RequestBody LoginUser loginUser){
//        //校验验证码
//        String code = loginUser.getVerificationCode();
//        if (!redisTemplate.hasKey(code)){
//            return Result.err(Result.CODE_ERR_BUSINESS,"验证码错误！");
//        }
//        //根据用户名查询
//        User user = userService.queryUserByCode(loginUser.getUserCode());
//        if (user.getUserCode().equals(loginUser.getUserCode())){
//            if (user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)){
//                String userPwd = loginUser.getUserPwd();
//                String pwd = DigestUtil.hmacSign(userPwd);//拿到前端传的密码并加密
//                if (pwd.equals(user.getUserPwd())){
//                    CurrentUser currentUser = new CurrentUser(user.getUserId(),user.getUserCode(),user.getUserName());
//                    String token = tokenUtils.loginSign(currentUser, pwd);
//                    //客户端响应
//                    return Result.ok("登录成功",token);
//                }else{
//                    return Result.err(Result.CODE_ERR_BUSINESS,"密码错误！");
//                }
//            }else{
//                return Result.err(Result.CODE_ERR_BUSINESS,"未审核！");
//            }
//        }else{
//            return Result.err(Result.CODE_ERR_BUSINESS,"用户名错误！");
//        }
//    }
//
//
//    /**
//     * 获取当前登录用户的登录信息的url接口
//     * 参数@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token --
//     * 表示将请求头Token的值(前端归还的token赋值给请求处理方法的入参变量token);
//     * @param token
//     * @return
//     */
//    @RequestMapping("/curr-user")
//    public Result currentUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
//        //解析token 拿到了封装了当前用户信息的CurrentUser对象
//        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
//        //响应
//        return Result.ok(currentUser);
//    }

}
