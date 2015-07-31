package com.xing.events.hr.domain.adv;

import com.xing.events.hr.TestUtils;
import com.xing.events.hr.domain.Preferences;
import com.xing.events.hr.domain.Framework;
import com.xing.events.hr.domain.SkillLevel;
import com.xing.events.hr.domain.adv.SoftwareEngineerAdv;
import com.xing.events.hr.domain.Engineer;
import com.xing.events.hr.domain.Engineer;
import com.xing.events.hr.domain.Framework;
import com.xing.events.hr.domain.Preferences;
import com.xing.events.hr.domain.SkillLevel;
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
public class SoftwareEngineerAdvTest {

    @Test
    public void testAlexanderYastrebov() {
        Engineer alex = TestUtils.newHighSkilledEngineer();

        SoftwareEngineerAdv ad = new SoftwareEngineerAdv();

        assertTrue(ad.fits(alex));
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

        SoftwareEngineerAdv ad = new SoftwareEngineerAdv();

        assertFalse(ad.fits(someone));
    }
}
