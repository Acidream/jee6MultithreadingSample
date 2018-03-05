import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.concurrent.Future;

/**
 * Created by oleg on 2018-03-03.
 */
@Singleton
public class SamplePool extends AbstractTimerPool {
    @EJB
    SlowSingle slowSingle;

    @Override
    protected Future<Void> doWork() {
        return slowSingle.slow();
    }
}
