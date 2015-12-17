package com.wicpar.wicparbase.utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Frederic on 17/12/2015 at 19:17.
 */
public abstract class Island
{
	private final ArrayList<Class> classes = new ArrayList<>();

	public Island(Class... classes)
	{
		for (Class aClass : classes)
		{
			this.classes.add(aClass);
		}
	}

	public boolean hasClass(Class c)
	{
		return classes.contains(c);
	}

	public abstract void UpdateClass(Class c);
}
