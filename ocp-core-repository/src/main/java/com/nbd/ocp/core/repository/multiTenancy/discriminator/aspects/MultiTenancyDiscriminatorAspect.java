package com.nbd.ocp.core.repository.multiTenancy.discriminator.aspects;

import com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.CurrentTenant;
import com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.Tenant;
import com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.WithoutTenant;
import com.nbd.ocp.core.repository.multiTenancy.context.TenantContextHolder;
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
@Conditional(MultiTenancyDiscriminatorCondition.class)
public class MultiTenancyDiscriminatorAspect {


  @Pointcut(value = "(@within(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.CurrentTenant) || @annotation(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.CurrentTenant))")
  void hasCurrentTenantAnnotation() {}

  @Pointcut(value = "@within(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.Tenant) || @annotation(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.Tenant)")
  void hasTenantAnnotation() {}

  @Pointcut(value = "@within(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.WithoutTenant) || @annotation(com.nbd.ocp.core.repository.multiTenancy.discriminator.annotations.WithoutTenant)")
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
      if (multiTenantAnnotation != null && !(multiTenantAnnotation instanceof WithoutTenant)) {
        String tenantId = TenantContextHolder.getContext().getTenantId();
        if (multiTenantAnnotation instanceof Tenant) {
          tenantId = ((Tenant) multiTenantAnnotation).value();
        }
        org.hibernate.Filter filter = entityManager.unwrap(Session.class).enableFilter("tenantFilter");
        filter.setParameter("tenantId", tenantId);
        filter.validate();
      }
    return pjp.proceed();
  }

  private Annotation getMultiTenantAnnotation(AnnotatedElement element) {
    Annotation annotation = element.getAnnotation(CurrentTenant.class);
    if (annotation != null) {
      return annotation;
    }
    annotation = element.getAnnotation(Tenant.class);
    if (annotation != null) {
      return annotation;
    }
    annotation = element.getAnnotation(WithoutTenant.class);
    if (annotation != null) {
      return annotation;
    }
    return null;
  }
}
