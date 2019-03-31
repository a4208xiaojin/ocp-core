package com.nbd.ocp.core.repository.multiTenancy.discriminator.aspects;

import com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpCurrentTenant;
import com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpTenant;
import com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpWithoutTenant;
import com.nbd.ocp.core.repository.multiTenancy.context.OcpTenantContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.Session;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

@Aspect
@Component
@Conditional(OcpMultiTenancyDiscriminatorCondition.class)
public class OcpMultiTenancyDiscriminatorAspect {


  @Pointcut(value = "(@within(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpCurrentTenant) || @annotation(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpCurrentTenant))")
  void hasCurrentTenantAnnotation() {}

  @Pointcut(value = "@within(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpTenant) || @annotation(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpTenant)")
  void hasTenantAnnotation() {}

  @Pointcut(value = "@within(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpWithoutTenant) || @annotation(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.OcpWithoutTenant)")
  void hasWithoutTenantAnnotation() {}

  @Pointcut(value = "hasCurrentTenantAnnotation() || hasTenantAnnotation() || hasWithoutTenantAnnotation()")
  void hasMultiTenantAnnotation() {}

  @PersistenceContext
  public EntityManager entityManager;

  @Around("execution(public * *(..)) && hasMultiTenantAnnotation()")
  public Object aroundExecution(ProceedingJoinPoint pjp) throws Throwable {
      final String methodName = pjp.getSignature().getName();
      final MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
      Method method = methodSignature.getMethod();
      if (!method.isDefault()&&method.getDeclaringClass().isInterface()) {
        method = pjp.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
      }
      Annotation multiTenantAnnotation = getMultiTenantAnnotation(method);
      if (multiTenantAnnotation == null) {
        multiTenantAnnotation = getMultiTenantAnnotation(method.getDeclaringClass());
      }
      if (multiTenantAnnotation != null && !(multiTenantAnnotation instanceof OcpWithoutTenant)) {
        String tenantId = OcpTenantContextHolder.getContext().getTenantId();
        if (multiTenantAnnotation instanceof OcpTenant) {
          tenantId = ((OcpTenant) multiTenantAnnotation).value();
        }
        org.hibernate.Filter filter = entityManager.unwrap(Session.class).enableFilter("tenantFilter");
        filter.setParameter("tenantId", tenantId);
        filter.validate();
      }
    return pjp.proceed();
  }

  private Annotation getMultiTenantAnnotation(AnnotatedElement element) {
    Annotation annotation = element.getAnnotation(OcpCurrentTenant.class);
    if (annotation != null) {
      return annotation;
    }
    annotation = element.getAnnotation(OcpTenant.class);
    if (annotation != null) {
      return annotation;
    }
    annotation = element.getAnnotation(OcpWithoutTenant.class);
    if (annotation != null) {
      return annotation;
    }
    return null;
  }
}
