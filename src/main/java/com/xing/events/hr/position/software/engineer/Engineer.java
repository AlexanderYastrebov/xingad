package com.xing.events.hr.position.software.engineer;

import java.util.Locale;
import javax.activation.DataSource;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Alexander Yastrebov
 */
public class Engineer {

    public static final int SKILL_LEVEL_BASIC = 1;
    public static final int SKILL_LEVEL_GOOD = 2;
    public static final int SKILL_LEVEL_EXPERT = 3;
    //
    public static final int PREFERENCES_WEB_DEVELOPMENT = 1;
    public static final int PREFERENCES_WORK_IN_TEAM = 2;

    public boolean isSpeaking(Locale ENGLISH, Language language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasJavaSkills(int level) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean preferes(int PREFERENCES_WEB_DEVELOPMENT, int PREFERENCES_WORK_IN_TEAM) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean writesTestCases() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasFrameWorkSkills(Framework f, int level) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isOpenSourceContributor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public InternetAddress getEmailAddress() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getGreetingText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getCoverLetterText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getSalarayExpectationText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getOpenSourceContributionLinks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public DataSource getCVAsDataSource() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public DataSource getWorkReferencesAsDataSource() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
