package com.wicpar.wicparbase.utils.defaults;

import com.wicpar.wicparbase.api.DynamicsHandlerExtension;
import com.wicpar.wicparbase.api.InputHandlerExtension;
import com.wicpar.wicparbase.api.RendererExtension;
import com.wicpar.wicparbase.mech.Base;
import com.wicpar.wicparbase.utils.plugins.IDynamicsHandler;
import com.wicpar.wicparbase.utils.plugins.IInputHandler;
import com.wicpar.wicparbase.utils.plugins.IModuleChooser;
import com.wicpar.wicparbase.utils.plugins.IRenderer;

import java.util.List;

/**
 * Created by Frederic on 28/09/2015 at 11:48.
 */
public class DefaultChooser implements IModuleChooser
{

	@Override
	public IRenderer chooseRenderer(List<RendererExtension> renderers)
	{
		if (renderers.size() == 0)
			return Base.getRenderer() == null ? new DefaultRenderer() : Base.getRenderer();
		else
			return Base.getRenderer() == null ? renderers.get(0) : Base.getRenderer();
	}

	@Override
	public IInputHandler chooseInputHandler(List<InputHandlerExtension> inputHandlers)
	{
		if (inputHandlers.size() == 0)
			return Base.getInputHandler() == null ? new DefaultInputHandler() : Base.getInputHandler();
		else
			return Base.getInputHandler() == null ? inputHandlers.get(0) : Base.getInputHandler();
	}

	@Override
	public IDynamicsHandler chooseDynamicsHandler(List<DynamicsHandlerExtension> dynamicsHandlers)
	{
		if (dynamicsHandlers.size() == 0)
			return Base.getDynamicsHandler() == null ? new DefaultDynamicsHandler() : Base.getDynamicsHandler();
		else
			return Base.getDynamicsHandler() == null ? dynamicsHandlers.get(0) : Base.getDynamicsHandler();
	}
}
