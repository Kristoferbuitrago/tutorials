package com.github.shansun.sparrow.actor.statistic;

/**
 * 引自j2ee-5: {@link javax.management.j2ee.statistics.Statistic}
 * 
 * @author: lanbo <br>
 * @version: 1.0 <br>
 * @date: 2012-8-13
 */
public interface Statistic {

	String getName();

	String getUnit();

	String getDescription();

	long getStartTime();

	long getLastSampleTime();
}
