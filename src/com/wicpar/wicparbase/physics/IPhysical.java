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
	void applyForce(Vector3d force);
	void applyImpulse(Vector3d impulse);
	void move(Vector3d trans);
	void setVel(Vector3d vel);
	void setPos(Vector3d pos);
	double getMass();
	double getVolume();
	Vector3d getPos();
	Vector3d getCenterOfMass();
	Vector3d getVel();
	AABB getAABB();

}
