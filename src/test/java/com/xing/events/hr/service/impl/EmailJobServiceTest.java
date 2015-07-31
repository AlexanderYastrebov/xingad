package com.xing.events.hr.service.impl;

import com.xing.events.hr.TestUtils;
import com.xing.events.hr.service.impl.EmailJobService;
import com.xing.events.hr.domain.adv.SoftwareEngineerAdv;
import com.xing.events.hr.domain.Engineer;
import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;
import org.junit.After;
import static org.junit.Assert.assertEquals;
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
public class EmailJobServiceTest {

    private EmailJobService jobService;
    private Wiser wiser;

    @Before
    public void setUp() throws Exception {
        wiser = new Wiser(12345);
        wiser.start();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(12345);

        jobService = new EmailJobService();
        jobService.setMailSender(mailSender);
    }

    @After
    public void tearDown() throws Exception {
        wiser.stop();
    }

    @Test
    public void testApply() {
        Engineer alex = TestUtils.newHighSkilledEngineer();

        SoftwareEngineerAdv ad = new SoftwareEngineerAdv();

        jobService.apply(alex, ad);

        List<WiserMessage> messages = wiser.getMessages();
        assertEquals(1, messages.size());
        WiserMessage m = messages.get(0);

        assertEquals("yastrebov.alex@gmail.com", m.getEnvelopeSender());
        assertEquals("jobs@xing-events.com", m.getEnvelopeReceiver());
        try {
            assertEquals("Application as Software Engineer", m.getMimeMessage().getSubject());
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
}
