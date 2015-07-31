package com.xing.events.hr.domain;

/**
 *
 * @author Alexander Yastrebov
 */
public interface Advertisment {

    String getPositionName();

    boolean fits(Engineer engineer);
}
