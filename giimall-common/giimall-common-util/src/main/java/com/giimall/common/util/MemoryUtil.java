package com.giimall.common.util;


import com.giimall.common.constant.JvmConstant;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 *
 * 内存工具类
 * 主要用于计算java对象占用内存
 * @author wangLiuJing
 * Created on 2021/8/19
 */
public final class MemoryUtil {


	/**
	 * No instantiation.
	 */
	private MemoryUtil() {
	}


	/**
	 * One kilobyte bytes.
	 */
	public static final long ONE_KB = 1024;

	/**
	 * One megabyte bytes.
	 */
	public static final long ONE_MB = ONE_KB * ONE_KB;

	/**
	 * One gigabyte bytes.
	 */
	public static final long ONE_GB = ONE_KB * ONE_MB;
	/**
	 * Number of bytes used to represent a {@code boolean} in binary form
	 *
	 */
	public final static int NUM_BYTES_BOOLEAN = 1;
	/**
	 * Number of bytes used to represent a {@code byte} in binary form
	 *
	 */
	public final static int NUM_BYTES_BYTE = 1;
	/**
	 * Number of bytes used to represent a {@code char} in binary form
	 *
	 */
	public final static int NUM_BYTES_CHAR = Character.BYTES;
	/**
	 * Number of bytes used to represent a {@code short} in binary form
	 *
	 */
	public final static int NUM_BYTES_SHORT = Short.BYTES;
	/**
	 * Number of bytes used to represent an {@code int} in binary form
	 *
	 */
	public final static int NUM_BYTES_INT = Integer.BYTES;
	/**
	 * Number of bytes used to represent a {@code float} in binary form
	 *
	 */
	public final static int NUM_BYTES_FLOAT = Float.BYTES;
	/**
	 * Number of bytes used to represent a {@code long} in binary form
	 *
	 */
	public final static int NUM_BYTES_LONG = Long.BYTES;
	/**
	 * Number of bytes used to represent a {@code double} in binary form
	 *
	 */
	public final static int NUM_BYTES_DOUBLE = Double.BYTES;
	/**
	 * True, iff compressed references (oops) are enabled by this JVM
	 */
	public final static boolean COMPRESSED_REFS_ENABLED;
	/**
	 * Number of bytes this JVM uses to represent an object reference.
	 */
	public final static int NUM_BYTES_OBJECT_REF;
	/**
	 * Number of bytes to represent an object header (no fields, no alignments).
	 */
	public final static int NUM_BYTES_OBJECT_HEADER;
	/**
	 * Number of bytes to represent an array header (no content, but with alignments).
	 */
	public final static int NUM_BYTES_ARRAY_HEADER;
	/**
	 * A constant specifying the object alignment boundary inside the JVM. Objects will
	 * always take a full multiple of this constant, possibly wasting some space.
	 */
	public final static int NUM_BYTES_OBJECT_ALIGNMENT;
	/**
	 * JVMs typically cache small longs. This tries to find out what the range is.
	 */
	static final long LONG_CACHE_MIN_VALUE, LONG_CACHE_MAX_VALUE;
	static final int LONG_SIZE;
	/**
	 * For testing only
	 */
	static final boolean JVM_IS_HOTSPOT_64BIT;
	static final String MANAGEMENT_FACTORY_CLASS = "java.lang.management.ManagementFactory";
	static final String HOTSPOT_BEAN_CLASS = "com.sun.management.HotSpotDiagnosticMXBean";
	/**
	 * Sizes of primitive classes.
	 */
	private static final Map<Class<?>, Integer> primitiveSizes = new IdentityHashMap<>();

	static {
		primitiveSizes.put(boolean.class, 1);
		primitiveSizes.put(byte.class, 1);
		primitiveSizes.put(char.class, Integer.valueOf(Character.BYTES));
		primitiveSizes.put(short.class, Integer.valueOf(Short.BYTES));
		primitiveSizes.put(int.class, Integer.valueOf(Integer.BYTES));
		primitiveSizes.put(float.class, Integer.valueOf(Float.BYTES));
		primitiveSizes.put(double.class, Integer.valueOf(Double.BYTES));
		primitiveSizes.put(long.class, Integer.valueOf(Long.BYTES));
	}

	/**
	 * Initialize constants and try to collect information about the JVM internals.
	 */
	static {
		if (JvmConstant.JRE_IS_64BIT) {
			// Try to get compressed oops and object alignment (the default seems to be 8 on Hotspot);
			// (this only works on 64 bit, on 32 bits the alignment and reference size is fixed):
			boolean compressedOops = false;
			int objectAlignment = 8;
			boolean isHotspot = false;
			try {
				final Class<?> beanClazz = Class.forName(HOTSPOT_BEAN_CLASS);
				// we use reflection for this, because the management factory is not part
				// of Java 8's compact profile:
				final Object hotSpotBean = Class.forName(MANAGEMENT_FACTORY_CLASS)
						.getMethod("getPlatformMXBean", Class.class)
						.invoke(null, beanClazz);
				if (hotSpotBean != null) {
					isHotspot = true;
					final Method getVMOptionMethod = beanClazz.getMethod("getVMOption", String.class);
					try {
						final Object vmOption = getVMOptionMethod.invoke(hotSpotBean, "UseCompressedOops");
						compressedOops = Boolean.parseBoolean(
								vmOption.getClass().getMethod("getValue").invoke(vmOption).toString()
						);
					} catch (ReflectiveOperationException | RuntimeException e) {
						isHotspot = false;
					}
					try {
						final Object vmOption = getVMOptionMethod.invoke(hotSpotBean, "ObjectAlignmentInBytes");
						objectAlignment = Integer.parseInt(
								vmOption.getClass().getMethod("getValue").invoke(vmOption).toString()
						);
					} catch (ReflectiveOperationException | RuntimeException e) {
						isHotspot = false;
					}
				}
			} catch (ReflectiveOperationException | RuntimeException e) {
				isHotspot = false;
			}
			JVM_IS_HOTSPOT_64BIT = isHotspot;
			COMPRESSED_REFS_ENABLED = compressedOops;
			NUM_BYTES_OBJECT_ALIGNMENT = objectAlignment;
			// reference size is 4, if we have compressed oops:
			NUM_BYTES_OBJECT_REF = COMPRESSED_REFS_ENABLED ? 4 : 8;
			// "best guess" based on reference size:
			NUM_BYTES_OBJECT_HEADER = 8 + NUM_BYTES_OBJECT_REF;
			// array header is NUM_BYTES_OBJECT_HEADER + NUM_BYTES_INT, but aligned (object alignment):
			NUM_BYTES_ARRAY_HEADER = (int) alignObjectSize(NUM_BYTES_OBJECT_HEADER + Integer.BYTES);
		} else {
			JVM_IS_HOTSPOT_64BIT = false;
			COMPRESSED_REFS_ENABLED = false;
			NUM_BYTES_OBJECT_ALIGNMENT = 8;
			NUM_BYTES_OBJECT_REF = 4;
			NUM_BYTES_OBJECT_HEADER = 8;
			// For 32 bit JVMs, no extra alignment of array header:
			NUM_BYTES_ARRAY_HEADER = NUM_BYTES_OBJECT_HEADER + Integer.BYTES;
		}

		// get min/max value of cached Long class instances:
		long longCacheMinValue = 0;
		while (longCacheMinValue > Long.MIN_VALUE
				&& Long.valueOf(longCacheMinValue - 1) == Long.valueOf(longCacheMinValue - 1)) {
			longCacheMinValue -= 1;
		}
		long longCacheMaxValue = -1;
		while (longCacheMaxValue < Long.MAX_VALUE
				&& Long.valueOf(longCacheMaxValue + 1) == Long.valueOf(longCacheMaxValue + 1)) {
			longCacheMaxValue += 1;
		}
		LONG_CACHE_MIN_VALUE = longCacheMinValue;
		LONG_CACHE_MAX_VALUE = longCacheMaxValue;
		LONG_SIZE = (int) shallowSizeOfInstance(Long.class);
	}


	/**
	 * 内存值校准（java对象的大小必须是8字节的整数倍）
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param size of type long
	 * @return long
	 */
	public static long alignObjectSize(long size) {
		size += (long) NUM_BYTES_OBJECT_ALIGNMENT - 1L;
		return size - (size % NUM_BYTES_OBJECT_ALIGNMENT);
	}


	/**
	 * 计算Long占用内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param value of type Long
	 * @return long
	 */
	public static long sizeOf(Long value) {
		if (value >= LONG_CACHE_MIN_VALUE && value <= LONG_CACHE_MAX_VALUE) {
			return 0;
		}
		return LONG_SIZE;
	}


	/**
	 * 计算byte数组占用内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param arr of type byte[]
	 * @return long
	 */
	public static long sizeOf(byte[] arr) {
		return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + arr.length);
	}


	/**
	 * 计算boolean数组占用内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param arr of type boolean[]
	 * @return long
	 */
	public static long sizeOf(boolean[] arr) {
		return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + arr.length);
	}


	/**
	 * 计算char数组占用内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param arr of type char[]
	 * @return long
	 */
	public static long sizeOf(char[] arr) {
		return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Character.BYTES * arr.length);
	}


	/**
	 * 计算short数组占用内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param arr of type short[]
	 * @return long
	 */
	public static long sizeOf(short[] arr) {
		return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Short.BYTES * arr.length);
	}


	/**
	 * 计算int数组占用内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param arr of type int[]
	 * @return long
	 */
	public static long sizeOf(int[] arr) {
		return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Integer.BYTES * arr.length);
	}


	/**
	 * 计算float数组占用内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param arr of type float[]
	 * @return long
	 */
	public static long sizeOf(float[] arr) {
		return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Float.BYTES * arr.length);
	}


	/**
	 * 计算long数组占用内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param arr of type long[]
	 * @return long
	 */
	public static long sizeOf(long[] arr) {
		return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Long.BYTES * arr.length);
	}


	/**
	 * 计算double数组占用内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param arr of type double[]
	 * @return long
	 */
	public static long sizeOf(double[] arr) {
		return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) Double.BYTES * arr.length);
	}

	/**
	 * 计算指定对象及其引用树上的所有对象占用内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param obj of type Object
	 * @return long
	 */
	public static long sizeOf(Object obj) {
		return measureObjectSize(obj);
	}

	private static long measureObjectSize(Object root) {
		// Objects seen so far.
		final IdentityHashSet<Object> seen = new IdentityHashSet<Object>();
		// Class cache with reference Field and precalculated shallow size.
		final IdentityHashMap<Class<?>, ClassCache> classCache = new IdentityHashMap<Class<?>, ClassCache>();
		// Stack of objects pending traversal. Recursion caused stack overflows.
		final ArrayList<Object> stack = new ArrayList<Object>();
		stack.add(root);

		long totalSize = 0;
		while (!stack.isEmpty()) {
			final Object ob = stack.remove(stack.size() - 1);

			if (ob == null || seen.contains(ob)) {
				continue;
			}
			seen.add(ob);

			final Class<?> obClazz = ob.getClass();
			if (obClazz.isArray()) {
				/*
				 * Consider an array, possibly of primitive types. Push any of its references to
				 * the processing stack and accumulate this array's shallow size.
				 */
				long size = NUM_BYTES_ARRAY_HEADER;
				final int len = Array.getLength(ob);
				if (len > 0) {
					Class<?> componentClazz = obClazz.getComponentType();
					if (componentClazz.isPrimitive()) {
						size += (long) len * primitiveSizes.get(componentClazz);
					} else {
						size += (long) NUM_BYTES_OBJECT_REF * len;

						// Push refs for traversal later.
						for (int i = len; --i >= 0 ;) {
							final Object o = Array.get(ob, i);
							if (o != null && !seen.contains(o)) {
								stack.add(o);
							}
						}
					}
				}
				totalSize += alignObjectSize(size);
			} else {
				/*
				 * Consider an object. Push any references it has to the processing stack
				 * and accumulate this object's shallow size.
				 */
				try {
					ClassCache cachedInfo = classCache.get(obClazz);
					if (cachedInfo == null) {
						classCache.put(obClazz, cachedInfo = createCacheEntry(obClazz));
					}

					for (Field f : cachedInfo.referenceFields) {
						// Fast path to eliminate redundancies.
						final Object o = f.get(ob);
						if (o != null && !seen.contains(o)) {
							stack.add(o);
						}
					}

					totalSize += cachedInfo.alignedShallowInstanceSize;
				} catch (IllegalAccessException e) {
					// this should never happen as we enabled setAccessible().
					throw new RuntimeException("Reflective field access failed?", e);
				}
			}
		}

		// Help the GC (?).
		seen.clear();
		stack.clear();
		classCache.clear();

		return totalSize;
	}

	private static ClassCache createCacheEntry(final Class<?> clazz) {
		ClassCache cachedInfo;
		long shallowInstanceSize = NUM_BYTES_OBJECT_HEADER;
		final ArrayList<Field> referenceFields = new ArrayList<Field>(32);
		for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
			final Field[] fields = c.getDeclaredFields();
			for (final Field f : fields) {
				if (!Modifier.isStatic(f.getModifiers())) {
					shallowInstanceSize = adjustForField(shallowInstanceSize, f);

					if (!f.getType().isPrimitive()) {
						f.setAccessible(true);
						referenceFields.add(f);
					}
				}
			}
		}

		cachedInfo = new ClassCache(
				alignObjectSize(shallowInstanceSize),
				referenceFields.toArray(new Field[referenceFields.size()]));
		return cachedInfo;
	}


	/**
	 * 只计算Object数组对象本身在堆空间的内存大小,不包含数组里面的内容所占用的内存
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param arr of type Object[]
	 * @return long
	 */
	public static long shallowSizeOf(Object[] arr) {
		return alignObjectSize((long) NUM_BYTES_ARRAY_HEADER + (long) NUM_BYTES_OBJECT_REF * arr.length);
	}


	/**
	 * 只计算Object.class对象本身在堆空间的内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param obj of type Object
	 * @return long
	 */
	public static long shallowSizeOf(Object obj) {
		if (obj == null) {
			return 0;
		}
		final Class<?> clz = obj.getClass();
		if (clz.isArray()) {
			return shallowSizeOfArray(obj);
		} else {
			return shallowSizeOfInstance(clz);
		}
	}


	/**
	 * 计算class实例化后对象占用内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param clazz of type Class
	 * @return long
	 */
	public static long shallowSizeOfInstance(Class<?> clazz) {
		if (clazz.isArray()) {
			throw new IllegalArgumentException("This method does not work with array classes.");
		}

		if (clazz.isPrimitive()) {
			return primitiveSizes.get(clazz);
		}


		long size = NUM_BYTES_OBJECT_HEADER;

		// Walk type hierarchy
		for (; clazz != null; clazz = clazz.getSuperclass()) {
			final Class<?> target = clazz;
			final Field[] fields = AccessController.doPrivileged((PrivilegedAction<Field[]>) () ->
					target.getDeclaredFields());
			for (Field f : fields) {
				if (!Modifier.isStatic(f.getModifiers())) {
					size = adjustForField(size, f);
				}
			}
		}
		return alignObjectSize(size);
	}


	/**
	 * 只计算数组对象本身在堆空间的内存大小
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param array of type Object
	 * @return long
	 */
	private static long shallowSizeOfArray(Object array) {
		long size = NUM_BYTES_ARRAY_HEADER;
		final int len = Array.getLength(array);
		if (len > 0) {
			Class<?> arrayElementClazz = array.getClass().getComponentType();
			if (arrayElementClazz.isPrimitive()) {
				size += (long) len * primitiveSizes.get(arrayElementClazz);
			} else {
				size += (long) NUM_BYTES_OBJECT_REF * len;
			}
		}
		return alignObjectSize(size);
	}

	/**
	 * This method returns the maximum representation size of an object. <code>sizeSoFar</code>
	 * is the object's size measured so far. <code>f</code> is the field being probed.
	 *
	 * <p>The returned offset will be the maximum of whatever was measured so far and
	 * <code>f</code> field's offset and representation size (unaligned).
	 */
	static long adjustForField(long sizeSoFar, final Field f) {
		final Class<?> type = f.getType();
		final int fsize = type.isPrimitive() ? primitiveSizes.get(type) : NUM_BYTES_OBJECT_REF;
		// TODO: No alignments based on field type/ subclass fields alignments?
		return sizeSoFar + fsize;
	}


	/**
	 * 单位转换 (GB, MB, KB or bytes)
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param bytes of type long
	 * @return String
	 */
	public static String humanReadableUnits(long bytes) {
		return humanReadableUnits(bytes,
				new DecimalFormat("0.#", DecimalFormatSymbols.getInstance(Locale.ROOT)));
	}


	/**
	 * 单位转换 (GB, MB, KB or bytes)
	 * @author wangLiuJing
	 * Created on 2021/8/19
	 *
	 * @param bytes of type long
	 * @param df of type DecimalFormat
	 * @return String
	 */
	public static String humanReadableUnits(long bytes, DecimalFormat df) {
		if (bytes / ONE_GB > 0) {
			return df.format((float) bytes / ONE_GB) + " GB";
		} else if (bytes / ONE_MB > 0) {
			return df.format((float) bytes / ONE_MB) + " MB";
		} else if (bytes / ONE_KB > 0) {
			return df.format((float) bytes / ONE_KB) + " KB";
		} else {
			return bytes + " bytes";
		}
	}

	static final class IdentityHashSet<KType> implements Iterable<KType> {
		/**
		 * Default load factor.
		 */
		public final static float DEFAULT_LOAD_FACTOR = 0.75f;

		/**
		 * Minimum capacity for the set.
		 */
		public final static int MIN_CAPACITY = 4;

		/**
		 * All of set entries. Always of power of two length.
		 */
		public Object[] keys;

		/**
		 * Cached number of assigned slots.
		 */
		public int assigned;

		/**
		 * The load factor for this set (fraction of allocated or deleted slots before
		 * the buffers must be rehashed or reallocated).
		 */
		public final float loadFactor;

		/**
		 * Cached capacity threshold at which we must resize the buffers.
		 */
		private int resizeThreshold;

		/**
		 * Creates a hash set with the default capacity of 16.
		 * load factor of {@value #DEFAULT_LOAD_FACTOR}. `
		 */
		public IdentityHashSet() {
			this(16, DEFAULT_LOAD_FACTOR);
		}

		/**
		 * Creates a hash set with the given capacity, load factor of
		 * {@value #DEFAULT_LOAD_FACTOR}.
		 */
		public IdentityHashSet(int initialCapacity) {
			this(initialCapacity, DEFAULT_LOAD_FACTOR);
		}

		/**
		 * Creates a hash set with the given capacity and load factor.
		 */
		public IdentityHashSet(int initialCapacity, float loadFactor) {
			initialCapacity = Math.max(MIN_CAPACITY, initialCapacity);

			assert initialCapacity > 0 : "Initial capacity must be between (0, "
					+ Integer.MAX_VALUE + "].";
			assert loadFactor > 0 && loadFactor < 1 : "Load factor must be between (0, 1).";
			this.loadFactor = loadFactor;
			allocateBuffers(roundCapacity(initialCapacity));
		}

		/**
		 * Adds a reference to the set. Null keys are not allowed.
		 */
		public boolean add(KType e) {
			assert e != null : "Null keys not allowed.";

			if (assigned >= resizeThreshold) {
				expandAndRehash();
			}

			final int mask = keys.length - 1;
			int slot = rehash(e) & mask;
			Object existing;
			while ((existing = keys[slot]) != null) {
				if (e == existing) {
					return false; // already found.
				}
				slot = (slot + 1) & mask;
			}
			assigned++;
			keys[slot] = e;
			return true;
		}

		/**
		 * Checks if the set contains a given ref.
		 */
		public boolean contains(KType e) {
			final int mask = keys.length - 1;
			int slot = rehash(e) & mask;
			Object existing;
			while ((existing = keys[slot]) != null) {
				if (e == existing) {
					return true;
				}
				slot = (slot + 1) & mask;
			}
			return false;
		}

		/** Rehash via MurmurHash.
		 *
		 * <p>The implementation is based on the
		 * finalization step from Austin Appleby's
		 * <code>MurmurHash3</code>.
		 *
		 * @see "http://sites.google.com/site/murmurhash/"
		 */
		private static int rehash(Object o) {
			int k = System.identityHashCode(o);
			k ^= k >>> 16;
			k *= 0x85ebca6b;
			k ^= k >>> 13;
			k *= 0xc2b2ae35;
			k ^= k >>> 16;
			return k;
		}

		/**
		 * Expand the internal storage buffers (capacity) or rehash current keys and
		 * values if there are a lot of deleted slots.
		 */
		private void expandAndRehash() {
			final Object[] oldKeys = this.keys;

			assert assigned >= resizeThreshold;
			allocateBuffers(nextCapacity(keys.length));

			/*
			 * Rehash all assigned slots from the old hash table.
			 */
			final int mask = keys.length - 1;
			for (int i = 0; i < oldKeys.length; i++) {
				final Object key = oldKeys[i];
				if (key != null) {
					int slot = rehash(key) & mask;
					while (keys[slot] != null) {
						slot = (slot + 1) & mask;
					}
					keys[slot] = key;
				}
			}
			Arrays.fill(oldKeys, null);
		}

		/**
		 * Allocate internal buffers for a given capacity.
		 *
		 * @param capacity
		 *          New capacity (must be a power of two).
		 */
		private void allocateBuffers(int capacity) {
			this.keys = new Object[capacity];
			this.resizeThreshold = (int) (capacity * DEFAULT_LOAD_FACTOR);
		}

		/**
		 * Return the next possible capacity, counting from the current buffers' size.
		 */
		protected int nextCapacity(int current) {
			assert current > 0 && Long.bitCount(current) == 1 : "Capacity must be a power of two.";
			assert ((current << 1) > 0) : "Maximum capacity exceeded ("
					+ (0x80000000 >>> 1) + ").";

			if (current < MIN_CAPACITY / 2){
				current = MIN_CAPACITY / 2;
			}
			return current << 1;
		}

		/**
		 * Round the capacity to the next allowed value.
		 */
		protected int roundCapacity(int requestedCapacity) {
			// Maximum positive integer that is a power of two.
			if (requestedCapacity > (0x80000000 >>> 1)) {
				return (0x80000000 >>> 1);
			}

			int capacity = MIN_CAPACITY;
			while (capacity < requestedCapacity) {
				capacity <<= 1;
			}

			return capacity;
		}

		public void clear() {
			assigned = 0;
			Arrays.fill(keys, null);
		}

		public int size() {
			return assigned;
		}

		public boolean isEmpty() {
			return size() == 0;
		}

		@Override
		public Iterator<KType> iterator() {
			return new Iterator<KType>() {
				int pos = -1;
				Object nextElement = fetchNext();

				@Override
				public boolean hasNext() {
					return nextElement != null;
				}

				@SuppressWarnings("unchecked")
				@Override
				public KType next() {
					Object r = this.nextElement;
					if (r == null) {
						throw new NoSuchElementException();
					}
					this.nextElement = fetchNext();
					return (KType) r;
				}

				private Object fetchNext() {
					pos++;
					while (pos < keys.length && keys[pos] == null) {
						pos++;
					}

					return (pos >= keys.length ? null : keys[pos]);
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
	}

	private static final class ClassCache {
		public final long alignedShallowInstanceSize;
		public final Field[] referenceFields;

		public ClassCache(long alignedShallowInstanceSize, Field[] referenceFields) {
			this.alignedShallowInstanceSize = alignedShallowInstanceSize;
			this.referenceFields = referenceFields;
		}
	}

}
