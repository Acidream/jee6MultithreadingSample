import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by oleg on 2018-03-03.
 */
@Singleton
@Startup
public class MainTimer {

    int maxTreads = 5;

    @EJB
    SlowSingle slowSingle;

    @Resource
    TimerService timerService;
    private List<Future<Void>> futures = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        TimerConfig config = new TimerConfig();
        config.setPersistent(false);
        timerService.createIntervalTimer(0, 100L, config);
    }

    @Timeout
    public void execute() {
        removeComplete();
        if (futures.size() < maxTreads) {
            System.out.println("----Invoked       : " + System.currentTimeMillis());
            futures.add(slowSingle.slow());
            System.out.println("----InvokeComplete: " + System.currentTimeMillis());
        }
    }

    private void removeComplete() {
        for (Iterator<Future<Void>> iter = futures.listIterator(); iter.hasNext(); ) {
            Future<Void> f = iter.next();
            if (f.isDone()) {
                iter.remove();
            }
        }
    }
}



