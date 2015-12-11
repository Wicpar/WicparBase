package com.wicpar.wicparbase.utils.timing;

/**
 * Created by Frederic on 11/09/2015 at 22:29.
 */
public class Timer
{
	public static final int SECOND = 1000000000;
	public static final int MILI = 1000000;
	public static final int NANO = 1;

	private final long STime;
	private long FTime;
	private final double minDelta;
	private double curdelta;

	public Timer(double minDelta)
	{
		STime = System.nanoTime();
		FTime = STime;
		this.minDelta = minDelta;
		curdelta = minDelta;
	}

	public void update()
	{
		curdelta = Math.max((((double) (System.nanoTime() - FTime)) / SECOND),minDelta);
		FTime += getCurrent(NANO);
	}

	public double getCurrent(int unit)
	{
		return (unit == SECOND) ? curdelta : ((curdelta * SECOND) / unit);
	}

	public double getFromStart(int unit)
	{
		return (((double) (FTime - STime)) / unit);
	}
}
