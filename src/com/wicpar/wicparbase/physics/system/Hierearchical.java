package com.wicpar.wicparbase.physics.system;

import com.wicpar.wicparbase.physics.IParent;
import com.wicpar.wicparbase.physics.IChild;

import java.util.ArrayList;

/**
 * Created by Frederic on 19/11/2015 at 14:18.
 */
public class Hierearchical extends Disposable implements IParent, IChild
{
	protected final ArrayList<IParent> parents = new ArrayList<>();
	protected final ArrayList<IChild> childs = new ArrayList<>();

	@Override
	public boolean addDependent(IChild dependent)
	{
		dependent.OnDependencyAdded(this);
		return childs.add(dependent);
	}

	@Override
	public boolean removeDependent(IChild dependent)
	{
		dependent.OnDependencyRemoved(this);
		return childs.remove(dependent);
	}

	@Override
	public boolean hasDependent(IChild dependent)
	{
		return childs.contains(dependent);
	}

	/**
	 * Should be called by a dependency on every dependent when it gets disposed
	 *
	 * @param dependency
	 */
	@Override
	public void OnDependencyDisposed(IParent dependency)
	{
		parents.remove(dependency);
	}

	@Override
	public void OnDependencyAdded(IParent dependency)
	{
		parents.add(dependency);
	}

	@Override
	public void OnDependencyRemoved(IParent dependency)
	{
		parents.remove(dependency);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		for (IChild child : childs)
			child.OnDependencyDisposed(this);
	}
}
