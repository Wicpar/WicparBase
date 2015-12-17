package com.wicpar.wicparbase.utils;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Frederic on 13/11/2015 at 23:18.
 */
public class ThreadPool
{
	private static boolean Init = init();
	private static ExecutorService executor;
	private static int executorThreads;

	public static ExecutorService getExecutor()
	{
		return executor;
	}

	private static boolean init(){
		executorThreads = Runtime.getRuntime().availableProcessors();
		executor = Executors.newWorkStealingPool();
		return true;
	}

	public static void ProcessRunnables(List<? extends Runnable> runnables) {
		executorThreads = Runtime.getRuntime().availableProcessors();
		int size = runnables.size();
		size -= size / (executorThreads + 1);
		ArrayList sublist = new ArrayList();
		ArrayList futures = new ArrayList();
		sublist.addAll(runnables.subList(0, size));
		runnables = runnables.subList(size, runnables.size());
		Iterator var4 = runnables.iterator();

		Runnable future;
		while(var4.hasNext()) {
			future = (Runnable)var4.next();
			futures.add(executor.submit(future));
		}

		var4 = sublist.iterator();

		while(var4.hasNext()) {
			future = (Runnable)var4.next();
			future.run();
		}

		var4 = futures.iterator();

		while(var4.hasNext()) {
			Future future1 = (Future)var4.next();

			try {
				future1.get();
			} catch (InterruptedException var7) {
				var7.printStackTrace();
			} catch (ExecutionException var8) {
				var8.printStackTrace();
			}
		}

		sublist.clear();
		futures.clear();
	}
}
