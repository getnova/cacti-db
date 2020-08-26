package net.getnova.backend.cacti.services;

import net.getnova.backend.cacti.models.CareGroup;
import net.getnova.backend.cacti.reposetories.CareGroupRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public final class CareGroupService {

  private final CareGroupRepository careGroupRepository;

  public CareGroupService(final CareGroupRepository careGroupRepository) {
    this.careGroupRepository = careGroupRepository;
  }

  @PostConstruct
  private void postConstruct() {
    final List<CareGroup> careGroups = this.careGroupRepository.findAll();

    this.careGroupRepository.save(this.updateCareGroupVersion(careGroups, new CareGroup("1",
      "Pflegegruppe 1", "tropische Uhrwälder, feuchtwarmes Klima", "Humusboden, pH 4.5 - 5.5, für Hydrokultur geeignet",
      null, "feuchtwarmer Stand", null, null, null,
      null, null, "über 10°C", "mäßig feucht", null)));

    this.careGroupRepository.save(this.updateCareGroupVersion(careGroups, new CareGroup("2",
      "Pflegegruppe 2", "Savannen, lichte Wälder", "Humusboden, pH 4.5 - 5.5, für Hydrokultur geeignet",
      "halbsonnig bis vollsonnig", null, "warm", "feucht", null,
      null, null, "min. 8°C", "trocken", null)));

    this.careGroupRepository.save(this.updateCareGroupVersion(careGroups, new CareGroup("3",
      "Pflegegruppe 3", "Steppe & Gebirge", "lehmig-kiesig, schwach sauer, pH 4.5 - 6",
      "sonnig", "freier Stand", null, "kaum nässeempfindlich", null,
      null, "trockener Stand", "nicht unter 0°C", null, null)));

    this.careGroupRepository.save(this.updateCareGroupVersion(careGroups, new CareGroup("3a",
      "Pflegegruppe 3a", "kühles Klima", "kiesig-lehmig, pH 4.5 - 6",
      "sonnig", "freier Stand", null, null, null,
      null, "trockender Stand", "kälteverträglich, bei geeignetem Substrat bis -25°C", null,
      "Überdauern den Winter unter einer Schneeecke sehr gut, gefährlich sind Kahlfröste nach vorhergegangener nasser Wärmeperiode,"
        + "abdecken mit Reisig oder Brettern, auch überwintern in trockenem Frühbeetkasten mit Sonnenschutz")));

    this.careGroupRepository.save(this.updateCareGroupVersion(careGroups, new CareGroup("4",
      "Pflegegruppe 4", "warme Steppe", "sandig-kiesig, humusarm, pH 5 - 6",
      "sonnig", "möglichtst freier Stand", "warmer Stand", "meist etwas nässeempfindlich", null,
      null, null, "min. 5°C", "trocken", null)));

    this.careGroupRepository.save(this.updateCareGroupVersion(careGroups, new CareGroup("5",
      "Pflegegruppe 5", "warme Trockengebiete", "kiesig, rein mineralisch, pH 5.6 - 6.5, Pfropfung zu emppfehlen",
      "vollsonnig", "warmer Stand", null, "nässeempfindlich, Wurzelhals trocken halten", null,
      "möglichst hell", null, "min. 4°C", "trocken", null)));

    this.careGroupRepository.save(this.updateCareGroupVersion(careGroups, new CareGroup("6",
      "Pflegegruppe 6", "sehr warme Steppen", "kiesig-lehmig, pH 5 - 6",
      "sonnig", null, "warm", "nässeempfindlich", null,
      null, null, "min. 8°C", "trocken bis schwach feucht", null)));

    this.careGroupRepository.save(this.updateCareGroupVersion(careGroups, new CareGroup("7",
      "Pflegegruppe 7", "Gebirge, Hochgebirge & Steppen", "unterschiedliche Mischungen & pH Werte, deshalb in Pflegegruppe 7a & 7b unterteilt",
      "sonnig", "luftig, freier Stand", null, null, null,
      null, null, null, null, "unterschiedliche Temperaturansprüche, deshalb in Pflegegruppe 7a & 7b unterteilt")));

    this.careGroupRepository.save(this.updateCareGroupVersion(careGroups, new CareGroup("7a",
      "Pflegegruppe 7a", "Gebirge, Hochgebirge", "sandig-lehmig, schwach sauer, pH 4.5 - 6",
      "sonnig", "freier Stand", null, "kaum nässeempfindlich", null,
      "hell", "luftig", "min. 3°C & möglichst nicht über 20°C", "trocken", null)));

    this.careGroupRepository.save(this.updateCareGroupVersion(careGroups, new CareGroup("7b",
      "Pflegegruppe 7b", "Steppen & Gebirge", "sandig-mineralisch mit etwas Humusgehalt, leicht sauer, pH 5 - 6.5",
      "sonnig, Schutz vor Prallsonne (besonders bei Jungpflanzen)", "luftig, zeitweise ohne Glasbedeckung",
      "genügend, bei Wärme nicht zu wenig Feuchtichkeit", null, null,
      "hell", null, "kühl, 8 - 12°C, Gebirgsarten auch darunter, andere nur Kurzfistig darunter",
      "ziemlich trocken, Jungpflanzen und Veredlungen brauchen zur Erhaltung der Faserwurzeln ständig ein geringes Quantum Feuchtigkeit von unten", null)));

    this.careGroupRepository.save(this.updateCareGroupVersion(careGroups, new CareGroup("8",
      "Pflegegruppe 8", "Küsten- bis Gebirgsgegenden & Nebelzonen", "mineralisch-kiesig, pH 5 - 6, Pfropfung zum Teil zu empfehlen",
      "hell, im Sommer halb- bis vollsonnig, Herbst bis Frühjahr vollsonnig", "reichlich lüften", "ausreichend Wärme", "ausreichnd Feuchtichkeit", null,
      "hell", "lufttrocken", "warm, nicht unter 10°C", null, null)));
  }

  private CareGroup updateCareGroupVersion(final List<CareGroup> careGroups, final CareGroup careGroup) {
    for (final CareGroup group : careGroups) {
      if (group.getId().equals(careGroup.getId())) {
        careGroup.setVersion(group.getVersion());
        return careGroup;
      }
    }
    return careGroup;
  }
}
