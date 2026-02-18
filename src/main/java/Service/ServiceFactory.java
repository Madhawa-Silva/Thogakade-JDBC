package Service;

import Service.custom.impl.CustomerServiceImpl;
import Service.custom.impl.ItemServiceImpl;
import Service.custom.impl.OrderServiceImpl;
import Util.ServiceType;

public class  ServiceFactory {

    private static ServiceFactory instance;

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return instance == null ? instance = new ServiceFactory() : instance;
    }

    public <T extends SuperService> T getServiceType(ServiceType serviceType) {
        switch (serviceType) {
            case CUSTOMER:
                return (T) new CustomerServiceImpl();
            case ITEM:
                return (T) new ItemServiceImpl();
            case ORDER:
                return (T) new OrderServiceImpl();
            default:
                return null;
        }
    }
}
