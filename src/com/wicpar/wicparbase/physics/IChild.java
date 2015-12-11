package com.wicpar.wicparbase.physics;

/**
 * Created by Frederic on 10/10/2015 at 17:37.
 */
public interface IChild
{
	/**
	 * Should be called by a dependency on every dependent when it gets disposed
	 * @param dependency
	 */
	void OnDependencyDisposed(IParent dependency);
	void OnDependencyAdded(IParent dependency);
	void OnDependencyRemoved(IParent dependency);
}
