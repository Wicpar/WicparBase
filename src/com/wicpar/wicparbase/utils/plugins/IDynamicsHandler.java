package com.wicpar.wicparbase.utils.plugins;

import com.wicpar.wicparbase.physics.IDynamical;

import java.util.List;

/**
 * Created by Frederic on 26/09/2015 at 18:05.
 */
public interface IDynamicsHandler
{
	Object addObject(Object object);
	Object removeObject(Object object);
	int getObjectNum(Class c);
	void update(double delta);
}
