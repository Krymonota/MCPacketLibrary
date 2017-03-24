/**
 * @project: mcpacketlibrary
 * @package: io.github.krymonota.mcpacketlibrary.util
 * @file: ReflectionUtil.java
 * @author: Niklas (Krymonota)
 * @date: 26.10.2015
 */
package io.github.krymonota.mcpacketlibrary.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionUtil {

	/**
	 * @param String The name of the field.
	 * @param Class<?> The class of the field.
	 * @return Field The field from the given class.
	 */
	public static Field getField(String name, Class<?> target) {
		try {
			final Field field = target.getDeclaredField(name);
			field.setAccessible(true);
	
			return field;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
	
	/**
	 * @see {@link #getField(String, Class<?>)}
	 */
	public static Object get(String name, Object target) {
		try {
			return getField(name, target.getClass()).get(target);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param Field The field to set.
	 * @param Object The class of the field.
	 * @param Object The value to set.
	 */
	public static void setField(Field field, Object classWithField, Object value) {
		try {
			field.set(classWithField, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see {@link setField(Field, Object, Object)}
	 */
	public static void getAndSetField(String fieldName, Object objectWithField, Object value) {
		setField(getField(fieldName, objectWithField.getClass()), objectWithField, value);
	}

	/**
	 * @param Field The field to unfinal.
	 * @return True if succeed.
	 */
	public static boolean unfinal(Field field) {
		try {
			Field fieldMods = Field.class.getDeclaredField("modifiers");
			fieldMods.setAccessible(true);
			fieldMods.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param String The name of the method.
	 * @param Class<?> The class of the method.
	 * @param Class<?>... The arguments of the method.
	 * @return Method The method to get.
	 */
	public static Method getDeclaredMethod(String methodName, Class<?> clazz, Class<?>... arguments) {
		try {
			Method method = clazz.getDeclaredMethod(methodName, arguments);
			method.setAccessible(true);

			return method;
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param String The name of the method.
	 * @param Object The object with the method.
	 * @param Class<?>[] The argument classes of the method.
	 * @param Object[] The arguments of the method.
	 * @return Object The method to execute.
	 */
	public static Object executeDeclaredMethod(String methodName, Object objectWithMethod, Class<?>[] argumentClasses, Object[] arguments) {
		try {
			return getDeclaredMethod(methodName, objectWithMethod.getClass(), argumentClasses).invoke(objectWithMethod, arguments);
		} catch (IllegalAccessException | IllegalArgumentException| InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

}
