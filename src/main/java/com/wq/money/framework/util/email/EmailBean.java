package com.wq.money.framework.util.email;

import lombok.*;

import java.util.Map;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmailBean {
    private String[] toEmails; // 发送人列表
    private String[] bccEmails;// 密送人泪飙
    private String[] ccEmails;// 抄送人列表
    private String subject;// 主题
    private String content;// 内容
    private String[] attachments;// 附件
    private Map<String, String> imagesMap;// 图片
}
