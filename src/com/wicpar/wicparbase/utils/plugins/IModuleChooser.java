package com.wicpar.wicparbase.utils.plugins;

import com.wicpar.wicparbase.api.DynamicsHandlerExtension;
import com.wicpar.wicparbase.api.InputHandlerExtension;
import com.wicpar.wicparbase.api.RendererExtension;

import java.util.List;

/**
 * Created by Frederic on 28/09/2015 at 10:55.
 */
public interface IModuleChooser
{
	IRenderer chooseRenderer(List<RendererExtension> renderers);
	IInputHandler chooseInputHandler(List<InputHandlerExtension> inputHandlers);
	IDynamicsHandler chooseDynamicsHandler(List<DynamicsHandlerExtension> dynamicsHandlers);
}
