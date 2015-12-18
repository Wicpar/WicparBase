package com.wicpar.wicparbase.utils.defaults;

import com.wicpar.wicparbase.physics.IDynamical;
import com.wicpar.wicparbase.physics.IForce;
import com.wicpar.wicparbase.physics.IPhysical;
import com.wicpar.wicparbase.physics.system.Force;
import com.wicpar.wicparbase.physics.system.Hierarchical;
import com.wicpar.wicparbase.physics.system.Physical;
import com.wicpar.wicparbase.utils.Disposable;
import com.wicpar.wicparbase.utils.plugins.IDynamicsHandler;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Frederic on 29/09/2015 at 13:20.
 */
public class DefaultDynamicsHandler implements IDynamicsHandler
{
	protected static final LinkedList<IDynamical> dynamicals = new LinkedList<>();
	protected static final LinkedList<IPhysical> physicals = new LinkedList<>();
	protected static final LinkedList<Physical> Tphysicals = new LinkedList<>();

	protected static final LinkedList<Force> TWorldForces = new LinkedList<>();
	protected static final LinkedList<Force> TForces = new LinkedList<>();
	protected static final LinkedList<IForce> Forces = new LinkedList<>();
	private final boolean useMultithreading = false;

	@Override
	public Object addObject(Object object)
	{
		if (object instanceof Physical)
		{
			for (Force force : TWorldForces)
				((Physical) object).addChild(force);
			return Tphysicals.add((Physical) object);
		}
		if (object instanceof IPhysical)
			return physicals.add((IPhysical) object);

		if (object instanceof IDynamical)
			return dynamicals.add((IDynamical) object);

		if (object instanceof Force)
		{
			boolean b = true;
			if (((Force) object).isWorldForce())
			{
				b = TWorldForces.add((Force) object);
				for (Physical physical : Tphysicals)
					if (!physical.hasChild((Hierarchical) object))
						physical.addChild((Hierarchical) object);
			}
			return TForces.add((Force) object) && b;
		}
		if (object instanceof IForce)
			return Forces.add((IForce) object);
		return false;
	}

	@Override
	public Object removeObject(Object object)
	{
		if (object instanceof Physical)
			return Tphysicals.remove(object);

		if (object instanceof IPhysical)
			return physicals.remove(object);

		if (object instanceof IDynamical)
			return dynamicals.remove(object);

		if (object instanceof Force)
		{
			if (((Force) object).isWorldForce())
			{
				TWorldForces.remove(object);
				for (Physical physical : Tphysicals)
					physical.removeChild((Hierarchical) object);
			}
			return TForces.remove(object);
		}
		if (object instanceof IForce)
			return Forces.remove(object);
		return false;
	}

	@Override
	public int getObjectNum(Class c)
	{
		int i = 0;
		for (IDynamical dynamical : dynamicals)
		{
			if (c.isInstance(dynamical))
				i++;
		}
		for (IPhysical dynamical : physicals)
		{
			if (c.isInstance(dynamical))
				i++;
		}
		for (Physical dynamical : Tphysicals)
		{
			if (c.isInstance(dynamical))
				i++;
		}
		return i;
	}

	@Override
	public void update(double delta)
	{

		for (Iterator<IForce> iterator = Forces.iterator(); iterator.hasNext(); )
		{
			IForce force = iterator.next();
			if (force instanceof Disposable && ((Disposable) force).isDisposed())
				iterator.remove();
			else
			{
				for (Physical tphysical : Tphysicals)
				{
					force.ApplyForce(tphysical, delta);
				}
				for (IPhysical physical : physicals)
				{
					force.ApplyForce(physical, delta);
				}
			}
		}
		for (Iterator<IDynamical> iterator = dynamicals.iterator(); iterator.hasNext(); )
		{
			IDynamical dynamical = iterator.next();
			if (dynamical instanceof Disposable && dynamical.isDisposed())
				iterator.remove();
			else
				dynamical.UpdateForces(delta);
		}
		for (Iterator<IPhysical> iterator = physicals.iterator(); iterator.hasNext(); )
		{
			IPhysical physical = iterator.next();
			if (physical instanceof Disposable && physical.isDisposed())
				iterator.remove();
			else
			{
				physical.UpdateForces(delta);
				physical.UpdatePhysicals(delta);
			}
		}
		for (Iterator<Physical> iterator = Tphysicals.iterator(); iterator.hasNext(); )
		{
			Physical physical = iterator.next();
			if (physical.isDisposed())
				iterator.remove();
			else
				physical.run();
		}

	}
}
