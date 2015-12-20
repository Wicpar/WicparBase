package com.wicpar.wicparbase.physics;

import org.joml.Vector3d;

/**
 * Created by Frederic on 04/10/2015 at 13:32.
 */
public interface IPhysical extends IDynamical
{
	/**
	 * Should be used to update the Physical properties of the object (EG shape, position)
	 * @param delta
	 */
	void UpdatePhysicals(double delta);
	void bindForce(IForce force);
	void unbindForce(IForce force);
	void ApplyForce(Vector3d force);
	void ApplyImpulse(Vector3d impulse);
	void SetVel(Vector3d vel);
	void SetPos(Vector3d pos);
	double getMass();
	double getVolume();
	Vector3d getPos();
	Vector3d getCenterOfMass();
	Vector3d getVel();
	AABB getAABB();

}
