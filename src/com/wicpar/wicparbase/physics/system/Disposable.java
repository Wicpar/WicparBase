package com.wicpar.wicparbase.physics.system;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Frederic on 19/11/2015 at 14:17.
 */
public class Disposable implements com.wicpar.wicparbase.utils.Disposable
{
	private boolean disposed = false;

	@Override
	public void dispose()
	{
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

}
