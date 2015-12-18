package com.wicpar.wicparbase.physics.system.Defaults;

import com.wicpar.wicparbase.physics.IPhysical;
import com.wicpar.wicparbase.physics.system.Force;
import com.wicpar.wicparbase.physics.system.Physical;
import org.joml.Vector3d;

/**
 * Created by Frederic on 19/11/2015 at 15:53.
 */
public class Spring extends Force
{

	protected Physical a,b;
	protected double dampening, stiffness, dst, lastForce;

	public Spring(Physical a, Physical b, double dampening, double stiffness)
	{
		this.a = a;
		this.b = b;
		this.dampening = dampening;
		this.stiffness = stiffness;
		dst = a.getPos().distance(b.getPos());

	}

	/**
	 * called every frame to apply force on a physical object. the extensions should be made thread safe.
	 *
	 * @param physical the physical to apply a force to.
	 * @param delta
	 */
	@Override
	public synchronized void ApplyForce(IPhysical physical, double delta)
	{
		Vector3d posA, posB, velA, velB;
		synchronized (a){
			posA = new Vector3d(a.getPos());
			velA = new Vector3d(a.getVel());
		}
		synchronized (b){
			posB = new Vector3d(b.getPos());
			velB = new Vector3d(b.getVel());
		}
		double stretch = posA.distance(posB) - dst;
		lastForce = stiffness * stretch;

		if (physical == a)
			physical.getVel().add(new Vector3d(new Vector3d().set(posB).sub(posA).normalize().mul(lastForce).add(new Vector3d(velB).sub(velA).mul(dampening))).div(physical.getMass()).mul(delta));
		if (physical == b)
			physical.getVel().add(new Vector3d(new Vector3d().set(posA).sub(posB).normalize().mul(lastForce).add(new Vector3d(velA).sub(velB).mul(dampening))).div(physical.getMass()).mul(delta));
	}
}
