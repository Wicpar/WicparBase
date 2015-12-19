package com.wicpar.wicparbase.physics.system;

import com.wicpar.wicparbase.mech.Base;
import com.wicpar.wicparbase.physics.AABB;
import com.wicpar.wicparbase.physics.IForce;
import com.wicpar.wicparbase.physics.IPhysical;
import org.joml.Vector3d;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Frederic on 19/11/2015 at 14:39.
 */
public class Physical extends Disposable implements IPhysical, Runnable
{
	protected Vector3d pos, vel;
	protected double mass, vol = 1;

	private final LinkedList<IForce> forces = new LinkedList<>();

	public Physical(Vector3d pos, Vector3d vel, double mass, double vol)
	{
		this.pos = pos;
		this.vel = vel;
		this.mass = mass;
		this.vol = vol;
	}
	public Physical(Vector3d pos, Vector3d vel, double mass)
	{
		this(pos, vel, mass, 1);
	}
	public Physical(Vector3d pos, double mass)
	{
		this(pos, new Vector3d(), mass, 1);
	}

	/**
	 * Should be used to update the Physical properties of the object (EG shape, position)
	 *
	 * @param delta
	 */
	@Override
	public void UpdatePhysicals(double delta)
	{
		pos.add(new Vector3d(vel).mul(delta));
	}

	@Override
	public void bindForce(IForce force)
	{
		forces.add(force);
	}

	@Override
	public void unbindForce(IForce force)
	{
		forces.remove(force);
	}

	@Override
	public void ApplyForce(Vector3d force, double DeltaT)
	{
		vel.add(new Vector3d(force).div(mass).mul(DeltaT));
	}

	@Override
	public void ApplyImpulse(Vector3d impulse)
	{
		vel.add(new Vector3d(impulse).div(mass));
	}

	@Override
	public void SetVel(Vector3d vel)
	{
		this.vel.set(vel);
	}

	@Override
	public void SetPos(Vector3d pos)
	{
		this.pos.set(pos);
	}

	@Override
	public double getMass()
	{
		return mass;
	}

	@Override
	public double getVolume()
	{
		return vol;
	}

	@Override
	public Vector3d getPos()
	{
		return pos;
	}

	@Override
	public Vector3d getCenterOfMass()
	{
		return new Vector3d(pos);
	}

	@Override
	public Vector3d getVel()
	{
		return vel;
	}

	@Override
	public AABB getAABB()
	{
		return new AABB(pos);
	}

	/**
	 * should be used to update the forces.
	 * Is called first
	 *
	 * @param delta the time length of the frame in seconds
	 */
	@Override
	public void UpdateForces(double delta)
	{
		for (Iterator<IForce> iterator = forces.iterator(); iterator.hasNext(); )
		{
			IForce force = iterator.next();
			if (force.ApplyForce(this, delta))
				iterator.remove();
		}
	}

	@Override
	public void run()
	{
		double delta = Base.getDelta();
		UpdateForces(delta);
		UpdatePhysicals(delta);
	}
}
