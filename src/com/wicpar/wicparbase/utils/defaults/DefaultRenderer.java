package com.wicpar.wicparbase.utils.defaults;

import com.wicpar.wicparbase.graphics.IDrawable;
import com.wicpar.wicparbase.utils.plugins.IRenderer;
import com.wicpar.wicparbase.utils.timing.Timer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Frederic on 28/09/2015 at 11:53.
 */
public class DefaultRenderer implements IRenderer
{
	private final Map<String,Long> windows = new HashMap<>();
	private long MainWindow;
	private GLFWErrorCallback errorCallback;
	private final LinkedList<IDrawable> drawables = new LinkedList<>();
	private final Timer timer = new Timer(1./30.);

	@Override
	public void Init()
	{
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		if (glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		int WIDTH = 500;
		int HEIGHT = 500;
		long window = glfwCreateWindow(WIDTH, HEIGHT, "Sinking Simulator 2", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center our window
		glfwSetWindowPos(
				window,
				(vidmode.width() - WIDTH) / 2,
				(vidmode.height() - HEIGHT) / 2
		);
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		GL.createCapabilities();

		glfwShowWindow(window);
		windows.put("Main", window);
		MainWindow = window;
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}

	@Override
	public Collection<Long> getWindows()
	{
		return windows.values();
	}

	@Override
	public long getWindow(String name)
	{
		return windows.get(name);
	}

	@Override
	public double getDeltaT()
	{
		return timer.getCurrent(timer.SECOND);
	}

	@Override
	public double getTimeFromStart()
	{
		return timer.getFromStart(Timer.SECOND);
	}

	@Override
	public void preLoop()
	{
		timer.update();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public boolean addDrawable(IDrawable drawable)
	{
		return drawables.add(drawable);
	}

	@Override
	public boolean removeDrawable(IDrawable drawable)
	{
		return drawables.remove(drawable);
	}

	@Override
	public void render()
	{
		for (IDrawable drawable : drawables)
		{
			drawable.draw();
		}
		glfwSwapBuffers(MainWindow);
	}

	@Override
	public void finalize() throws Throwable
	{
		super.finalize();
		for (Long window: windows.values())
			glfwDestroyWindow(window);
		glfwTerminate();
		if (errorCallback != null)
			errorCallback.release();
	}

	@Override
	public boolean getLoopCondition()
	{
		return GLFW.glfwWindowShouldClose(MainWindow) == GL_FALSE;
	}
}
