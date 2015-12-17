package com.wicpar.wicparbase.oldphysics;

import com.wicpar.wicparbase.utils.Disposable;

/**
 * Created by Frederic on 04/10/2015 at 13:32.
 */
public interface IDynamical extends Disposable
{
	/**
	 * should be used to update the forces.
	 * Is called first
	 * @param delta the time length of the frame in seconds
	 */
	void UpdateForces(double delta);

}
