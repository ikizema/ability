package com.ability.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;
import com.ability.imaging.Copmarator;

/**
 * Created by ikizema on 15-09-11.
 */
@Stateless
public class ServiceImageComparator {
    @Inject
    private Logger logger;

    public Copmarator SaqVineComparator = new Copmarator("./DATA/avro/saq_vine_150902.parquet");


}
