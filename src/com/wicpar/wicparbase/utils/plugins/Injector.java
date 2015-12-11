package com.wicpar.wicparbase.utils.plugins;

import ro.fortsoft.pf4j.ExtensionPoint;

/**
 * Created by Frederic on 28/09/2015 at 11:36.
 */
public interface Injector extends ExtensionPoint
{
	void OnHandlerPreInit();
	void OnHandlerPostInit();
	void OnGamePreInit();
	void OnGamePostInit();
	void OnGameFinish();
}
