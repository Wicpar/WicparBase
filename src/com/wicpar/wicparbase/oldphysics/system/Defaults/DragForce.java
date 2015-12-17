package com.wicpar.wicparbase.oldphysics.system.Defaults;

import com.wicpar.wicparbase.oldphysics.IPhysical;
import org.joml.Vector3d;

/**
 * Created by Frederic on 19/11/2015 at 18:14.
 */
public class DragForce extends com.wicpar.wicparbase.oldphysics.system.Force
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
	public void ApplyForce(IPhysical physical, double delta)
	{
		Vector3d vel = new Vector3d(physical.getVel());
		physical.getVel().sub(vel.mul(drag * delta));
	}

	@Override
	public boolean isWorldForce()
	{
		return true;
	}
}
