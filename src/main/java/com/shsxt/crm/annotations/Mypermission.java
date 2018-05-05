package com.shsxt.crm.annotations;

import java.lang.annotation.*;

/**
 * Created by kingkill on 2018/5/3.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mypermission {
    String aclValue() default "";
}
