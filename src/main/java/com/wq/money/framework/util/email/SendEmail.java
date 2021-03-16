package com.wq.money.framework.util.email;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class SendEmail {
    private static final Logger log = LoggerFactory.getLogger(SendEmail.class);

    @Value("${email.nickName}")
    private String nickName;
    @Value("${email.userName}")
    private String userName;
    @Value("${email.passWord}")
    private String passWord;
    @Value("${email.proToCol}")
    private String proToCol;
    @Value("${email.host}")
    private String host;
    @Value("${email.port}")
    private String port;

    public boolean sendEmail(EmailBean email) throws Exception{
        if (null == email){
            email = createDefaultEmail();
        }
        try {
            Session session = createSession();
            MimeMessage message = createMessage(session, email);
            log.info("发送邮件中......");
            Transport.send(message);
        } catch (Exception e){
            throw e;
        }
        return true;
    }
    private Session createSession() {
        log.info("-------------->"+nickName);
        // 封装属性参数
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", proToCol);
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.auth", "true"); // 是否需要验证设置为TRUE，使用授权码发送邮件需要验证
        // 获取与服务器的会话Session对象
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 登陆账号及密码，密码需要是第三方授权码
                return new PasswordAuthentication(userName, passWord);
            }
        });
        return session;
    }
    private MimeMessage createMessage(Session session, EmailBean email) throws Exception {
        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置发件人
        if (!StringUtils.isBlank(nickName)) {
            // 自定义发件人昵称
            message.setFrom(new InternetAddress(MimeUtility.encodeText(nickName) + " <" + userName + ">"));
        } else {
            message.setFrom(new InternetAddress(userName));
        }

        // 设置收件人
        String[] toEmails = email.getToEmails();
        if (toEmails != null && toEmails.length != 0) {
            InternetAddress[] internetAddressTO = new InternetAddress[toEmails.length];
            for (int i = 0; i < toEmails.length; i++) {
                internetAddressTO[i] = new InternetAddress(toEmails[i]);
            }
            message.setRecipients(Message.RecipientType.TO, internetAddressTO);
        }

        // 设置密送人
        String[] bccEmails = email.getBccEmails();
        if (bccEmails != null && bccEmails.length != 0) {
            InternetAddress[] internetAddressBCC = new InternetAddress[bccEmails.length];
            for (int i = 0; i < bccEmails.length; i++) {
                internetAddressBCC[i] = new InternetAddress(bccEmails[i]);
            }
            message.setRecipients(Message.RecipientType.BCC, internetAddressBCC);
        }

        // 设置抄送人
        String[] ccEmails = email.getCcEmails();
        if (ccEmails != null && ccEmails.length != 0) {
            InternetAddress[] internetAddressCC = new InternetAddress[ccEmails.length];
            for (int i = 0; i < ccEmails.length; i++) {
                internetAddressCC[i] = new InternetAddress(ccEmails[i]);
            }
            message.setRecipients(Message.RecipientType.CC, internetAddressCC);
        }

        // 设置发生日期
        message.setSentDate(new Date());

        // 设置邮件主题
        message.setSubject(email.getSubject());

        /* 创建用于组合邮件正文和附件的MimeMultipart对象 */
        MimeMultipart multipart = new MimeMultipart();

        // 设置HTML内容
        MimeBodyPart content = createContent(email.getContent(), email.getImagesMap());
        multipart.addBodyPart(content);

        // 设置附件
        String[] attachments = email.getAttachments();
        if (attachments != null && attachments.length != 0) {
            for (String filename : attachments) {
                MimeBodyPart attachPart = createAttachment(filename);
                multipart.addBodyPart(attachPart);
            }
        }
        // 将组合的MimeMultipart对象设置为整个邮件的内容，要注意调用saveChanges方法进行更新
        message.setContent(multipart);
        // 保存并生成最终的邮件内容
        message.saveChanges();
        return message;
    }
    private MimeBodyPart createContent(String body, Map<String, String> imagesMap) throws Exception {
        /* 创建代表组合MIME消息的MimeMultipart对象和该对象保存到的MimeBodyPart对象 */
        MimeBodyPart content = new MimeBodyPart();
        // 创建一个MimeMultipart对象
        MimeMultipart multipart = new MimeMultipart();
        if (!body.isEmpty()) {
            // 创建一个表示HTML正文的MimeBodyPart对象，并将它加入到前面的创建的MimeMultipart对象中
            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(body, "text/html;charset=UTF-8");
            multipart.addBodyPart(htmlBodyPart);
        }
        if (imagesMap != null && !imagesMap.isEmpty()) {
            for (Map.Entry<String, String> entry : imagesMap.entrySet()) {
                /* 创建一个表示图片的MimeBodyPart对象，并将它加入到前面的创建的MimeMultipart对象中 */
                MimeBodyPart pictureBodyPart = new MimeBodyPart();
                // FileDataSource用于读取文件数据，并返回代表数据的输入输出流和数据的MIME类型
                FileDataSource fileDataSource = new FileDataSource(entry.getValue());
                // DataHandler类用于封装FileDataSource对象，并为应用程序提供访问数据的接口
                pictureBodyPart.setDataHandler(new DataHandler(fileDataSource));
                pictureBodyPart.setContentID(entry.getKey());
                multipart.addBodyPart(pictureBodyPart);
            }
        }
        // 将MimeMultipart对象保存到MimeBodyPart对象中
        content.setContent(multipart);
        return content;
    }
    private MimeBodyPart createAttachment(String filepath) throws Exception {
        /* 创建一个表示附件的MimeBodyPart对象，并加入附件内容以及相应的信息 */
        MimeBodyPart attachPart = new MimeBodyPart();
        // FileDataSource用于读取文件数据，并返回代表数据的输入输出流和数据的MIME类型
        FileDataSource fileDataSource = new FileDataSource(filepath);
        // DataHandler类用于封装FileDataSource对象，并为应用程序提供访问数据的接口
        attachPart.setDataHandler(new DataHandler(fileDataSource));
        // 设置附件名称,MimeUtility.encodeText可以处理乱码问题
        attachPart.setFileName(MimeUtility.encodeText(fileDataSource.getName()));
        return attachPart;
    }
    private EmailBean createDefaultEmail(){
        EmailBean bean = new EmailBean();
        bean.setToEmails(new String [] {"1486382928@qq.com","t-qiang.wang@pcitc.com"});
        bean.setSubject("系统默认主题");
        bean.setContent(getBody());
        bean.setAttachments(new String[] {"E:\\docker.txt"});
        Map<String,String> imagesMap = new HashMap<>();
        imagesMap.put("logo","E:\\1295091_135113554551_2.jpg");
        bean.setImagesMap(imagesMap);
        return bean;
    }
    private String getBody(){
        String body = "<h4>HTML+附件+内嵌图片的邮件测试！！！</h4></br><a href=http://www.apache.org>" + "点击跳转</a>"
                + "<h4>LOGO图标</h4></hr><img src=\"cid:logo\">";


        body = "<html><head>\r\n" +
                "<base target=\"_blank\">\r\n" +
                "<style type=\"text/css\">\r\n" +
                "::-webkit-scrollbar{ display: none; }\r\n" +
                "</style>\r\n" +
                "<style id=\"cloudAttachStyle\" type=\"text/css\">\r\n" +
                "#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}\r\n" +
                "</style>\r\n" +
                "\r\n" +
                "</head>\r\n" +
                "<body tabindex=\"0\" role=\"listitem\">\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                " <span id=\"9999\" style=\"display: none !important; font-size:0; line-height:0\">绑定邮箱成功</span>\r\n" +
                " \r\n" +
                "    &nbsp;\r\n" +
                "   \r\n" +
                "    &nbsp;\r\n" +
                "   \r\n" +
                "    &nbsp;\r\n" +
                "   <table cellpadding=\"0\" cellspacing=\"0\" style=\"border: 1px solid #cdcdcd; width: 640px; margin:auto;font-size: 12px; color: #1E2731; line-height: 20px;\"> \r\n" +
                "  <tbody>\r\n" +
                "   <tr> \r\n" +
                "    <td colspan=\"3\" align=\"center\" style=\"background-color:#454c6d; height: 55px; padding: 30px 0\"><a href=\"https://www.huobipro.com\" target=\"_blank\"><img src=\"http://hbpic5.com/huobipro_static/images/20171103/logo_white.png\" alt=\"火币pro\"></a></td> \r\n" +
                "   </tr> \r\n" +
                "   <tr style=\"height: 30px;\"></tr> \r\n" +
                "   <tr> \r\n" +
                "    <td width=\"20\"></td> \r\n" +
                "    <td style=\"line-height: 40px\"> 您好，<br> 您的火币账号已成功绑定邮箱。<br> 如果您没有进行该操作，请立即修改登录密码。<br> </td> \r\n" +
                "    <td width=\"20\"></td> \r\n" +
                "   </tr> \r\n" +
                "   <tr style=\"height: 20px;\"></tr> \r\n" +
                "   <tr> \r\n" +
                "    <td width=\"20\"></td> \r\n" +
                "    <td> <br> 火币团队<br> <a href=\"https://www.huobipro.com\">https://www.huobipro.com</a><br> </td> \r\n" +
                "    <td width=\"20\"></td> \r\n" +
                "   </tr> \r\n" +
                "   <tr style=\"height: 50px;\"></tr> \r\n" +
                "  </tbody>\r\n" +
                " </table>\r\n" +
                "\r\n" +
                "<style type=\"text/css\">\r\n" +
                "body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}\r\n" +
                "td, input, button, select, body{font-family:Helvetica, 'Microsoft Yahei', verdana}\r\n" +
                "pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}\r\n" +
                "th,td{font-family:arial,verdana,sans-serif;line-height:1.666}\r\n" +
                "img{ border:0}\r\n" +
                "header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}\r\n" +
                "blockquote{margin-right:0px}\r\n" +
                "</style>\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                "<style id=\"ntes_link_color\" type=\"text/css\">a,td a{color:#1D6CA3}</style>\r\n" +
                "\r\n" +
                "</body></html>";
        return body;
    }
}
