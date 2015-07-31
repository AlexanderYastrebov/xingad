package com.xing.events.hr;

import com.xing.events.hr.domain.Preferences;
import com.xing.events.hr.domain.Framework;
import com.xing.events.hr.domain.SkillLevel;
import com.xing.events.hr.domain.Engineer;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author Alexander Yastrebov
 */
public class TestUtils {

    private TestUtils() {
    }

    public static Engineer newHighSkilledEngineer() {
        Engineer alex = new Engineer();

        try {
            alex.setEmailAddress(new InternetAddress("Alexander Yastrebov <yastrebov.alex@gmail.com>"));
        } catch (AddressException ex) {
            throw new RuntimeException(ex);
        }

        alex.setGreetingText("Hello!");
        alex.setCoverLetterText("My name is Alexander Yastrebov.");
        alex.setSalarayExpectationText("To be discussed.");

        Map<Locale, SkillLevel> langs = new HashMap<>();
        langs.put(Locale.ENGLISH, SkillLevel.BUSINESS);
        langs.put(Locale.GERMAN, SkillLevel.NO_SKILL);

        alex.setSpokenLanguages(langs);

        Map<Framework, SkillLevel> f = new HashMap<>();
        f.put(Framework.SPRING, SkillLevel.EXPERT);
        f.put(Framework.HIBERNATE, SkillLevel.GOOD);
        f.put(Framework.APACHE_WICKET, SkillLevel.NO_SKILL);

        alex.setFrameworkSkills(f);

        alex.setPreferences(new HashSet<>(Arrays.asList(Preferences.WEB_DEVELOPMENT, Preferences.WORK_IN_TEAM)));
        alex.setJavaLevel(SkillLevel.EXPERT);
        alex.setWritesTestCases(true);
        alex.setOpenSourceContributionLinks(Arrays.asList("https://github.com/neo4j/neo4j/pull/330", "https://netbeans.org/bugzilla/show_bug.cgi?id=185733"));

        try {
            alex.setCV(new ByteArrayDataSource("My CV", "text/plain"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return alex;
    }
}
