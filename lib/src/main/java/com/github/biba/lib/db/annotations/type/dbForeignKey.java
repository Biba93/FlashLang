package com.github.biba.lib.db.annotations.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface dbForeignKey {

    String type() default "FOREIGN KEY";

    String referredTableName();

    String referredTableColumnName();

}
