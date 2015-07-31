package com.xing.events.hr.position.software.engineer;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * Class that provides the decision process if any given {@link Engineer} should
 * apply at the open position at XING EVENTS, as well as
 *
 * the opportunity to send an application.
 *
 * XING EVENTS is a XING AG subsidiary that currently employs over 75 people and
 * offers professional event organizers an award-winning software for online
 * ticketing
 *
 * and event marketing. We are an innovative, modern and enthusiastic company
 * and our main goal is to create memorable events in cooperation with our
 * customers.
 *
 * Always keeping our vision “For people moments that matters” in mind.
 *
 *
 *
 * @author jobs@xing-events.com
 *
 * @see http://www.xing-events.com
 *
 */
public class SoftwareEngineerAd implements Advertisment {

    private static final int MINIMUM_BONUS_SKILLS = 2;

    private final Engineer engineer;

    @Autowired
    private JavaMailSender mailSender;

    public SoftwareEngineerAd(Engineer engineer) {
        this.engineer = engineer;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     *
     * Calculates if the {@link Engineer} looking at the Ad is qualified for the
     * position.
     *
     */
    @Override
    public boolean fitsJobDescription() {

        boolean matchesBasic = engineer.isSpeaking(Locale.ENGLISH, SkillLevel.BUSINESS)
                && engineer.hasJavaSkills(SkillLevel.EXPERT)
                && engineer.preferesAll(Preferences.WEB_DEVELOPMENT, Preferences.WORK_IN_TEAM)
                && engineer.writesTestCases();

        if (!matchesBasic) {
            return false;
        }

        //bonus requirements.
        int bonusSkills = 0;

        if (engineer.hasFrameWorkSkills(Framework.HIBERNATE, SkillLevel.GOOD)) {
            bonusSkills++;
        }

        if (engineer.hasFrameWorkSkills(Framework.SPRING, SkillLevel.GOOD)) {
            bonusSkills++;
        }

        if (engineer.hasFrameWorkSkills(Framework.APACHE_WICKET, SkillLevel.BASIC)) {
            bonusSkills++;
        }

        if (engineer.isOpenSourceContributor()) {
            bonusSkills++;
        }

        if (engineer.isSpeaking(Locale.GERMAN, SkillLevel.BUSINESS)) {
            bonusSkills++;
        }

        if (bonusSkills < MINIMUM_BONUS_SKILLS) {
            return false;
        }

        return true;
    }

    /**
     *
     * Makes the given Engineer apply for the position.
     *
     * Will throw a RuntimeException if {@link #fitsJobDescription()} returns
     * false
     *
     */
    @Override
    public void apply() {
        if (!fitsJobDescription()) {
            throw new HRRuntimeException("Engineer doesn't fit the job description: " + engineer.toString());
        }

        MimeMessage email = mailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(email, true, "UTF-8");

            messageHelper.setTo("jobs@xing-events.com");
            messageHelper.setFrom(engineer.getEmailAddress());
            messageHelper.setSubject("Application as " + getClass().getSimpleName());

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
            throw new HRRuntimeException(ex);
        }

        mailSender.send(email);
    }
}
