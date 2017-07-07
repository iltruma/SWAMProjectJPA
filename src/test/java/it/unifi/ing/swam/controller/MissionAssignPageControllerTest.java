package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.MissionBean;
import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class MissionAssignPageControllerTest {

    private MissionAssignPageController missionAssignPageController;
    private UserSessionBean userSession;
    private MissionBean conversationBean;

    private User user;
    private Mission mission;
    private User wrongUser;
    private Waybill waybill;
    @Before
    public void setUp() throws Exception {
        missionAssignPageController = new MissionAssignPageController();
        userSession = new UserSessionBean();

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateOperator());
        userSession.setUser(user);

        wrongUser = ModelFactory.generateUser();

        mission = ModelFactory.generateMission();
        conversationBean = new MissionBean();
        conversationBean.setMission(mission);

        waybill = ModelFactory.generateWaybill();
        mission.addWaybill(waybill);

        try {
            FieldUtils.writeField(missionAssignPageController, "userSession", userSession, true);
            FieldUtils.writeField(missionAssignPageController, "conversationBean", conversationBean, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testInitMissionAssignPage() {
        missionAssignPageController.initMissionAssignPage();
        assertEquals(mission, conversationBean.getMission());

        conversationBean.setMission(null);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            missionAssignPageController.initMissionAssignPage();
        });
    }

    @Test
    public void testInitMissionAssignPageThrowsIllegalArgumentException() {
        userSession.setUser(wrongUser);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            missionAssignPageController.initMissionAssignPage();
        });
    }

    @Test
    public void testRemove() throws InitializationError {
        missionAssignPageController.initMissionAssignPage();
        missionAssignPageController.remove(waybill);
        assertEquals(0, missionAssignPageController.getMission().getWaybills().size());
        assertTrue(!missionAssignPageController.getMission().getWaybills().contains(waybill));

    }

}
