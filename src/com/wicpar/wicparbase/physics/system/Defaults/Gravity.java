package com.wicpar.wicparbase.physics.system.Defaults;

import com.wicpar.wicparbase.physics.IPhysical;
import com.wicpar.wicparbase.physics.system.Force;

/**
 * Created by Frederic on 19/11/2015 at 15:44.
 */
public class Gravity extends Force
{

	/**
	 * called every frame to apply force on a physical object. the extensions should be made thread safe.
	 *
	 * @param physical the physical to apply a force to.
	 * @param delta
	 */
	@Override
	public boolean ApplyForce(IPhysical physical, double delta)
	{
		physical.getVel().add(0,-9.81 * delta, 0);
		return false;
	}

}
