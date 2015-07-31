package com.xing.events.hr.service;

import com.xing.events.hr.domain.Advertisment;
import com.xing.events.hr.domain.Engineer;

/**
 *
 * @author Alexander Yastrebov
 */
public interface JobService {

    /**
     *
     * Makes the given Engineer apply for the advertised position.
     */
    void apply(Engineer engineer, Advertisment advertisment);
}
