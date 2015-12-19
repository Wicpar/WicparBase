package com.wicpar.wicparbase.physics.system.Defaults;

import com.wicpar.wicparbase.physics.IPhysical;
import org.joml.Vector3d;

/**
 * Created by Frederic on 19/11/2015 at 18:14.
 */
public class DragForce extends com.wicpar.wicparbase.physics.system.Force
{
	protected double drag;
	public DragForce(double drag)
	{
		this.drag = drag;
	}

	/**
	 * called every frame to apply force on a physical object. the extensions should be made thread safe.
	 *
	 * @param physical the physical to apply a force to.
	 * @param delta
	 */
	@Override
	public boolean ApplyForce(IPhysical physical, double delta)
	{
		Vector3d vel = new Vector3d(physical.getVel());
		physical.getVel().sub(vel.mul(drag * delta));
		return false;
	}
}
