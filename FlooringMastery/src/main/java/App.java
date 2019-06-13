import com.tsg.flooringmastery.controller.FloorMasterController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args)  {
//
//        OrderDAO orderDao = new OrderImpl();
//        UserIO io = new UserIOConsoleFileImpl();
//        FloorMasterView view = new FloorMasterView(io);
//        ProductDAO product = new ProductDAOImpl();
//        TaxDAO tax = new TaxDAOImpl();
//        FloorMasterService service = new FloorMasterServiceImpl(orderDao, product, tax);
//
//
//        FloorMasterController controller = new FloorMasterController(view, service);
//        controller.run();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FloorMasterController controller = ctx.getBean("controller", FloorMasterController.class);
        controller.run();
    }
}

