package com.wicpar.wicparbase.utils.defaults;

import com.wicpar.wicparbase.input.IInput;
import com.wicpar.wicparbase.utils.plugins.IInputHandler;
import org.lwjgl.glfw.*;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;

import static com.wicpar.wicparbase.input.GenericGLFW.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Frederic on 28/09/2015 at 20:37.
 */
public class DefaultInputHandler implements IInputHandler
{

	private final ArrayList<IInput> inputs = new ArrayList<>();
	private final ArrayList<WeakReference<DefaultInputHandler>> instances = new ArrayList<>();

	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCharCallback charCallback;
	private GLFWCharModsCallback charModsCallback;
	private GLFWCursorEnterCallback cursorEnterCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	private GLFWDropCallback dropCallback;
	private GLFWFramebufferSizeCallback framebufferSizeCallback;
	private static GLFWMonitorCallback monitorCallback = null;
	private GLFWScrollCallback scrollCallback;
	private GLFWWindowCloseCallback windowCloseCallback;
	private GLFWWindowIconifyCallback windowIconifyCallback;
	private GLFWWindowFocusCallback windowFocusCallback;
	private GLFWWindowPosCallback windowPosCallback;
	private GLFWWindowRefreshCallback windowRefreshCallback;
	private GLFWWindowSizeCallback windowSizeCallback;
	private GLFWKeyCallback keyCallback;

	public DefaultInputHandler()
	{
		instances.add(new WeakReference<>(this));
	}

	@Override
	public void MakeCallbacks(long window)
	{
		glfwSetMouseButtonCallback(window, mouseButtonCallback = new GLFWMouseButtonCallback()
		{
			@Override
			public void invoke(long l, int i, int i1, int i2)
			{
				InvokeCallback(onMouseButtonCallback, l, i, i1, i2);
			}
		});
		glfwSetCharCallback(window, charCallback = new GLFWCharCallback()
		{
			@Override
			public void invoke(long l, int i)
			{
				InvokeCallback(onCharCallback, l, i);
			}
		});
		glfwSetCharModsCallback(window, charModsCallback = new GLFWCharModsCallback()
		{
			@Override
			public void invoke(long l, int i, int i1)
			{
				InvokeCallback(onCharModsCallback, l, i, i1);
			}
		});
		glfwSetCursorEnterCallback(window, cursorEnterCallback = new GLFWCursorEnterCallback()
		{
			@Override
			public void invoke(long l, int i)
			{
				InvokeCallback(onCursorEnterCallback, l, i);
			}
		});
		glfwSetCursorPosCallback(window, cursorPosCallback = new GLFWCursorPosCallback()
		{
			@Override
			public void invoke(long l, double v, double v1)
			{
				InvokeCallback(onCursorPosCallback, l, v, v1);
			}
		});
		glfwSetDropCallback(window, dropCallback = new GLFWDropCallback()
		{
			@Override
			public void invoke(long l, int i, long l1)
			{
				InvokeCallback(onDropCallback, l, i, l1);
			}
		});
		glfwSetFramebufferSizeCallback(window, framebufferSizeCallback = new GLFWFramebufferSizeCallback()
		{
			@Override
			public void invoke(long l, int i, int i1)
			{
				InvokeCallback(onFramebufferSizeCallback, l, i, i1);
			}
		});
		if (monitorCallback == null)
		glfwSetMonitorCallback(monitorCallback = new GLFWMonitorCallback() {
			@Override
			public void invoke(long l, int i) {
				final LinkedList<WeakReference> todel = new LinkedList<>();
				for (WeakReference<DefaultInputHandler> ref : instances)
				{
					DefaultInputHandler h = ref.get();
					if (h != null)
						h.InvokeCallback(onMonitorCallback, l, i);
					else
						todel.add(ref);
				}
				instances.removeAll(todel);
				todel.clear();
			}
		});
		glfwSetScrollCallback(window, scrollCallback = new GLFWScrollCallback() {
			@Override
			public void invoke(long l, double v, double v1) {
				InvokeCallback(onScrollCallback, l, v, v1);
			}
		});
		glfwSetWindowCloseCallback(window, windowCloseCallback = new GLFWWindowCloseCallback() {
			@Override
			public void invoke(long l) {
				InvokeCallback(onWindowCloseCallback, l);
			}
		});
		glfwSetWindowIconifyCallback(window, windowIconifyCallback = new GLFWWindowIconifyCallback() {
			@Override
			public void invoke(long l, int i) {
				InvokeCallback(onWindowIconifyCallback, l, i);
			}
		});
		glfwSetWindowFocusCallback(window, windowFocusCallback = new GLFWWindowFocusCallback() {
			@Override
			public void invoke(long l, int i) {
				InvokeCallback(onWindowFocusCallback, l, i);
			}
		});
		glfwSetWindowPosCallback(window, windowPosCallback = new GLFWWindowPosCallback() {
			@Override
			public void invoke(long l, int i, int i1) {
				InvokeCallback(onWindowPosCallback, l, i, i1);
			}
		});
		glfwSetWindowRefreshCallback(window, windowRefreshCallback = new GLFWWindowRefreshCallback() {
			@Override
			public void invoke(long l) {
				InvokeCallback(onWindowRefreshCallback, l);
			}
		});
		glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long l, int i, int i1) {
				InvokeCallback(onWindowSizeCallback, l, i, i1);
			}
		});
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long l, int i, int i1, int i2, int i3) {
				InvokeCallback(onKeyCallback, l, i, i1, i2, i3);
			}
		});
	}

	@Override
	public void PollEvents()
	{
		glfwPollEvents();
	}

	private void InvokeCallback(int callback, Object... objects) {
		for (IInput input : inputs)
		{
			input.Invoke(callback, objects);
		}
	}

	@Override
	public boolean addInput(IInput handler)
	{
		return inputs.add(handler);
	}

	@Override
	public boolean removeInput(IInput handler)
	{
		return inputs.remove(handler);
	}

	@Override
	public void StaticClean()
	{
		if (monitorCallback != null)
			monitorCallback.release();
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		if (mouseButtonCallback != null)
			mouseButtonCallback.release();
		if (charCallback != null)
			charCallback.release();
		if (charModsCallback != null)
			charModsCallback.release();
		if (cursorEnterCallback != null)
			cursorEnterCallback.release();
		if (cursorPosCallback != null)
			cursorPosCallback.release();
		if (dropCallback != null)
			dropCallback.release();
		if (framebufferSizeCallback != null)
			framebufferSizeCallback.release();
		if (scrollCallback != null)
			scrollCallback.release();
		if (windowCloseCallback != null)
			windowCloseCallback.release();
		if (windowIconifyCallback != null)
			windowIconifyCallback.release();
		if (windowFocusCallback != null)
			windowFocusCallback.release();
		if (windowPosCallback != null)
			windowPosCallback.release();
		if (windowRefreshCallback != null)
			windowRefreshCallback.release();
		if (windowSizeCallback != null)
			windowSizeCallback.release();
		if (keyCallback != null)
			keyCallback.release();
	}

}
