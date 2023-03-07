package com.dmh.msgateway.configuration;

import org.keycloak.enums.SslRequired;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.RealmManager;
import org.keycloak.services.resources.KeycloakApplication;

public class EmbeddedKeycloakApplication extends KeycloakApplication {


    public EmbeddedKeycloakApplication() {
        super();
        changeMasterRealm();

    }

    private void changeMasterRealm() {
        KeycloakSession session = getSessionFactory().create();
        try {
            session.getTransactionManager().begin();
            RealmManager manager = new RealmManager(session);
            manager.getRealm("master").setSslRequired(SslRequired.NONE);
            session.getTransactionManager().commit();
        } catch (Exception ex) {
            session.getTransactionManager().rollback();
        }
    }
}