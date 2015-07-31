package com.xing.events.hr.position.software.engineer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

/**
 *
 * @author Alexander Yastrebov
 */
public class SoftwareEngineerAdTest {

    private Wiser wiser;
    private JavaMailSenderImpl mailSender;

    @Before
    public void setUp() throws Exception {
        wiser = new Wiser(12345);
        wiser.start();

        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(12345);
    }

    @After
    public void tearDown() throws Exception {
        wiser.stop();
    }

    @Test
    public void testAlexanderYastrebov() {
        Engineer alex = newHighSkilledEngineer();

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

        SoftwareEngineerAd ad = new SoftwareEngineerAd(someone);

        assertFalse(ad.fitsJobDescription());
    }

    @Test
    public void testApply() {
        Engineer alex = newHighSkilledEngineer();

        SoftwareEngineerAd ad = new SoftwareEngineerAd(alex);
        ad.setMailSender(mailSender);

        ad.apply();

        List<WiserMessage> messages = wiser.getMessages();
        assertEquals(1, messages.size());
        WiserMessage m = messages.get(0);

        assertEquals("yastrebov.alex@gmail.com", m.getEnvelopeSender());
        assertEquals("jobs@xing-events.com", m.getEnvelopeReceiver());
        try {
            assertEquals("Application as SoftwareEngineerAd", m.getMimeMessage().getSubject());
            // RFC 2821 #2.3.7 mandates that line termination is CRLF
            assertEquals(
                    "Hello!\r\n"
                    + "My name is Alexander Yastrebov.\r\n"
                    + "To be discussed.\r\n"
                    + "https://github.com/neo4j/neo4j/pull/330\r\n"
                    + "https://netbeans.org/bugzilla/show_bug.cgi?id=185733",
                    getPartContent(m, 0));
            assertEquals("My CV", getPartContent(m, 1));
        } catch (MessagingException | IOException ex) {
            fail();
        }
    }

    private String getPartContent(WiserMessage m, int i) throws MessagingException, IOException {
        return getContent(((MimeMultipart) m.getMimeMessage().getContent()).getBodyPart(i).getContent());
    }

    private String getContent(Object o) throws MessagingException, IOException {
        if (o instanceof Multipart) {
            return getContent(((Multipart) o).getBodyPart(0).getContent());
        } else {
            return o.toString();
        }
    }

    private Engineer newHighSkilledEngineer() {
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
