package cc.kebei.expands.sql.auditing.interceptor;


import cc.kebei.expands.sql.auditing.entity.AbstractAuditingEntity;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Created by qingyuan on 2019/6/26.
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class AuditingInterceptor implements Interceptor {

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
    SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
    Object parameter = invocation.getArgs()[1];
    if (parameter instanceof AbstractAuditingEntity) {
      //Field[] fields = parameter.getClass().getDeclaredFields();
      String user = "Anonymous";
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication != null) {
        user = SecurityContextHolder.getContext().getAuthentication().getName();
      }
      if (SqlCommandType.UPDATE == sqlCommandType) {
        // LastModifiedDate
        Field lastModifiedDate = parameter.getClass().getSuperclass().getDeclaredField("lastModifiedDate");
        lastModifiedDate.setAccessible(true);
        lastModifiedDate.set(parameter, ((AbstractAuditingEntity) parameter).getCreatedDate());
        lastModifiedDate.setAccessible(false);
        // LastModifiedBy
        Field lastModifiedBy = parameter.getClass().getSuperclass().getDeclaredField("lastModifiedBy");
        lastModifiedBy.setAccessible(true);
        lastModifiedBy.set(parameter, user);
        lastModifiedBy.setAccessible(false);

      } else if (SqlCommandType.INSERT == sqlCommandType) {
        // CreatedDate
        Field createdDate = parameter.getClass().getSuperclass().getDeclaredField("createdDate");
        createdDate.setAccessible(true);
        createdDate.set(parameter, ((AbstractAuditingEntity) parameter).getCreatedDate());
        createdDate.setAccessible(false);
        // CreatedBy
        Field createdBy = parameter.getClass().getSuperclass().getDeclaredField("createdBy");
        createdBy.setAccessible(true);
        createdBy.set(parameter, user);
        createdBy.setAccessible(false);
        // LastModifiedDate
        Field lastModifiedDate = parameter.getClass().getSuperclass().getDeclaredField("lastModifiedDate");
        lastModifiedDate.setAccessible(true);
        lastModifiedDate.set(parameter, ((AbstractAuditingEntity) parameter).getCreatedDate());
        lastModifiedDate.setAccessible(false);
        // LastModifiedBy
        Field lastModifiedBy = parameter.getClass().getSuperclass().getDeclaredField("lastModifiedBy");
        lastModifiedBy.setAccessible(true);
        lastModifiedBy.set(parameter, user);
        lastModifiedBy.setAccessible(false);
      }
    }
    return invocation.proceed();
  }

  @Override
  public Object plugin(Object o) {
    if (o instanceof Executor) {
      return Plugin.wrap(o, this);
    } else {
      return o;
    }
  }

  @Override
  public void setProperties(Properties properties) {

  }

}
