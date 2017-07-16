package it.unifi.ing.swam.bean.startup;

import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.swam.dao.AgencyDao;
import it.unifi.ing.swam.dao.ItemDao;
import it.unifi.ing.swam.dao.MissionDao;
import it.unifi.ing.swam.dao.UserDao;
import it.unifi.ing.swam.dao.WaybillDao;

public class StartupBeanTest {

    private StartupBean startupBean;

    private UserDao userDao;
    private AgencyDao agencyDao;
    private MissionDao missionDao;
    private WaybillDao waybillDao;
    private ItemDao itemDao;

    @Before
    public void setUp() throws InitializationError {
        startupBean = new StartupBean();

        userDao = mock(UserDao.class);
        agencyDao = mock(AgencyDao.class);
        missionDao = mock(MissionDao.class);
        waybillDao = mock(WaybillDao.class);
        itemDao = mock(ItemDao.class);

        try {
            FieldUtils.writeField(startupBean, "userDao", userDao, true);
            FieldUtils.writeField(startupBean, "agencyDao", agencyDao, true);
            FieldUtils.writeField(startupBean, "missionDao", missionDao, true);
            FieldUtils.writeField(startupBean, "waybillDao", waybillDao, true);
            FieldUtils.writeField(startupBean, "itemDao", itemDao, true);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }

    @Test
    public void testInit() {
        startupBean.init();
    }

}
