import com.tsg.vendingmachine.controller.VendingMachineController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
//        UserIO IO = new UserIOConsoleImpl();
//        VendingMachineView view = new VendingMachineView(IO);
//        VendingMachineDAO dao = new VendingMachineDAOImpl();
//        VendingMachineAuditDAO auditDAO = new VendingMachineAuditDAOImpl();
//        VendingMachineServiceDAO service = new VendingMachineServiceImpl(dao, auditDAO);
//        VendingMachineController controller = new VendingMachineController(view, service);
//        controller.run();


        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller =
                ctx.getBean("controller", VendingMachineController.class);
        controller.run();

    }
}