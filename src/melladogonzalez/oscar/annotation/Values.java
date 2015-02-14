package melladogonzalez.oscar.annotation;

public @interface Values {
	int min() default 0;
	int max() default Integer.MAX_VALUE; 
}
