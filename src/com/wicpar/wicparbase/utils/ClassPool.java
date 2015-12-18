package com.wicpar.wicparbase.utils;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Frederic on 18/12/2015 at 16:58.
 */
public class ClassPool
{
	private final LinkedList<Object> objects = new LinkedList<>();
	private final HashMap<Class, LinkedList> classpools = new HashMap<>();
	private boolean shouldUpdate = false;

	public ClassPool()
	{
		CreateClasses(getClass());
	}

	public ClassPool(Class... Handles)
	{
		this();
		CreateClasses(Handles);
	}

	public void addClass(Object... c)
	{
		for (Object o : c)
		{
			objects.add(o);
		}
		shouldUpdate = true;
	}

	public void removeClass(Object... c)
	{
		for (Object o : c)
		{
			objects.remove(o);
		}
		shouldUpdate = true;
	}

	public void RelaodClasses()
	{
		if (shouldUpdate)
			for (Class aClass : classpools.keySet())
			{
				RelaodClass(aClass);
			}
		shouldUpdate = false;
	}

	public void UpdateClass(Updater updater, Class c, Object... params)
	{
		LinkedList<Object> tmp;
		tmp = classpools.get(c);
		if (tmp == null)
		{
			CreateClasses(c);
			tmp = classpools.get(c);
		}
		for (Object o : tmp)
		{
			updater.Update(o, params);
		}
		for (Object o : classpools.get(this.getClass()))
		{
			ClassPool classPool = (ClassPool) o;
			classPool.UpdateClass(updater, c, params);
		}
	}

	private void RelaodClass(Class c)
	{
		LinkedList<Object> tmp;

		tmp = classpools.get(c);
		tmp.clear();
		for (Object object : objects)
		{
			if (c.isInstance(object))
			{
				tmp.add(object);
			}
		}
	}

	private void CreateClasses(Class... c)
	{
		for (Class aClass : c)
		{
			classpools.put(aClass, new LinkedList());
			RelaodClass(aClass);
		}
	}

	public interface Updater
	{
		void Update(Object c, Object... params);
	}

}
