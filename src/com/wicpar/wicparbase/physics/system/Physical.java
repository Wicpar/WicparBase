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
	protected Vector3d pos, vel, force = new Vector3d(), impulse = new Vector3d();
	protected double mass, vol = 1;

	protected final LinkedList<IForce> forces = new LinkedList<>();

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
	public void ApplyForce(Vector3d force)
	{
		force.add(force);
	}

	@Override
	public void ApplyImpulse(Vector3d impulse)
	{
		impulse.add(impulse);
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
		return new Vector3d(pos);
	}

	@Override
	public Vector3d getCenterOfMass()
	{
		return new Vector3d(pos);
	}

	@Override
	public Vector3d getVel()
	{
		return new Vector3d(vel);
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
		vel.add(impulse.mul(delta));
		vel.add(force.div(mass).mul(delta));
		impulse.set(0,0,0);
		force.set(0,0,0);
	}

	@Override
	public void run()
	{
		double delta = Base.getDelta();
		UpdateForces(delta);
		UpdatePhysicals(delta);
	}
}
