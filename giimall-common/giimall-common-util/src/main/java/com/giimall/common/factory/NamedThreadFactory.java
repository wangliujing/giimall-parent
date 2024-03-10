package com.giimall.common.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可命名的线程工厂
 * @author wangLiuJing
 * @date 2022/09/30
 */
public class NamedThreadFactory implements ThreadFactory {


	private final AtomicInteger mThreadNum = new AtomicInteger(1);

	protected final String mPrefix;

	protected final boolean mDaemon;

	protected final ThreadGroup mGroup;

	public NamedThreadFactory(String prefix, boolean daemon) {
		mPrefix = prefix + "-thread-";
		mDaemon = daemon;
		SecurityManager s = System.getSecurityManager();
		mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
	}

	@Override
	public Thread newThread(Runnable runnable) {
		String name = mPrefix + mThreadNum.getAndIncrement();
		Thread ret = new Thread(mGroup, runnable, name, 0);
		ret.setDaemon(mDaemon);
		return ret;
	}

	public ThreadGroup getThreadGroup() {
		return mGroup;
	}
}