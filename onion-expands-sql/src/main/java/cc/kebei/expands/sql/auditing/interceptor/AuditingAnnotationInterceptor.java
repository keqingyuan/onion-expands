package cc.kebei.expands.sql.auditing.interceptor;

import cc.kebei.expands.sql.auditing.annotation.CreatedBy;
import cc.kebei.expands.sql.auditing.annotation.CreatedDate;
import cc.kebei.expands.sql.auditing.annotation.LastModifiedBy;
import cc.kebei.expands.sql.auditing.annotation.LastModifiedDate;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Properties;

/**
 * Created by qingyuan on 2019/6/26.
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class AuditingAnnotationInterceptor implements Interceptor {

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
    SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
    Object parameter = invocation.getArgs()[1];
    Field[] fields = parameter.getClass().getDeclaredFields();
    Timestamp timestamp = Timestamp.from(Instant.now());
    String user = "unknown";
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      user = SecurityContextHolder.getContext().getAuthentication().getName();
    }
    if (SqlCommandType.UPDATE == sqlCommandType) {
      for (Field field : fields) {
        // LastModifiedBy
        if (AnnotationUtils.getAnnotation(field, LastModifiedBy.class) != null) {
          field.setAccessible(true);
          field.set(parameter, user);
          field.setAccessible(false);
        }
        // LastModifiedDate
        if (AnnotationUtils.getAnnotation(field, LastModifiedDate.class) != null) {
          field.setAccessible(true);
          field.set(parameter, timestamp);
          field.setAccessible(false);
        }
      }
    } else if (SqlCommandType.INSERT == sqlCommandType) {
      // CreatedBy
      for (Field field : fields) {
        if (AnnotationUtils.getAnnotation(field, CreatedBy.class) != null) {
          field.setAccessible(true);
          field.set(parameter, user);
          field.setAccessible(false);
        }
        // CreatedDate
        if (AnnotationUtils.getAnnotation(field, CreatedDate.class) != null) {
          field.setAccessible(true);
          field.set(parameter, timestamp);
          field.setAccessible(false);
        }
        // LastModifiedBy
        if (AnnotationUtils.getAnnotation(field, LastModifiedBy.class) != null) {
          field.setAccessible(true);
          field.set(parameter, user);
          field.setAccessible(false);
        }
        // LastModifiedDate
        if (AnnotationUtils.getAnnotation(field, LastModifiedDate.class) != null) {
          field.setAccessible(true);
          field.set(parameter, timestamp);
          field.setAccessible(false);
        }
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
