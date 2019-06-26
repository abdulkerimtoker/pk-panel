package toker.warbandscripts.panel.util;

import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import toker.warbandscripts.panel.annotation.FilterSpecification;

import javax.persistence.metamodel.Attribute;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class FilterSpecificationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(FilterSpecification.class)
                && methodParameter.getParameterType().equals(Specification.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<String> attributeNames = new LinkedList<>();
            root.getModel().getAttributes().forEach((attr) -> attributeNames.add(((Attribute)attr).getName()));


            List<Predicate> predicates = new LinkedList<>();

            nativeWebRequest.getParameterNames().forEachRemaining((param) -> {
                if (attributeNames.contains(param)) {
                    Class fieldType = root.getModel().getJavaType();

                    try {
                        Method fieldGetter = fieldType.getDeclaredMethod("get" + Character.toUpperCase(param.charAt(0)) + param.substring(1));
                        if (fieldGetter.isAnnotationPresent(OneToMany.class) ||
                                fieldGetter.isAnnotationPresent(ManyToMany.class)) {
                            // do nothing
                        } else if (fieldGetter.isAnnotationPresent(OneToOne.class) ||
                                fieldGetter.isAnnotationPresent(ManyToOne.class)) {
                            predicates.add(criteriaBuilder.equal(root.get(param).get("id"), nativeWebRequest.getParameter(param)));
                        } else {
                            predicates.add(criteriaBuilder.equal(root.get(param), nativeWebRequest.getParameter(param)));
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
        };
    }
}
