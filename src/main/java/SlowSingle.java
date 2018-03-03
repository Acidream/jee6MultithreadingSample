import javax.annotation.Resource;
import javax.ejb.*;
import java.util.concurrent.Future;

/**
 * Created by oleg on 2018-03-03.
 */

@Stateless
@Asynchronous
@TransactionAttribute(TransactionAttributeType.NEVER)
public class SlowSingle {


    @Resource
    SessionContext sessionContext;


    public Future<Void> slow() {
        try {
            System.out.println("Slow started");
            for (int i = 0; i < 60 * 150; i++) {
                if (sessionContext.wasCancelCalled()) {
                    Thread.sleep(5000);
                    System.out.println("graceful shutdown");
                    break;
                }
                Thread.sleep(1000);
            }

            System.out.println("Slow complete");
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
            e.printStackTrace();
        }
        return new AsyncResult<>(null);
    }


}
