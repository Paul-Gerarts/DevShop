package be.syntra.devshop.DevshopBack.testutilities;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SpyHelper {

    public static void assertAllGettersCalled(Object spy) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(spy.getClass().getSuperclass());
        final Set<Method> methods = Arrays.stream(beanInfo.getPropertyDescriptors())
                .map(PropertyDescriptor::getReadMethod)
                .filter(method -> !method.getName().contains("getClass"))
                .collect(Collectors.toSet());
        final Set<Method> calledMethods = Mockito.mockingDetails(spy).getInvocations().stream()
                .map(InvocationOnMock::getMethod)
                .collect(Collectors.toSet());
        methods.removeAll(calledMethods);
        assertThat(methods).isEqualTo(Collections.emptySet());
    }
}
