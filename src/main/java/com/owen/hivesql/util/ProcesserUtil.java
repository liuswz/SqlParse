package com.owen.hivesql.util;


import com.owen.hivesql.exception.parseRunningException;
import com.owen.hivesql.expand.QueryResultProcessor;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ProjectName: Sql
 * @Package: com.owen.parseasttree.util
 * @ClassName: ProcesserUtil
 * @Author: owen
 * @Description: 通过扩展类包名反射获取扩展类对象
 * @Date: 2021/3/31 17:10
 * @Version: 1.0
 */
public class ProcesserUtil {

    // getAllProcessor  通过包名反射获取所有扩展类对象
    public static List<QueryResultProcessor> getAllProcessor(String packageName) throws parseRunningException {
        List<QueryResultProcessor> queryResultProcessors = new ArrayList<>();
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends QueryResultProcessor>> qrProcessorClass = reflections.getSubTypesOf(QueryResultProcessor.class);

        for (Class<? extends QueryResultProcessor> processorClass : qrProcessorClass) {
            queryResultProcessors.add(reflectObject(processorClass));
        }
        return queryResultProcessors;
    }

    // reflectObject  通过class获取 QueryResultProcessor对象
    public static QueryResultProcessor reflectObject(Class<? extends QueryResultProcessor> processorClass) throws parseRunningException {
        Constructor<?> declaredConstructor = null;
        try {
            declaredConstructor = processorClass.getDeclaredConstructor();
            return (QueryResultProcessor) declaredConstructor.newInstance();
        } catch (InstantiationException e) {
            // e.printStackTrace();
            throw new parseRunningException(e.getMessage());
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
            throw new parseRunningException(e.getMessage());
        } catch (InvocationTargetException e) {
            //  e.printStackTrace();
            throw new parseRunningException(e.getMessage());
        } catch (NoSuchMethodException e) {
            //  e.printStackTrace();
            throw new parseRunningException(e.getMessage());
        }

    }

}
