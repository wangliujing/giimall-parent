package com.giimall.common.constant;

import java.lang.reflect.Field;
import java.util.Collections;

/**
 * Jvm常量类
 * 作用：可以通过静态属性来获取Jvm运行环境参数
 *
 * @author wangLiuJing
 * Created on 2021/8/19
 */
public final class JvmConstant {
  private JvmConstant() {}  // can't construct

  /** JVM vendor info. */
  public static final String JVM_VENDOR = System.getProperty("java.vm.vendor");
  public static final String JVM_VERSION = System.getProperty("java.vm.version");
  public static final String JVM_NAME = System.getProperty("java.vm.name");

  /** The value of <tt>System.getProperty("java.version")</tt>. **/
  public static final String JAVA_VERSION = System.getProperty("java.version");
 
  /** The value of <tt>System.getProperty("os.name")</tt>. **/
  public static final String OS_NAME = System.getProperty("os.name");
  /** True iff running on Linux. */
  public static final boolean LINUX = OS_NAME.startsWith("Linux");
  /** True iff running on Windows. */
  public static final boolean WINDOWS = OS_NAME.startsWith("Windows");
  /** True iff running on SunOS. */
  public static final boolean SUN_OS = OS_NAME.startsWith("SunOS");
  /** True iff running on Mac OS X */
  public static final boolean MAC_OS_X = OS_NAME.startsWith("Mac OS X");
  /** True iff running on FreeBSD */
  public static final boolean FREE_BSD = OS_NAME.startsWith("FreeBSD");

  public static final String OS_ARCH = System.getProperty("os.arch");
  public static final String OS_VERSION = System.getProperty("os.version");
  public static final String JAVA_VENDOR = System.getProperty("java.vendor");

  /** @deprecated With Lucene 4.0, we are always on Java 6 */
  @Deprecated
  public static final boolean JRE_IS_MINIMUM_JAVA6 =
    new Boolean(true).booleanValue();
  
  public static final boolean JRE_IS_MINIMUM_JAVA7;
  public static final boolean JRE_IS_MINIMUM_JAVA8;
  
  /** True iff running on a 64bit JVM */
  public static final boolean JRE_IS_64BIT;
  
  static {
    boolean is64Bit;
    try {
      final Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
      final Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
      unsafeField.setAccessible(true);
      final Object unsafe = unsafeField.get(null);
      final int addressSize = ((Number) unsafeClass.getMethod("addressSize")
        .invoke(unsafe)).intValue();
      is64Bit = addressSize >= 8;
    } catch (Exception e) {
      final String x = System.getProperty("sun.arch.data.model");
      if (x != null) {
        is64Bit = x.indexOf("64") != -1;
      } else {
        if (OS_ARCH != null && OS_ARCH.indexOf("64") != -1) {
          is64Bit = true;
        } else {
          is64Bit = false;
        }
      }
    }
    JRE_IS_64BIT = is64Bit;
    
    // this method only exists in Java 7:
    boolean v7 = true;
    try {
      Throwable.class.getMethod("getSuppressed");
    } catch (NoSuchMethodException nsme) {
      v7 = false;
    }
    JRE_IS_MINIMUM_JAVA7 = v7;
    
    if (JRE_IS_MINIMUM_JAVA7) {
      // this method only exists in Java 8:
      boolean v8 = true;
      try {
        Collections.class.getMethod("emptySortedSet");
      } catch (NoSuchMethodException nsme) {
        v8 = false;
      }
      JRE_IS_MINIMUM_JAVA8 = v8;
    } else {
      JRE_IS_MINIMUM_JAVA8 = false;
    }
  }
}
