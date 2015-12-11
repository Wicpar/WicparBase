package com.wicpar.wicparbase.physics;

/**
 * Created by Frederic on 10/10/2015 at 17:36.
 */
public interface IParent
{
	boolean addDependent(IChild dependent);
	boolean removeDependent(IChild dependent);
	boolean hasDependent(IChild dependent);
}
