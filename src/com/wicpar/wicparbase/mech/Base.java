package com.wicpar.wicparbase.mech;

import com.wicpar.wicparbase.api.DynamicsHandlerExtension;
import com.wicpar.wicparbase.api.InputHandlerExtension;
import com.wicpar.wicparbase.api.RendererExtension;
import com.wicpar.wicparbase.graphics.IDrawable;
import com.wicpar.wicparbase.physics.IDynamical;
import com.wicpar.wicparbase.physics.IForce;
import com.wicpar.wicparbase.physics.IPhysical;
import com.wicpar.wicparbase.utils.ClassPool;
import com.wicpar.wicparbase.utils.defaults.DefaultChooser;
import com.wicpar.wicparbase.utils.error.ErrorDialog;
import com.wicpar.wicparbase.utils.plugins.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginStateEvent;
import ro.fortsoft.pf4j.PluginStateListener;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frederic on 11/09/2015 at 20:11.
 */
public class Base
{
	private static Logger logger = LoggerFactory.getLogger(Base.class);
	public static final DefaultPluginManager pluginManager = new DefaultPluginManager(new File(PVars.BaseModsFolder));
	private static final List<Injector> injectors = new ArrayList<Injector>();
	private static IModuleChooser chooser = new DefaultChooser();
	private static IRenderer renderer;
	private static IInputHandler inputHandler;
	private static IDynamicsHandler dynamicsHandler;

	private static ClassPool classHandler = new ClassPool(IDynamical.class, IPhysical.class, IForce.class, IDrawable.class);

	public Base()
	{
		boolean created = false;
		logger.info("Setting Game folder in " + PVars.GameFolder);
		File f = new File(PVars.GameFolder);
		if (!f.exists())
		{
			logger.info("Game folder does not exists, creating it...");
			f.mkdirs();
			/*try
			{
				URL website = new URL("https://mega.nz/#!gQFkGArb!JSGbFGCI6Gn98q6vmkBfPHhUuHP18K4D1-oq8y9ZVtM");
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				File zip = new File("SinkingSimulator2FilesTMP.zip");
				FileOutputStream fos = new FileOutputStream(zip);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				logger.info("file in " + zip.getAbsolutePath());
				ZipFile zipFile = new ZipFile(zip);
				zipFile.extractAll(f.getAbsolutePath());
			} catch (Exception e)
			{
				logger.warn("Failed to retrieve game files: ", e);
			}*/
			created = true;
		}
		f = new File(PVars.BaseModsFolder);
		if (!f.exists())
		{
			logger.info("Plugin folder does not exists, creating it...");
			f.mkdirs();
			created = true;
		}
		f = new File(PVars.Natives);
		if (!f.exists())
		{
			logger.info("Natives folder does not exists, creating it...");
			f.mkdirs();
			created = true;
		}
		if (created)
			logger.info("Done creating folders.");
	}

	public void run()
	{
		pluginManager.loadPlugins();
		pluginManager.startPlugins();

		pluginManager.addPluginStateListener(new PluginStateListener()
		{
			@Override
			public void pluginStateChanged(PluginStateEvent event)
			{
				logger.info("Plugin states changed. Reloading Handlers.");
				loadHandlers();
			}
		});
		loadHandlers();

		logger.debug("Executing OnGamePreInit();");
		for (Injector j : injectors)
		{
			j.OnGamePreInit();
		}
		renderer.Init();
		inputHandler.MakeCallbacks(renderer.getWindow("Main"));
		logger.debug("Executing OnGamePostInit();");
		for (Injector j : injectors)
		{
			j.OnGamePostInit();
		}
		while (renderer.getLoopCondition())
		{
			renderer.preLoop();
			inputHandler.PollEvents();
			dynamicsHandler.update(renderer.getDeltaT());
			renderer.render();
			classHandler.ReloadClasses();
		}
		logger.debug("Executing OnGameFinish();");
		for (Injector j : injectors)
		{
			j.OnGameFinish();
		}
		inputHandler.StaticClean();
	}

	public void loadHandlers()
	{
		logger.info("Loading Injectors");
		injectors.clear();
		injectors.addAll(pluginManager.getExtensions(Injector.class));
		logger.debug("Executing OnHandlerPreInit();");
		for (Injector j : injectors)
		{
			j.OnHandlerPreInit();
		}
		logger.debug("Finished executing OnHandlerPreInit();");


		logger.info("Loading Handlers...");

		dynamicsHandler = chooser.chooseDynamicsHandler(pluginManager.getExtensions(DynamicsHandlerExtension.class));
		if (dynamicsHandler != null)
			logger.debug(getClassPluginID(dynamicsHandler.getClass()).equals("NotAPlugin") ? "Loaded DynamicsHandler" : "Loaded DynamicsHandler from Plugin: " + getClassPluginID(dynamicsHandler.getClass()));
		else
		{
			logger.error("Dynamics handler is Null: abort. The Module chooser must have been overwritten externally. Please complain to that modules author.");
			ErrorDialog.Send("Dynamics handler is Null: abort. The Module chooser must have been overwritten externally. Please complain to the responsible module's author.");
		}

		renderer = chooser.chooseRenderer(pluginManager.getExtensions(RendererExtension.class));
		if (renderer != null)
			logger.debug(getClassPluginID(renderer.getClass()).equals("NotAPlugin") ? "Loaded Renderer" : "Loaded Renderer from Plugin: " + getClassPluginID(renderer.getClass()));
		else
		{
			logger.error("Renderer is Null: abort. The Module chooser must have been overwritten externally. Please complain to that modules author.");
			ErrorDialog.Send("Renderer is Null: abort. The Module chooser must have been overwritten externally. Please complain to the responsible module's author.");
		}
		inputHandler = chooser.chooseInputHandler(pluginManager.getExtensions(InputHandlerExtension.class));
		if (inputHandler != null)
			logger.debug(getClassPluginID(inputHandler.getClass()).equals("NotAPlugin") ? "Loaded InputHandler" : "Loaded InputHandler from Plugin: " + getClassPluginID(inputHandler.getClass()));
		else
		{
			logger.error("Input handler is Null: abort. The Module chooser must have been overwritten externally. Please complain to the reponsible module's author.");
			ErrorDialog.Send("Input handler is Null: abort. The Module chooser must have been overwritten externally. Please complain to the responsible module's author.");
		}

		logger.debug("Executing OnHandlerPostInit();");
		for (Injector j : injectors)
		{
			j.OnHandlerPostInit();
		}
	}

	public static IModuleChooser getChooser()
	{
		return chooser;
	}

	public static IRenderer getRenderer()
	{
		return renderer;
	}

	public static IInputHandler getInputHandler()
	{
		return inputHandler;
	}

	public static IDynamicsHandler getDynamicsHandler()
	{
		return dynamicsHandler;
	}


	public static ClassPool getClassHandler()
	{
		return classHandler;
	}

	public static String getClassPluginID(Class c)
	{
		PluginWrapper w = pluginManager.whichPlugin(c);
		if (w == null)
			return "NotAPlugin";
		else
			return w.getPluginId();
	}
}
