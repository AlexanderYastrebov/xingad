package com.xing.events.hr.position.software.engineer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Alexander Yastrebov
 */
public class SoftwareEngineerAdTest {

    @Test
    public void testAlexanderYastrebov() {
        Engineer alex = new Engineer();

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
        alex.setOpenSourceContributor(true);

        SoftwareEngineerAd ad = new SoftwareEngineerAd(alex);

        assertTrue(ad.fitsJobDescription());
    }
    
    @Test
    public void testSomeoneElseGood() {
        Engineer someone = new Engineer();

        Map<Locale, SkillLevel> langs = new HashMap<>();
        langs.put(Locale.ENGLISH, SkillLevel.GOOD);
        langs.put(Locale.GERMAN, SkillLevel.GOOD);

        someone.setSpokenLanguages(langs);

        Map<Framework, SkillLevel> f = new HashMap<>();
        f.put(Framework.SPRING, SkillLevel.GOOD);
        f.put(Framework.HIBERNATE, SkillLevel.GOOD);
        f.put(Framework.APACHE_WICKET, SkillLevel.NO_SKILL);

        someone.setFrameworkSkills(f);

        someone.setPreferences(new HashSet<>(Arrays.asList(Preferences.WEB_DEVELOPMENT)));
        someone.setJavaLevel(SkillLevel.GOOD);
        someone.setWritesTestCases(true);
        someone.setOpenSourceContributor(true);

        SoftwareEngineerAd ad = new SoftwareEngineerAd(someone);

        assertFalse(ad.fitsJobDescription());
    }
}
