package com.xing.events.hr.position.software.engineer;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 *
 *
 * Class that provides the decision process if any given {@link Engineer} should
 * apply at the open position at XING EVENTS, as well as
 *
 * the opportunity to send an application.
 *
 *
 *
 *
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
 *
 *
 */
public class SoftwareEngineerAd implements Advertisment {

    protected static final int MINIMUM_BONUS_SKILLS = 2;

    private Engineer engineer;

    @Autowired
    private JavaMailSender mailSender;

    public SoftwareEngineer(Engineer engineer) {

        this.engineer = engineer;

    }

    /**
     *
     * Calculates if the {@link Engineer} looking at the Ad is qualified for the
     * position.
     *
     */
    @Override
    public boolean fitsJobDescription() {

        if (!engineer.isSpeaking(Locale.ENGLISH, Language.SKILL_LEVEL_BUSINESS)) {

            return false;

        }

        if (!engineer.hasJavaSkills(Engineer.SKILL_LEVEL_EXPERT)) {

            return false;

        }

        if (!engineer.preferes(Engineer.PREFERENCES_WEB_DEVELOPMENT, Engineer.PREFERENCES_WORK_IN_TEAM)) {

            return false;

        }

        if (!engineer.writesTestCases()) {

            return false;

        }

                               //bonus requirements.
        int bonusSkills = 0;

        if (engineer.hasFrameWorkSkills(Framework.HIBERNATE, Engineer.SKILL_LEVEL_GOOD)) {

            bonusSkills++;

        }

        if (engineer.hasFrameWorkSkills(Framework.SPRING, Engineer.SKILL_LEVEL_GOOD)) {

            bonusSkills++;

        }

        if (engineer.hasFrameWorkSkills(Framework.APACHE_WICKET, Engineer.SKILL_LEVEL_BASIC)) {

            bonusSkills++;

        }

        if (engineer.isOpenSourceContributor()) {

            bonusSkills++;

        }

        if (engineer.isSpeaking(Locale.GERMAN, Language.SKILL_LEVEL_BUSINESS)) {

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

        MimeMessageHelper messageHelper = new MimeMessageHelper(email, "UTF-8");

        messageHelper.setTo("jobs@xing-events.com");

        messageHelper.setFrom(engineer.getEmailAddress());

        messageHelper.setSubject("Application as " + getClass().getName());

        StringBuffer emailTextBuffer = new StringBuffer(1024);

        emailTextBuffer.append(engineer.getGreetingText()).append(engineer.getCoverLetterText()).append(engineer.getSalarayExpectationText());

        if (engineer.isOpenSourceContributor()) {

            emailTextBuffer.append(engineer.getOpenSourceContributionLinks());

        }

        messageHelper.setText(emailTextBuffer.toString());

        messageHelper.addAttachment("CV.pdf", engineer.getCVAsDataSource());

        messageHelper.addAttachment("References.pdf", engineer.getWorkReferencesAsDataSource());

        mailSender.send(email);

    }

}
