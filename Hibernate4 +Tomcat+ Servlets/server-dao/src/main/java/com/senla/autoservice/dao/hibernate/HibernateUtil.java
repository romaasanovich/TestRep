package com.senla.autoservice.dao.hibernate;

import com.senla.autoservice.bean.*;
import com.senla.autoservice.bean.aentity.AEntity;
import com.senla.autoservice.bean.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public final class HibernateUtil {
    private static HibernateUtil instance;
    private static SessionFactory sessionFactory;

    public static HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    public  SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            final Configuration configuration = new Configuration();
            initProperties(configuration);
            configuration.addAnnotatedClass(AEntity.class)
                    .addAnnotatedClass(Place.class)
                    .addAnnotatedClass(Master.class)
                    .addAnnotatedClass(Work.class)
                    .addAnnotatedClass(Order.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(AuditLog.class);

            final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }

    private void initProperties(final Configuration configuration) {
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.username", "root")
                .setProperty("hibernate.connection.password", "12345")
                .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mydb")
                .setProperty("show_sql", "true")
                .setProperty("format_sql", "true")
                .setProperty("hibernate.current_session_context_class","thread");

    }
}
