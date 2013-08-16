package net.easyUI.common.mail.render;


import net.easyUI.common.mail.context.MailContext;
import net.easyUI.common.mail.template.MailTemplateResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 邮件渲染抽象类
 * 
 */
public abstract class AbstractMailRender implements MailRender {
    
    /** 日志 */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private MailTemplateResolver templateResolver;

    public MailTemplateResolver getTemplateResolver() {
        return templateResolver;
    }

    public void setTemplateResolver(MailTemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    public MailContext render(MailContext context) {
        return doRender(context);
    }
    
    protected abstract MailContext doRender(MailContext context);

}
