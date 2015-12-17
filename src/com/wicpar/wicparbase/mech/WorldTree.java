package com.wicpar.wicparbase.mech;

import com.wicpar.wicparbase.oldphysics.system.Hierarchical;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Frederic on 11/12/2015 at 14:55.
 */
public class WorldTree
{
	public static final WorldTree instance = new WorldTree();

	private final LinkedList<Hierarchical> tree = new LinkedList<>();

	private final HashMap<Class, LinkedList<Hierarchical>> tempRegs = new HashMap<>();

	public LinkedList<Hierarchical> getClassesInTree(Class c)
	{
		if (tempRegs.containsKey(c))
			return tempRegs.get(c);
		else
			return null;
	}

	public void updateTemp()
	{
		for (LinkedList<Hierarchical> hierarchicals : tempRegs.values())
			hierarchicals.clear();

		for (Hierarchical hierarchical : tree)
			for (Class aClass : tempRegs.keySet())
				if (aClass.isInstance(hierarchical))
					tempRegs.get(aClass).add(hierarchical);
	}

	private void register(LinkedList<Hierarchical> childs)
	{
		for (Hierarchical hierarchical : childs)
		{
			for (Class aClass : tempRegs.keySet())
				if (aClass.isInstance(hierarchical))
					tempRegs.get(aClass).add(hierarchical);
			register(hierarchical.getChilds());
		}
	}

	public void registerClass(Class aClass)
	{
		tempRegs.put(aClass, new LinkedList<>());
	}

	public void unRegisterClass(Class aClass)
	{
		tempRegs.put(aClass, new LinkedList<>());
	}

	public void addRoot(Hierarchical root)
	{
		tree.add(root);
	}
}
