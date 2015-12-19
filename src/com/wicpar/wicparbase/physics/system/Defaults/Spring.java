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
		a.bindForce(this);
		b.bindForce(this);
	}

	@Override
	public boolean ApplyForce(IPhysical physical, double delta)
	{
		if (isDisposed())
			return true;
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
		Vector3d x = new Vector3d(posA).sub(posB).normalize().mul(stretch).mul(stiffness).sub(new Vector3d(velB).sub(velA).mul(dampening));
		lastForce = x.length();
		if (physical == a)
		{
			physical.getVel().add(new Vector3d(x).negate().div(physical.getMass()).mul(delta));
		}
		if (physical == b)
		{
			physical.getVel().add(new Vector3d(x).div(physical.getMass()).mul(delta));
		}
		return false;
	}
}
