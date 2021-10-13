import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author WingsiWoo
 * @date 2021/10/13
 */
public class Application {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        // 解决 java.awt.HeadlessException 异常
        builder.headless(false).web(WebApplicationType.NONE).run(args);
    }
}
