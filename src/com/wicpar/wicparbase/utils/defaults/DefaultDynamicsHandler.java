package com.wicpar.wicparbase.utils.defaults;

import com.wicpar.wicparbase.mech.Base;
import com.wicpar.wicparbase.physics.IDynamical;
import com.wicpar.wicparbase.physics.IForce;
import com.wicpar.wicparbase.physics.IPhysical;
import com.wicpar.wicparbase.utils.plugins.IDynamicsHandler;

import java.util.LinkedList;

/**
 * Created by Frederic on 29/09/2015 at 13:20.
 */
public class DefaultDynamicsHandler implements IDynamicsHandler
{
	private final boolean useMultithreading = false;
	private final LinkedList<IForce> forces = new LinkedList<>();

	@Override
	public void update(double delta)
	{
		if (Base.getClassHandler().hasUpdated())
		{
			forces.clear();
			Base.getClassHandler().UpdateClass((c, params) -> {
				forces.add((IForce) c);
			}, IForce.class);
		}

		Base.getClassHandler().UpdateClass((c, params) -> {
			for (IForce force : forces)
			{
				force.ApplyForce((IPhysical) c, (Double) params[0]);
			}
		}, IPhysical.class, delta);

		Base.getClassHandler().UpdateClass((c, params) -> {
			((IDynamical) c).UpdateForces((Double) params[0]);
		}, IDynamical.class, delta);

		Base.getClassHandler().UpdateClass((c, params) -> {
			((IPhysical) c).UpdatePhysicals((Double) params[0]);
		}, IPhysical.class, delta);

	}
}
