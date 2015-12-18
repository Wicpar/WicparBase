package com.wicpar.wicparbase.physics.system;

import com.wicpar.wicparbase.mech.Base;
import com.wicpar.wicparbase.physics.IForce;
import com.wicpar.wicparbase.physics.IPhysical;

/**
 * Created by Frederic on 19/11/2015 at 14:38.
 */
public abstract class Force extends Hierarchical implements IForce
{

	@Override
	public void Initialize()
	{
		super.Initialize();
		Base.getDynamicsHandler().addObject(this);
	}
	/**
	 * called every frame to apply force on a physical object.
	 *
	 * @param physical the physical to apply a force to.
	 * @param delta
	 */
	@Override
	public abstract void ApplyForce(IPhysical physical, double delta);

	@Override
	public abstract boolean isWorldForce();

	@Override
	public void dispose()
	{
		super.dispose();
		Base.getDynamicsHandler().removeObject(this);
	}
}
