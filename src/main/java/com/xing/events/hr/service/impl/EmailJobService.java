package com.xing.events.hr.service.impl;

import com.xing.events.hr.service.JobService;
import com.xing.events.hr.domain.Advertisment;
import com.xing.events.hr.domain.Engineer;
import com.xing.events.hr.service.exception.ServiceException;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

/**
 *
 * @author Alexander Yastrebov
 */
public class EmailJobService implements JobService {

    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void apply(Engineer engineer, Advertisment adv) {
        Assert.notNull(engineer);
        Assert.notNull(adv);

        if (!adv.fits(engineer)) {
            throw new ServiceException("Engineer " + engineer + " doesn't fit the job " + adv);
        }

        MimeMessage email = mailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(email, true, "UTF-8");

            messageHelper.setTo("jobs@xing-events.com");
            messageHelper.setFrom(engineer.getEmailAddress());
            messageHelper.setSubject("Application as " + adv.getPositionName());

            StringBuilder text = new StringBuilder(1024);

            text.append(engineer.getGreetingText()).append("\n");
            text.append(engineer.getCoverLetterText()).append("\n");
            text.append(engineer.getSalarayExpectationText()).append("\n");

            if (engineer.isOpenSourceContributor()) {
                text.append(
                        engineer.getOpenSourceContributionLinks().stream()
                        .collect(Collectors.joining("\n"))
                );
            }

            messageHelper.setText(text.toString());

            messageHelper.addAttachment("CV.pdf", engineer.getCVAsDataSource());

            Optional<DataSource> workReferences = engineer.getWorkReferences();
            if (workReferences.isPresent()) {
                messageHelper.addAttachment("References.pdf", workReferences.get());
            }
        } catch (MessagingException ex) {
            throw new ServiceException("Can't create message", ex);
        }

        mailSender.send(email);
    }
}
