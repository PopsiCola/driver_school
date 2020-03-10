package com.llb.service.impl;

import com.llb.service.MailService;
import com.llb.utils.VerifycodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件服务接口实现
 * @Author llb
 * Date on 2020/3/10
 */
@Service
public class MailServiceImpl implements MailService{

    @Autowired
    private JavaMailSender mailSender;

    //邮件发件人
    @Value("${mail.fromMail.addr}")
    private String from;

    @Autowired
    TemplateEngine templateEngine;

    @Override
    public Map<String, Object> sendMail(String to, String subject) {
        Map<String, Object> result = new HashMap<>();

        //生成随机验证码
        VerifycodeUtil verifyCode = new VerifycodeUtil();
        int code = verifyCode.randomCode();

        //创建邮件正文
        Context context = new Context();
        context.setVariable("verifyCode", code);

        //将模板内容解析成html
        String template = templateEngine.process("emailTemplate", context);

        //设置邮件信息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(code+"");

        try {
            //邮件发送
            mailSender.send(message);
            result.put("code", 200);
            result.put("verifyMailCode", code);
            result.put("msg", "邮件发送成功，请注意查收");
        } catch (Exception e) {
            result.put("code", 201);
            result.put("msg", "邮件发送异常");

        }

        return  result;
    }
}
