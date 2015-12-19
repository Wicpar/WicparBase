package com.wicpar.wicparbase.utils.plugins;

import com.wicpar.wicparbase.graphics.IDrawable;
import com.wicpar.wicparbase.utils.Disposable;

import java.util.Collection;

/**
 * Created by Frederic on 26/09/2015 at 18:04.
 */
public interface IRenderer
{
	void Init();
	Collection<Long> getWindows();
	long getWindow(String name);
	void render(double delta);
	boolean getLoopCondition();
}
