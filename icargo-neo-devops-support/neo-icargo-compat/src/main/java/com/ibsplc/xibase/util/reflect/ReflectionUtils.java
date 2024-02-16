
/**
 * Revision History
 * Revision 		Date      			Author			Description
 * 1.0			Apr 1, 2005				A-1456			First draft
 */
package com.ibsplc.xibase.util.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author A-1456
 * 
 */
public final class ReflectionUtils {

	static final String GET_PREFIX = "get";
	static final String SET_PREFIX = "set";

	private static HashMap<Class, Class> mappings;
	public static final Object[] NOARG = new Object[0];

	static {
		mappings = new HashMap<Class, Class>();
		mappings.put(Integer.class, Integer.TYPE);
		mappings.put(Long.class, Long.TYPE);
		mappings.put(Short.class, Short.TYPE);
		mappings.put(Float.class, Float.TYPE);
		mappings.put(Double.class, Double.TYPE);
		mappings.put(Boolean.class, Boolean.TYPE);
		mappings.put(Byte.class, Byte.TYPE);
		mappings.put(Character.class, Character.TYPE);
	}

	/**
	 * Returns the first instance of the Field having the specified Annotation
	 * 
	 * @param obj-
	 *            the annotated object
	 * @param clazz -
	 *            the annotation class
	 * @return the Annotated Field
	 */
	public static Field getAnnotatedField(Object obj,
			Class<? extends Annotation> clazz) throws NoSuchFieldException {
		Class objClass = obj.getClass();
		Field field = null;
		Field fields[] = objClass.getFields();
		for (Field f : fields) {
			if (f.isAnnotationPresent(clazz)) {
				field = f;
				break;
			}
		}
		if (field == null) {
			throw new NoSuchFieldException("NO SUCH ANNOTATED FIELD WITH ANNOTATION @"
							+ clazz.getSimpleName() + " IN CLASS "+ objClass.getSimpleName());
		}
		return field;
	}

	/**
	 * Returns all instances of the Field having the specified Annotation
	 * 
	 * @param obj-
	 *            the annotated object
	 * @param clazz -
	 *            the annotation class
	 * @return the Collection of Annotated Fields
	 */
	public static Collection<Field> getAllAnnotatedFields(Object obj,
			Class<? extends Annotation> clazz) throws NoSuchFieldException {
		Class objClass = obj.getClass();
		Collection<Field> fieldsToReturn = null;
		Field fields[] = objClass.getFields();
		for (Field f : fields) {
			if (f.isAnnotationPresent(clazz)) {
				if (fieldsToReturn == null) {
					fieldsToReturn = new ArrayList<Field>();
				}
				fieldsToReturn.add(f);
			}
		}
		if (fieldsToReturn == null) {
			throw new NoSuchFieldException("NO SUCH ANNOTATED FIELDS WITH ANNOTATION @"
							+ clazz.getSimpleName() + " IN CLASS " + objClass.getSimpleName());
		}
		return fieldsToReturn;
	}

	/**
	 * Returns the first instance of a Method having the specified Annotation
	 * 
	 * @param obj -
	 *            the annotated object
	 * @param clazz -
	 *            the annotation class
	 * @return - the annotated Method
	 */
	public static Method getAnnotatedMethod(Object obj,
			Class<? extends Annotation> clazz) throws NoSuchMethodException {
		Class objClass = obj.getClass();
		Method method = null;
		Method methods[] = objClass.getMethods();
		for (Method m : methods) {
			if (m.isAnnotationPresent(clazz)) {
				method = m;
				break;
			}
		}
		if (method == null) {
			throw new NoSuchMethodException("NO SUCH ANNOTATED METHOD WITH ANNOTATION @"
							+ clazz.getSimpleName() + " IN CLASS " + objClass.getSimpleName());
		}
		return method;
	}

	/**
	 * Returns the first instance of a Method having the specified Annotation
	 * 
	 * @param objClass -
	 *            the annotated class
	 * @param clazz -
	 *            the annotation class
	 * @return - the annotated Method
	 */
	public static Method getAnnotatedMethod(Class objClass,
			Class<? extends Annotation> clazz) throws NoSuchMethodException {
		Method method = null;
		Method methods[] = objClass.getMethods();
		for (Method m : methods) {
			if (m.isAnnotationPresent(clazz)) {
				method = m;
				break;
			}
		}
		if (method == null) {
			throw new NoSuchMethodException("NO SUCH ANNOTATED METHOD WITH ANNOTATION @"
							+ clazz.getSimpleName() + " IN CLASS " + objClass.getSimpleName());
		}
		return method;
	}

	/**
	 * Returns all Methods having the specified Annotation
	 * 
	 * @param obj -
	 *            the annotated object
	 * @param clazz -
	 *            the annotation class
	 * @return - the annotated Method
	 */
	public static Collection<Method> getAllAnnotatedMethods(Object obj,
			Class<? extends Annotation> clazz) throws NoSuchMethodException {
		return getAllAnnotatedMethods(obj, clazz, true);
	}

	/**
	 * Returns all Methods having the specified Annotation
	 * 
	 * @param obj -
	 *            the annotated object
	 * @param clazz -
	 *            the annotation class
	 * @return - the annotated Method
	 */
	public static Collection<Method> getAllAnnotatedMethods(Object obj,
			Class<? extends Annotation> clazz, boolean raiseError)
			throws NoSuchMethodException {
		Class objClass = obj.getClass();
		Collection<Method> methods = new ArrayList<Method>();
		Method metds[] = objClass.getMethods();
		for (Method m : metds) {
			if (m.isAnnotationPresent(clazz)) {
				methods.add(m);
			}
		}
		if (methods.size() == 0) {
			if (raiseError) {
				throw new NoSuchMethodException("NO SUCH ANNOTATED METHODS WITH ANNOTATION @"
								+ clazz.getSimpleName() + " IN CLASS " + objClass.getSimpleName());
			} else {
				methods = null;
			}
		}
		return methods;
	}

	/**
	 * Returns all Methods having the specified Annotation
	 * 
	 * @param objClass -
	 *            the annotated class
	 * @param clazz -
	 *            the annotation class
	 * @return - the annotated Method
	 */
	public static Collection<Method> getAllAnnotatedMethods(Class objClass,
			Class<? extends Annotation> clazz) throws NoSuchMethodException {
		return getAllAnnotatedMethods(objClass, clazz, true);
	}

	/**
	 * Returns all Methods having the specified Annotation
	 * 
	 * @param objClass -
	 *            the annotated class
	 * @param clazz -
	 *            the annotation class
	 * @return - the annotated Method
	 */
	public static Collection<Method> getAllAnnotatedMethods(Class objClass,
			Class<? extends Annotation> clazz, boolean raiseError)
			throws NoSuchMethodException {
		Collection<Method> methods = new ArrayList<Method>();
		Method metds[] = objClass.getMethods();
		for (Method m : metds) {
			if (m.isAnnotationPresent(clazz)) {
				methods.add(m);
			}
		}
		if (methods.size() == 0) {
			if (raiseError) {
				throw new NoSuchMethodException("NO SUCH ANNOTATED METHODS WITH ANNOTATION @"
								+ clazz.getSimpleName() + " IN CLASS " + objClass.getSimpleName());
			} else {
				methods = null;
			}
		}
		return methods;
	}

	/**
	 * Get the getter method for the attribute. The method expects the object to
	 * have Jave bean style properties
	 * 
	 * @param obj -
	 *            the Java bean stype object
	 * @param attributeName -
	 *            the name of the attribute
	 * @return - An instance of get Method for the attribute
	 * @throws NoSuchMethodException
	 */
	public static Method getAccessorForField(Object obj, String attributeName)
			throws NoSuchMethodException {
		StringBuilder sbuf = new StringBuilder().append(GET_PREFIX).append(
				attributeName);
		sbuf.setCharAt(GET_PREFIX.length(), Character.toUpperCase(sbuf
				.charAt(GET_PREFIX.length())));
		Method m = obj.getClass().getMethod(sbuf.toString(), new Class[] {});
		return m;
	}

	/**
	 * Get the setter method for the attribute. The method expects the object to
	 * have Java bean style properties. <b>It is expected that there will be
	 * no overloaded set methods</b>
	 * 
	 * @param obj -
	 *            the Java bean type object
	 * @param attributeName -
	 *            the name of the attribute
	 * @return - An instance of set Method for the attribute
	 * @throws NoSuchMethodException
	 * @throws NoSuchFieldException
	 */
	public static Method getModifierForField(Object obj, String attributeName)
			throws NoSuchMethodException, NoSuchFieldException {
		StringBuffer sbuf = new StringBuffer().append(SET_PREFIX).append(
				attributeName);
		sbuf.setCharAt(SET_PREFIX.length(), Character.toUpperCase(sbuf
				.charAt(SET_PREFIX.length())));
		Method setter = null;
		Method[] methods = obj.getClass().getMethods();
		String methodName = sbuf.toString();
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				setter = m;
				break;
			}
		}
		return setter;
	}

	private static Class getClass(Object o) {
		Class clazz = null;
		clazz = o.getClass();
		if ((o instanceof Number) || (o instanceof Character)) {
			clazz = mappings.get(clazz);
		}
		return clazz;
	}

	/**
	 * Invoke the method with the specified name on the passed object, with the
	 * given arguments Methods having wrappers for the primitive types as method
	 * arguments are NOT SUPPORTED.
	 * 
	 * @param obj
	 * @param methodName
	 * @param args
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Object invokeMethod(Object obj, String methodName, Object[] args) 
			throws NoSuchMethodException {		
		ArrayList<Class> pars = new ArrayList<Class>(args.length);
		for (Object o : args) {
			pars.add(getClass(o));
		}
		Class[] parTypes = pars.toArray(new Class[args.length]);
		Method m = obj.getClass().getMethod(methodName, parTypes);
		Object ret = null;
		try {
			ret = m.invoke(obj, args);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	private static boolean checkToOut(String methodName) {
		boolean canOut = false;
		if (!("hashCode".equalsIgnoreCase(methodName))
				&& !("getClass".equalsIgnoreCase(methodName))) {
			if ((methodName.indexOf(GET_PREFIX) > -1)
					|| (methodName.indexOf("is") > -1)
					|| (methodName.indexOf("has") > -1)) {
				canOut = true;
			}
		}
		return canOut;
	}

	public static String genericToString(Object entity) {
		Method[] methods = entity.getClass().getMethods();
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sBuff = new StringBuilder();
		String methodName, fieldName;
		/*for (Method m : methods) {
			try {
				methodName = m.getName();
				if (checkToOut(methodName)) {
					sBuff.append("\n").append(methodName).append("-->").append(
							m.invoke(entity, new Object[] {}));
				}
			} catch (Exception e) { // ignored.
			}
		}*/

		for (Field f : fields) {
			if(!f.isAccessible())
				f.setAccessible(true);
			try {
				if (f.isAccessible()) {
					fieldName = f.getName();

					sBuff.append("\n").append(fieldName).append("-->").append(
							f.get(entity));
				}
			} catch (Exception e) {
				// Ignored
			}
		}

		return sBuff.toString();
	}

	/**
	 * Return all getter methods of the Java Bean style object
	 * @param beanObject
	 * @return
	 */
	public static Method[] getAllAccessors(Object beanObject) {
		return getAllAccessors(beanObject.getClass());
	}
	
	/**
	 * Return all getter methods of the Java Bean style class
	 * @param beanClass
	 * @return
	 */
	public static Method[] getAllAccessors(Class beanClass){
		Method[] getters = null;
		Method[] methods = beanClass.getMethods();
		if (methods != null) {
			ArrayList<Method> getterList = new ArrayList<Method>(methods.length);
			String methodName = null;
			for (Method m : methods) {
				methodName = m.getName();
				if (methodName.startsWith(GET_PREFIX) || methodName.startsWith("is") || methodName.startsWith("has")) {
					getterList.add(m);
				}
			}
			getterList.trimToSize();
			getters = getterList.toArray(new Method[getterList.size()]);
		}
		return getters;
		
	}
	
	/**
	 * 
	 * @param obj
	 * @param params
	 */
	
	public static void setProperties(Object obj,Map<String, Object> params) {
		String methodName;
		StringBuffer buf = new StringBuffer();
		String cap;
		try {
			if (params != null && params.size() > 0) {
				for (Map.Entry<String, Object> e : params.entrySet()) {
					buf = new StringBuffer();
					cap = e.getKey().substring(0, 1).toUpperCase()
							+ e.getKey().substring(1);
					buf.append(SET_PREFIX).append(cap);
					methodName = buf.toString();
					for (Method m : obj.getClass().getMethods()) {
						if (methodName.equals(m.getName())) {
							Class<?>[] mParams = m.getParameterTypes();
							if (mParams.length == 1) {
								if (mParams[0].getName().equals("boolean")) {																
									invokeMethod(obj, methodName,new Object[]{Boolean.parseBoolean((String) e.getValue())});									
								}
								else if (mParams[0].getName().equals("java.lang.String")) {
									invokeMethod(obj, methodName,new Object[]{(String) e.getValue()});
								}
								else if (mParams[0].getName().equals("double")) {
									invokeMethod(obj, methodName,new Object[]{Double.parseDouble((String) e.getValue())});
								}
								else if (mParams[0].getName().equals("int")) {
									invokeMethod(obj, methodName,new Object[]{Integer.parseInt((String) e.getValue())});
								}
							}else{
								throw new IllegalArgumentException("ILLEGAL ARGUMENT EXCEPTION !");
							}
							break;
						}
					}
				}
			}		
		} catch (NoSuchMethodException e1) {		
			e1.printStackTrace();
		}		
		
	}
}
