package com.wicpar.wicparbase.utils.plugins;

import com.wicpar.wicparbase.input.IInput;
import com.wicpar.wicparbase.utils.Disposable;

/**
 * Created by Frederic on 26/09/2015 at 18:15.
 */
public interface IInputHandler
{
	void MakeCallbacks(long window);
	void PollEvents();
	boolean addInput(IInput handler);
	boolean removeInput(IInput handler);
	void StaticClean();
}
