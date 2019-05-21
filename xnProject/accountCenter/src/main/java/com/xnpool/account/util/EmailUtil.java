package com.xnpool.account.util;

import com.xnpool.account.entity.EmailSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

/**
 * FileName:       EmailUtil
 * Author:         Administrator
 * Date:           2019/5/17 10:21
 * Description:
 */
@Component
@Slf4j
public class EmailUtil {

    public static void sendMail(String to, String code,EmailSetting emailSetting) {
        // 1.创建连接对象，链接到邮箱服务器
        Properties props = new Properties();// 参数配置
        props.setProperty("mail.transport.protocol", emailSetting.getProtocol());// 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", emailSetting.getSmtp());// 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", emailSetting.getAuth());// 需要请求认证
//        props.setProperty("mail.smtp.starttls.enable","false");
        props.setProperty("mail.smtp.port", emailSetting.getPort());
        props.setProperty("mail.smtp.socketFactory.class", emailSetting.getSocketFactory());
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", emailSetting.getPort());
        props.setProperty("default-encoding",emailSetting.getDefaultEncoding());
        // 2.根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSetting.getUsername(),emailSetting.getPassword());
            }
        });

        try {
            // 3.创建邮件对象
            Message message = new MimeMessage(session);
            // 3.1设置发件人
            message.setFrom(new InternetAddress(emailSetting.getUsername()));
            // 3.2设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 3.3设置邮件的主题
            message.setSubject("来自小鸟矿池的验证邮件");
            // 3.4设置邮件的正文
            //message.setContent("<h1>来自智慧电梯的激活邮件，您的验证码是：</h1><h3><a href='http://localhost:10080/Demo_JavaMail/active?code=" + code + "'>http://localhost:10080/Demo_JavaMail/active?code=" + code + "</h3>", "text/html;charset=UTF-8");
            message.setContent("<h1>来自小鸟矿池的验证邮件，您的验证码是："+ code+" 验证码5分钟内有效", "text/html;charset=UTF-8");
            // 4.发送邮件
            Transport.send(message);
            log.info("发送邮箱成功");
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    public static String createCode(){
        String str="0123456789";
        StringBuilder stringBuilder = new StringBuilder(6);
        for(int i=0;i<6;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            stringBuilder.append(ch);
        }
        return String.valueOf(stringBuilder);
    }

}
