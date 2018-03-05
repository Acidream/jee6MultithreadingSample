import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by oleg on 2018-03-03.
 */
// descendants maust be @Singleton
public abstract class AbstractTimerPool {

    int maxTreads = 5;

    @EJB
    SlowSingle slowSingle;

    @Resource
    TimerService timerService;
    private List<Future<Void>> futures = new ArrayList<>();


    public void start() {
        TimerConfig config = new TimerConfig();
        config.setPersistent(false);
        timerService.createIntervalTimer(0, 100L, config);
    }

    @Timeout
    public void execute() {
        removeComplete();
        if (futures.size() < maxTreads) {
            System.out.println("----Invoked       : " + System.currentTimeMillis());
            futures.add(doWork());
            System.out.println("----InvokeComplete: " + System.currentTimeMillis());
        }
    }

    protected abstract Future<Void> doWork();

    private void removeComplete() {
        for (Iterator<Future<Void>> iter = futures.listIterator(); iter.hasNext(); ) {
            Future<Void> f = iter.next();
            if (f.isDone()) {
                iter.remove();
            }
        }
    }
}



