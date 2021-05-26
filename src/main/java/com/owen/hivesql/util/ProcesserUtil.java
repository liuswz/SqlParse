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
 * @Description:
 * @Date: 2021/3/31 17:10
 * @Version: 1.0
 */
public class ProcesserUtil {

    public static List<QueryResultProcessor> getAllProcessor(String packageName)  throws  parseRunningException{

        List<QueryResultProcessor> queryResultProcessors = new ArrayList<>();
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends QueryResultProcessor>> qrProcessorClass = reflections.getSubTypesOf(QueryResultProcessor.class);

        for (Class<? extends QueryResultProcessor> processorClass : qrProcessorClass) {
            queryResultProcessors.add(reflectObject(processorClass));
        }
        return queryResultProcessors;
    }

    public static QueryResultProcessor reflectObject(Class<? extends QueryResultProcessor> processorClass)  throws  parseRunningException{
        Constructor<?> declaredConstructor = null;

        try {
            declaredConstructor = processorClass.getDeclaredConstructor();
            return (QueryResultProcessor)declaredConstructor.newInstance();
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
