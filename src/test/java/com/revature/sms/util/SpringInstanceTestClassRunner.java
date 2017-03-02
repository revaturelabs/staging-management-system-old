package com.revature.sms.util;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//This class, which I stole from https://dzone.com/articles/enhancing-spring-test, allows non-static, 
//non-final variables to be used in the beforeClassSetup method of a JUnit test. This is useful for the 
//sms testing framework because it gives tests access to autowired variables, which are needed to 
//initialize the database with test data.
public class SpringInstanceTestClassRunner extends SpringJUnit4ClassRunner {

	private InstanceTestClassListener InstanceSetupListener;
    public SpringInstanceTestClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
    
    @Override
    protected Object createTest() throws Exception {
        Object test = super.createTest();
        // Note that JUnit4 will call this createTest() multiple times for each
        // test method, so we need to ensure to call "beforeClassSetup" only once.
        if (test instanceof InstanceTestClassListener && InstanceSetupListener == null) {
            InstanceSetupListener = (InstanceTestClassListener) test;
            InstanceSetupListener.beforeClassSetup();
        }
        return test;
    }
    
    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
        if (InstanceSetupListener != null)
            InstanceSetupListener.afterClassSetup();
    }
    
}	

