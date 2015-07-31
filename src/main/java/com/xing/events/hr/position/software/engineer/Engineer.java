package com.xing.events.hr.position.software.engineer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.activation.DataSource;
import javax.mail.internet.InternetAddress;
import org.springframework.util.Assert;

/**
 *
 * @author Alexander Yastrebov
 */
public class Engineer {

    private Map<Locale, SkillLevel> spokenLanguages = Collections.emptyMap();
    private Map<Framework, SkillLevel> frameworkSkills = Collections.emptyMap();
    private Set<Preferences> preferences = Collections.emptySet();
    private SkillLevel javaLevel = SkillLevel.NO_SKILL;
    //
    private boolean writesTestCases = false;
    private boolean openSourceContributor = false;
    //
    private InternetAddress emailAddress;
    private String greetingText;
    private String coverLetterText;
    private String salarayExpectationText;
    private DataSource cv;
    private Optional<DataSource> workReferences;

    public void setSpokenLanguages(Map<Locale, SkillLevel> spokenLanguages) {
        Assert.notNull(spokenLanguages);
        this.spokenLanguages = spokenLanguages;
    }

    public void setFrameworkSkills(Map<Framework, SkillLevel> frameworkSkills) {
        Assert.notNull(frameworkSkills);
        this.frameworkSkills = frameworkSkills;
    }

    public void setPreferences(Set<Preferences> preferences) {
        Assert.notNull(preferences);
        this.preferences = preferences;
    }

    public void setJavaLevel(SkillLevel javaLevel) {
        Assert.notNull(javaLevel);
        this.javaLevel = javaLevel;
    }

    public void setWritesTestCases(boolean writesTestCases) {
        this.writesTestCases = writesTestCases;
    }

    public void setOpenSourceContributor(boolean openSourceContributor) {
        this.openSourceContributor = openSourceContributor;
    }

    public void setEmailAddress(InternetAddress emailAddress) {
        Assert.notNull(emailAddress);
        this.emailAddress = emailAddress;
    }

    public void setGreetingText(String greetingText) {
        Assert.hasText(greetingText);
        this.greetingText = greetingText;
    }

    public void setCoverLetterText(String coverLetterText) {
        Assert.hasText(coverLetterText);
        this.coverLetterText = coverLetterText;
    }

    public void setSalarayExpectationText(String salarayExpectationText) {
        Assert.hasText(salarayExpectationText);
        this.salarayExpectationText = salarayExpectationText;
    }

    public void setCV(DataSource cv) {
        Assert.notNull(cv);
        this.cv = cv;
    }

    public void setWorkReferences(DataSource workReferences) {
        this.workReferences = Optional.ofNullable(workReferences);
    }

    public boolean isSpeaking(Locale lang, SkillLevel level) {
        Assert.notNull(lang);
        Assert.notNull(level);

        return spokenLanguages.getOrDefault(lang, SkillLevel.NO_SKILL).compareTo(level) >= 0;
    }

    public boolean hasJavaSkills(SkillLevel level) {
        Assert.notNull(level);

        return javaLevel.compareTo(level) >= 0;
    }

    public boolean preferesAll(Preferences... prefs) {
        Assert.notNull(prefs);

        return preferences.containsAll(Arrays.asList(prefs));
    }

    public boolean writesTestCases() {
        return writesTestCases;
    }

    public boolean hasFrameWorkSkills(Framework f, SkillLevel level) {
        Assert.notNull(f);
        Assert.notNull(level);

        return frameworkSkills.getOrDefault(f, SkillLevel.NO_SKILL).compareTo(level) >= 0;
    }

    public boolean isOpenSourceContributor() {
        return openSourceContributor;
    }

    public InternetAddress getEmailAddress() {
        return emailAddress;
    }

    public String getGreetingText() {
        return greetingText;
    }

    public String getCoverLetterText() {
        return coverLetterText;
    }

    public String getSalarayExpectationText() {
        return salarayExpectationText;
    }

    public Object getOpenSourceContributionLinks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public DataSource getCVAsDataSource() {
        return cv;
    }

    public Optional<DataSource> getWorkReferences() {
        return workReferences;
    }

    @Override
    public String toString() {
        return "Engineer{"
                + "spokenLanguages=" + spokenLanguages
                + ", frameworkSkills=" + frameworkSkills
                + ", preferences=" + preferences
                + ", javaLevel=" + javaLevel
                + ", writesTestCases=" + writesTestCases
                + ", openSourceContributor=" + openSourceContributor
                + ", emailAddress=" + emailAddress
                + ", greetingText=" + greetingText
                + ", coverLetterText=" + coverLetterText
                + ", salarayExpectationText=" + salarayExpectationText
                + ", cv=yes"
                + ", workReferences=" + (workReferences.isPresent() ? "yes" : "no")
                + '}';
    }
}
