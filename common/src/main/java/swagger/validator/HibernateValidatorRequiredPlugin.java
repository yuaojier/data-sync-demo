package swagger.validator;

import com.google.common.base.Optional;
import org.hibernate.validator.constraints.NotBlank;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import java.lang.annotation.Annotation;

import static springfox.bean.validators.plugins.Validators.annotationFromBean;
import static springfox.bean.validators.plugins.Validators.annotationFromField;


public class HibernateValidatorRequiredPlugin implements ModelPropertyBuilderPlugin {
    private static final Class<? extends Annotation>[] REQUIRED_ANNOTATION = new Class[]{org.hibernate.validator.constraints.NotEmpty.class, NotBlank.class};

    @Override
    public void apply(ModelPropertyContext context) {
        for (Class<? extends Annotation> aClass : REQUIRED_ANNOTATION) {
            if (isAnnotation(context, aClass)) {
                context.getBuilder().required(true);
                break;
            }
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {

        return true;
    }

    <T extends Annotation> boolean isAnnotation(ModelPropertyContext context, Class<T> annotationClass) {
        Optional<T> optional = annotationFromBean(context, annotationClass).or(annotationFromField(context, annotationClass));
        return optional.isPresent();
    }
}
