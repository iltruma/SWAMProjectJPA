package it.unifi.ing.swam.controller;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.bean.ConversationMissionBean;
import it.unifi.ing.swam.bean.UserSessionBean;
import it.unifi.ing.swam.dao.WaybillDao;
import it.unifi.ing.swam.model.Agency;
import it.unifi.ing.swam.model.Item;
import it.unifi.ing.swam.model.Mission;
import it.unifi.ing.swam.model.ModelFactory;
import it.unifi.ing.swam.model.User;
import it.unifi.ing.swam.model.Waybill;

public class WaybillsToAssignPageControllerTest extends BasicController {

    private WaybillsToAssignPageController waybillsToAssignPageController;
    private ConversationMissionBean conversationBean;
    private UserSessionBean userSession;

    private WaybillDao waybillDao;

    private Waybill waybill;
    private User user;
    private User wrongUser;
    private Agency agency;
    private Mission mission;

    private Long waybillId = 1L;
    private Float truckVolume = 10F;
    private Float truckWeight = 10F;

    @Before
    public void setUp() throws InitializationError {
        waybillsToAssignPageController = new WaybillsToAssignPageController();
        conversationBean = new ConversationMissionBean();
        userSession = new UserSessionBean();

        waybillDao = mock(WaybillDao.class);

        agency = ModelFactory.generateAgency();

        user = ModelFactory.generateUser();
        user.addRole(ModelFactory.generateOperator());
        user.setAgency(agency);

        userSession.setUser(user);

        wrongUser = ModelFactory.generateUser();
        wrongUser.addRole(ModelFactory.generateDriver());

        mission = ModelFactory.generateMission();

        waybill = ModelFactory.generateWaybill();
        Item item = ModelFactory.generateItem();
        item.setVolume(5F);
        item.setWeigth(5F);
        waybill.getLoad().addItem(item);
        List<Waybill> result = new ArrayList<>();
        result.add(waybill);

        when(waybillDao.findUnassignedToDriver(agency)).thenReturn(result);

        conversationBean.setTruckVolume(truckVolume);
        conversationBean.setTruckWeight(truckWeight);
        conversationBean.setMission(mission);

        try {
            FieldUtils.writeField(waybill, "id", waybillId, true);
            FieldUtils.writeField(waybillsToAssignPageController, "waybillDao", waybillDao, true);
            FieldUtils.writeField(waybillsToAssignPageController, "userSession", userSession, true);
            FieldUtils.writeField(waybillsToAssignPageController, "conversationBean", conversationBean, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testInitAssignDriverPage() {
        waybillsToAssignPageController.initAssignDriverPage();

        List<Waybill> result = waybillsToAssignPageController.getWaybills();

        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
    }

    @Test
    public void testInitAssignDriverPageThrowsIllegalArgumentException() {
        userSession.setUser(wrongUser);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            waybillsToAssignPageController.initAssignDriverPage();
        });
    }

    @Test
    public void testAddWaybills() {
        waybillsToAssignPageController.initAssignDriverPage();
        List<Waybill> list = new ArrayList<>();
        list.add(waybill);
        waybillsToAssignPageController.addWaybills(list);

        List<Waybill> result = conversationBean.getMission().getWaybills();

        assertEquals(1, result.size());
        assertEquals(waybill, result.get(0));
        assertEquals(truckVolume - waybill.getVolume(), waybillsToAssignPageController.getRemainVolume(), 0.1);
        assertEquals(truckWeight - waybill.getWeight(), waybillsToAssignPageController.getRemainWeight(), 0.1);
    }

    @Test
    public void testAddWaybillsThrowsIllegalArgumentException() {
        waybillsToAssignPageController.initAssignDriverPage();

        Item bigItem = ModelFactory.generateItem();
        bigItem.setVolume(20F);
        bigItem.setWeigth(20F);
        waybill.getLoad().addItem(bigItem);
        List<Waybill> list = new ArrayList<>();
        list.add(waybill);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            waybillsToAssignPageController.addWaybills(list);
        });
    }

}
