package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.core.IncidentState;
import ca.uptoeleven.status.core.Service;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import java.util.List;

import static ca.uptoeleven.status.db.DBTestHelpers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IncidentsDAOTest {

	@Rule
	public H2JDBIRule h2JDBIRule = new H2JDBIRule();

	private DBI dbi;
	private IncidentsDAO incidentsDAO;
	private ServicesDAO servicesDAO;

	@Before
	public void setup() {
		this.dbi = h2JDBIRule.getDbi();
		this.incidentsDAO = incidentsDAO(h2JDBIRule.getDbi());
		this.servicesDAO = servicesDAO(h2JDBIRule.getDbi());
	}

	@Test
	public void findActiveIncidentsOmitsResolvedIncidents() {
		Incident open = newIncidentForTest().withState(IncidentState.INVESTIGATING);
		Incident resolved = newIncidentForTest().withState(IncidentState.RESOLVED);
		incidentsDAO.create(open);
		incidentsDAO.create(resolved);

		List<Incident> incidents = incidentsDAO.findActiveIncidents();
		assertEquals(1, incidents.size());
		assertEquals(open.getId(), incidents.get(0).getId());
	}

	@Test
	public void insertIncidentSucceedsWithoutError() {
		incidentsDAO.insert(newIncidentWithUpdateForTest().withId(newId()));
	}

	@Test
	public void createIncidentSetsId() {
		Incident incident = newIncidentWithUpdateForTest();
		incident = incidentsDAO.create(incident);
		assertNotNull(incident.getId());
	}

	@Test
	public void createIncidentSavesAffectedServiceId() {
		Service service = newServiceForTest().withId(newId());
		servicesDAO.insert(service);

		Incident incident = newIncidentWithUpdateForTest().withAffectedServicesIdsList(Lists.newArrayList(service.getId()));
		incident = incidentsDAO.create(incident);
		List<String> affectedIds = incidentsDAO.findAffectedServiceIds(incident.getId());
		assertEquals(1, affectedIds.size());
		assertEquals(service.getId(), affectedIds.get(0));
	}

	@Test
	public void createdIncidentCanBeSelected() {
		Incident toInsert = newIncidentForTest();
		toInsert = incidentsDAO.create(toInsert);
		Incident selected = incidentsDAO.findById(toInsert.getId());
		assertNotNull(selected);
		assertEquals(toInsert, selected);
	}

}
