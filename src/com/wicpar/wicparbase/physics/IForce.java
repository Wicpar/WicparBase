package com.wicpar.wicparbase.physics;

/**
 * Created by Frederic on 13/11/2015 at 19:02.
 */
public interface IForce
{
	/**
	 * called every frame to apply force on a physical object. the extensions should be made thread safe.
	 * @param physical the physical to apply a force to.
	 * @param delta
	 */
	void ApplyForce(IPhysical physical, double delta);
}
