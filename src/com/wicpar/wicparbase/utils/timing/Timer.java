package com.wicpar.wicparbase.utils.timing;

/**
 * Created by Frederic on 11/09/2015 at 22:29.
 */
public class Timer
{
	private long FTime, TTime;
	private final long minDelta;
	private long curdelta;

	public Timer(double minDelta)
	{
		TTime = 0;
		FTime = System.nanoTime();
		this.minDelta = (long) (minDelta * 1000000000);
		curdelta = this.minDelta;
	}

	public void update()
	{
		curdelta = System.nanoTime() - FTime;
		FTime += curdelta;
		curdelta = Math.min(curdelta, minDelta);
		TTime += curdelta;
	}

	public long getDeltaNano()
	{
		return curdelta;
	}

	public double getDelta()
	{
		return (double)curdelta / 1000000000;
	}

	public long getFromStartNano()
	{
		return TTime;
	}

	public double getFromStart()
	{
		return (double)TTime / 1000000000;
	}
}
