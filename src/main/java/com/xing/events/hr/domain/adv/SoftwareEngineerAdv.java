package com.xing.events.hr.domain.adv;

import com.xing.events.hr.domain.Advertisment;
import com.xing.events.hr.domain.Engineer;
import com.xing.events.hr.domain.Framework;
import com.xing.events.hr.domain.Preferences;
import com.xing.events.hr.domain.SkillLevel;
import java.util.Locale;

/**
 *
 * Class that provides the decision process if any given {@link Engineer} should
 * apply at the open position at XING EVENTS
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
 * @author jobs@xing-events.com
 *
 * @see http://www.xing-events.com
 *
 */
public class SoftwareEngineerAdv implements Advertisment {

    private static final int MINIMUM_BONUS_SKILLS = 2;

    @Override
    public String getPositionName() {
        return "Software Engineer";
    }

    /**
     *
     * Calculates if the {@link Engineer} looking at the Ad is qualified for the
     * position.
     *
     */
    @Override
    public boolean fits(Engineer engineer) {

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

}
