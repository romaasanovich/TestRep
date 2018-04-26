package com.senla.autoservice.utills;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.senla.autoservice.facade.Autoservice;
import com.senla.autoservice.utills.request.Request;
import com.senla.autoservice.utills.response.Response;
import com.senla.autoservice.utills.response.Status;

public class MethodGetter {
	
	public static Response execute(final Request request) throws NoSuchMethodException {
        final Response response = new Response();

        final Class clazz = Autoservice.class;
        final List<Object> arguments = request.getArguments();
        final Class[] types = getArrayOfTypes(arguments);
        final Method method = clazz.getMethod(request.getMethod(), types);
        try {
            final Object invoke;
            if (arguments == null || arguments.isEmpty()) {
                invoke = method.invoke(Autoservice.getInstance());
            } else {
                invoke = method.invoke(Autoservice.getInstance(), arguments.toArray());
            }
            response.setResponse((String) invoke);
            response.setStatus(Status.Success);
        } catch (IllegalAccessException | InvocationTargetException pE) {
            response.setResponse("");
            response.setStatus(Status.Error);
        }
        return response;
    }
	
	   private static Class[] getArrayOfTypes(final List<Object> list) {
	        final Class[] types = new Class[list.size()];
	        for (int i = 0; i < list.size(); i++) {
	            types[i] = list.get(i).getClass();
	        }
	        return types;
	    }

}
