package io.renren.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * 实体工具类
 * @author suyibo
 * @Date 2018/1/29 18:23
 */
public class BeanUtil {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 将来源对象中的属性赋值到目标对象中(相同属性名和相同类型才能复制)
     * @param source 来源对象
     * @param target 目标对象
     * @return 目标对象(如果发生异常则返回null)
     */
    public static Object copyEntity(Object source,Object target){
        //目标字节码
        Class<?> tarClazz = target.getClass();
    
        //目标类当前属性
        Field[] tarFields = tarClazz.getDeclaredFields();

        //目标类父类属性
        Class<?> tarSuper = tarClazz.getSuperclass();
        Field[] tarSuperFields = tarSuper.getDeclaredFields();


        try {
            target = analyze(tarFields, source, target);
            target = analyze(tarSuperFields, source, target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage());
            return null;
        }
        return target;
    }

    /**
     * 分解
     * @param tarFields 属性字节数组
     * @param source 来源对象
     * @param target 目标对象
     * @return 目标对象
     * @throws IllegalAccessException 非法存取异常
     */
    private static Object analyze(Field[] tarFields, Object source, Object target) throws IllegalAccessException {
        //来源字节码
        Class<?> sourClazz = source.getClass();
        //来源类当前属性
        Field[] sourFields = sourClazz.getDeclaredFields();
        //来源类父类属性
        Class<?> sourSuper = sourClazz.getSuperclass();
        Field[] sourSuperFields = sourSuper.getDeclaredFields();
        //分解field数组
        for (Field field : tarFields) {
            field.setAccessible(true);
            Object convert = convert(field, sourFields, source, target);
            if (convert==null){
                convert = convert(field, sourSuperFields, source, target);
            }
            if (convert!=null){
                target = convert;
            }
        }
        return target;
    }


    /**
     * 将来源对象中的属性值转换到目标对象
     * @param field 目标对象字段
     * @param sourFields 来源对象字段数组
     * @param source 来源对象
     * @param target 目标对象
     * @return 目标对象
     * @throws IllegalAccessException 非法存取异常
     */
    private static Object convert(Field field, Field[] sourFields, Object source, Object target) throws IllegalAccessException {
        if (field == null || sourFields == null || sourFields.length == 0 || source == null || target == null){
            return null;
        }
        field.setAccessible(true);
        for (Field sourField : sourFields) {
            sourField.setAccessible(true);
            if (sourField.getName().equals(field.getName())){
                Type genericType = field.getGenericType();
                Type sourFieldGenericType = sourField.getGenericType();
                if (sourField.get(source)!=null && genericType.getTypeName().equals(sourFieldGenericType.getTypeName())){
                    field.set(target,sourField.get(source));
                    return target;
                }
            }
        }
        return null;
    }



/**/
    /**
     * 测试
     * @param args 参数
     *//*

    public static void main(String[] args) throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        LMPreserveSerEntity target = new LMPreserveSerEntity();
        PreserveEntity source = new PreserveEntity();
        source.setPrsvId("123");
        source.setAccount("987");
        source.setCreateDate(new Date());
        long start = System.currentTimeMillis();
        Object o = copyEntity(source, target);
        long end = System.currentTimeMillis();
        System.out.println(o);
        System.out.println(end-start);
    }
*/

}
