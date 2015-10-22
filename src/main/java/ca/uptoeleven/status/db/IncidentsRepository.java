package ca.uptoeleven.status.db;

import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.db.models.IncidentUpdate;
import org.skife.jdbi.v2.DBI;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class IncidentsRepository {

    private final DBI dbi;
    private final IncidentsDAO incidentsDAO;
    private final IncidentUpdatesDAO updatesDAO;
    private final ServicesDAO servicesDAO;

    @Inject
    public IncidentsRepository(DBI dbi, IncidentsDAO incidentsDAO, IncidentUpdatesDAO updatesDAO, ServicesDAO servicesDAO) {
        this.dbi = dbi;
        this.incidentsDAO = incidentsDAO;
        this.updatesDAO = updatesDAO;
        this.servicesDAO = servicesDAO;
    }

    public List<Incident> getAllIncidents() {
        List<Incident> allIncidents = incidentsDAO.findAllIncidents();
        return allIncidents.stream().map(incident -> populateUpdates(incident)).collect(Collectors.toList());
    }

    private Incident populateUpdates(Incident incident) {
        List<IncidentUpdate> updates = updatesDAO.findByIncidentId(incident.getId());
        return incident.withIncidentUpdates(updates);
    }

    public Incident getIncident(String incidentId) {
        Incident incident = incidentsDAO.findById(incidentId);
        List<IncidentUpdate> updates = updatesDAO.findByIncidentId(incidentId);
        return incident.withIncidentUpdates(updates);
    }

    public Incident createIncident(final Incident incident, final IncidentUpdate initialUpdate) {
        return dbi.inTransaction((handle, transactionStatus) -> {
            Incident created = incidentsDAO.create(incident);
            createUpdateAndUpdateAffectedServices(incident, initialUpdate.withIncidentId(created.getId()));
            return created;
        });
    }

    public void updateIncident(final IncidentUpdate update) {
        dbi.inTransaction((handle, transactionStatus) -> {
            Incident incident = incidentsDAO.findById(update.getIncidentId());
            incidentsDAO.updateState(update.getIncidentId(), update.getNewState());
            createUpdateAndUpdateAffectedServices(incident, update);
            return null;
        });
    }

    private void createUpdateAndUpdateAffectedServices(final Incident incident, final IncidentUpdate update) {
        updatesDAO.create(update);
        for(String serviceId : incident.getAffectedServicesIds()) {
            servicesDAO.updateServiceStatusId(serviceId, update.getNewServiceStatusId());
        }
    }
}
