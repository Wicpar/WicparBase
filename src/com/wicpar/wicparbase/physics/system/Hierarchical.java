package com.wicpar.wicparbase.physics.system;

import java.util.LinkedList;

/**
 * Created by Frederic on 19/11/2015 at 14:18.
 */
public class Hierarchical extends Disposable
{
	protected final LinkedList<Hierarchical> parents = new LinkedList<>();

	protected final LinkedList<Hierarchical> childs = new LinkedList<>();

	public LinkedList<Hierarchical> getParents()
	{
		return parents;
	}

	public LinkedList<Hierarchical> getChilds()
	{
		return childs;
	}

	public boolean addChild(Hierarchical dependent)
	{
		dependent.OnParentAdded(this);
		return childs.add(dependent);
	}

	public boolean removeChild(Hierarchical dependent)
	{
		dependent.OnParentRemoved(this);
		return childs.remove(dependent);
	}

	public boolean hasChild(Hierarchical dependent)
	{
		return childs.contains(dependent);
	}

	/**
	 * Should be called by a dependency on every dependent when it gets disposed
	 *
	 * @param dependency
	 */
	public void OnParentRemoved(Hierarchical dependency)
	{
		parents.remove(dependency);
	}

	public void OnParentAdded(Hierarchical dependency)
	{
		parents.add(dependency);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		for (Hierarchical child : childs)
			child.OnParentRemoved(this);
	}
}
