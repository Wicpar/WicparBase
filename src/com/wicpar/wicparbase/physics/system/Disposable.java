package com.wicpar.wicparbase.physics.system;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Frederic on 19/11/2015 at 14:17.
 */
public class Disposable implements com.wicpar.wicparbase.utils.Initializable
{
	private static final List<WeakReference<com.wicpar.wicparbase.utils.Disposable>> disposables = Collections.synchronizedList(new LinkedList<>());
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

	@Override
	public void Initialize()
	{
		disposables.add(new WeakReference<>(this));
	}
	public static void cleanse()
	{
		for (WeakReference<com.wicpar.wicparbase.utils.Disposable> disposable : disposables)
		{
			if (disposable.get() != null && !disposable.get().isDisposed())
				disposable.get().dispose();
		}
	}

}
