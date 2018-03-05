import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Created by oleg on 2018-03-03.
 */

@Startup
@Singleton
public class Starter {

    @EJB
    SamplePool sp;

    @PostConstruct
    public void init() {

        sp.start();

    }

}
