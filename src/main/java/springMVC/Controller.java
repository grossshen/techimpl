package springMVC;

import java.lang.annotation.*;

/**
 * 手写Controller注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String value();
}
