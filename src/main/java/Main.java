import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by oleg on 2018-03-03.
 */
@Singleton
//@Startup
public class Main {

    @EJB
    SlowSingle slowSingle;

    private List<Future<Void>> futures = new ArrayList<>();

    @PostConstruct
    public void init() {
        System.out.println("INIT_____________________________");
        for (int i = 0; i < 50; i++) {
            futures.add(slowSingle.slow());
        }

        System.out.println("slow executed_____________________________");

    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroydestroydestroydestroydestroy_____________________________");
        for (Future<Void> future : futures) {
            future.cancel(true);

        }

    }


}
