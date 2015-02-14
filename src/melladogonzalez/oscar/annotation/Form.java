package melladogonzalez.oscar.annotation;

public @interface Form {
	String name() default "";
	boolean main() default false;
	String background() default "";
}
