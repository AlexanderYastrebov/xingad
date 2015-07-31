package com.xing.events.hr.domain;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Alexander Yastrebov
 */
public class EngineerTest {

    @Test
    public void testSpeaking() {
        Engineer e = new Engineer();

        Map<Locale, SkillLevel> m = new HashMap<>();
        m.put(Locale.ENGLISH, SkillLevel.GOOD);
        m.put(Locale.GERMAN, SkillLevel.NO_SKILL);

        e.setSpokenLanguages(m);

        assertTrue(e.isSpeaking(Locale.ENGLISH, SkillLevel.GOOD));
        assertFalse(e.isSpeaking(Locale.GERMAN, SkillLevel.GOOD));
    }

    @Test
    public void testPreferences() {
        Engineer e = new Engineer();
        e.setPreferences(new HashSet<>(Arrays.asList(Preferences.WEB_DEVELOPMENT)));

        assertTrue(e.preferesAll(Preferences.WEB_DEVELOPMENT));
        assertFalse(e.preferesAll(Preferences.WORK_IN_TEAM));
    }

    @Test
    public void testMultiplePreferences() {
        Engineer e = new Engineer();
        e.setPreferences(new HashSet<>(Arrays.asList(Preferences.WEB_DEVELOPMENT, Preferences.WORK_IN_TEAM)));

        assertTrue(e.preferesAll(Preferences.WORK_IN_TEAM, Preferences.WEB_DEVELOPMENT));
    }

    @Test
    public void testFrameworks() {
        Engineer e = new Engineer();

        Map<Framework, SkillLevel> f = new HashMap<>();
        f.put(Framework.SPRING, SkillLevel.EXPERT);
        f.put(Framework.HIBERNATE, SkillLevel.GOOD);
        f.put(Framework.APACHE_WICKET, SkillLevel.NO_SKILL);

        e.setFrameworkSkills(f);

        assertTrue(e.hasFrameWorkSkills(Framework.SPRING, SkillLevel.GOOD));
        assertTrue(e.hasFrameWorkSkills(Framework.HIBERNATE, SkillLevel.GOOD));

        assertFalse(e.hasFrameWorkSkills(Framework.APACHE_WICKET, SkillLevel.GOOD));
    }
}
