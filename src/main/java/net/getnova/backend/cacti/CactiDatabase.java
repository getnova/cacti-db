package net.getnova.backend.cacti;

import net.getnova.backend.api.handler.playground.PlaygroundService;
import net.getnova.backend.api.handler.websocket.WebsocketApiService;
import net.getnova.backend.cacti.endpoints.CactusEndpointCollection;
import net.getnova.backend.cacti.endpoints.CactusHistoryEndpointCollection;
import net.getnova.backend.cacti.endpoints.CareGroupEndpointCollection;
import net.getnova.backend.cacti.endpoints.FormEndpointCollection;
import net.getnova.backend.cacti.endpoints.GenusEndpointCollection;
import net.getnova.backend.cacti.endpoints.SpecieEndpointCollection;
import net.getnova.backend.cacti.models.Cactus;
import net.getnova.backend.cacti.models.CactusHistory;
import net.getnova.backend.cacti.models.CareGroup;
import net.getnova.backend.cacti.models.Form;
import net.getnova.backend.cacti.models.Genus;
import net.getnova.backend.cacti.models.Specie;
import net.getnova.backend.cacti.reposetories.CactusHistoryRepository;
import net.getnova.backend.cacti.reposetories.CactusHistoryRepositoryImpl;
import net.getnova.backend.cacti.reposetories.CactusRepository;
import net.getnova.backend.cacti.reposetories.CactusRepositoryImpl;
import net.getnova.backend.cacti.reposetories.CareGroupRepository;
import net.getnova.backend.cacti.reposetories.CareGroupRepositoryImpl;
import net.getnova.backend.cacti.reposetories.FormRepository;
import net.getnova.backend.cacti.reposetories.FormRepositoryImpl;
import net.getnova.backend.cacti.reposetories.GenusRepository;
import net.getnova.backend.cacti.reposetories.GenusRepositoryImpl;
import net.getnova.backend.cacti.reposetories.SpecieRepository;
import net.getnova.backend.cacti.reposetories.SpecieRepositoryImpl;
import net.getnova.backend.service.Service;
import net.getnova.backend.service.event.InitService;
import net.getnova.backend.service.event.InitServiceEvent;
import net.getnova.backend.service.event.PostInitService;
import net.getnova.backend.service.event.PostInitServiceEvent;
import net.getnova.backend.service.event.PreInitService;
import net.getnova.backend.service.event.PreInitServiceEvent;
import net.getnova.backend.sql.SqlService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Service(id = "cacti-db", depends = {SqlService.class, WebsocketApiService.class, PlaygroundService.class})
@Singleton
public final class CactiDatabase {

    @Inject
    private SqlService sqlService;

    @Inject
    private WebsocketApiService websocketApiService;

    @Inject
    private PlaygroundService playgroundService;

    @PreInitService
    private void preInit(final PreInitServiceEvent event) {
        this.sqlService.addEntity(Genus.class);
        this.sqlService.addEntity(Specie.class);
        this.sqlService.addEntity(Form.class);
        this.sqlService.addEntity(CareGroup.class);
        this.sqlService.addEntity(Cactus.class);
        this.sqlService.addEntity(CactusHistory.class);
        event.getBinder().bind(GenusRepository.class).to(GenusRepositoryImpl.class);
        event.getBinder().bind(SpecieRepository.class).to(SpecieRepositoryImpl.class);
        event.getBinder().bind(FormRepository.class).to(FormRepositoryImpl.class);
        event.getBinder().bind(CareGroupRepository.class).to(CareGroupRepositoryImpl.class);
        event.getBinder().bind(CactusRepository.class).to(CactusRepositoryImpl.class);
        event.getBinder().bind(CactusHistoryRepository.class).to(CactusHistoryRepositoryImpl.class);
    }

    @InitService
    private void init(final InitServiceEvent event) {
        this.websocketApiService.addEndpointCollection(GenusEndpointCollection.class);
        this.websocketApiService.addEndpointCollection(SpecieEndpointCollection.class);
        this.websocketApiService.addEndpointCollection(FormEndpointCollection.class);
        this.websocketApiService.addEndpointCollection(CareGroupEndpointCollection.class);
        this.websocketApiService.addEndpointCollection(CactusEndpointCollection.class);
        this.websocketApiService.addEndpointCollection(CactusHistoryEndpointCollection.class);
    }

    @PostInitService
    private void postInit(final PostInitServiceEvent event) {
        this.playgroundService.addCollections(this.websocketApiService.getCollectionsData());
    }
}
