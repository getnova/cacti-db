package net.getnova.module.cacti.endpoint;

import io.netty.handler.codec.http.HttpResponseStatus;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.getnova.framework.api.annotations.ApiEndpoint;
import net.getnova.framework.api.annotations.ApiEndpointCollection;
import net.getnova.framework.api.annotations.ApiParameter;
import net.getnova.framework.api.data.ApiResponse;
import net.getnova.framework.api.data.ApiType;
import net.getnova.framework.json.JsonUtils;
import net.getnova.module.cacti.CactiException;
import net.getnova.module.cacti.model.Cactus;
import net.getnova.module.cacti.model.CareGroup;
import net.getnova.module.cacti.model.Form;
import net.getnova.module.cacti.model.Genus;
import net.getnova.module.cacti.model.Specie;
import net.getnova.module.cacti.repository.CactusRepository;
import net.getnova.module.cacti.repository.CareGroupRepository;
import net.getnova.module.cacti.repository.FormRepository;
import net.getnova.module.cacti.repository.GenusRepository;
import net.getnova.module.cacti.repository.SpecieRepository;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@ApiEndpointCollection(id = "cactus", description = "Handle all cacti.", type = ApiType.REST)
public class CactusEndpointCollection {

  private final GenusRepository genusRepository;
  private final SpecieRepository specieRepository;
  private final FormRepository formRepository;
  private final CareGroupRepository careGroupRepository;
  private final CactusRepository cactusRepository;

  @ApiEndpoint(id = "list", description = "Lists all cacti.")
  public ApiResponse list() {
    return new ApiResponse(HttpResponseStatus.OK, JsonUtils.toArray(this.cactusRepository.findAllByOrderByNumber(), Cactus::serializeSmall));
  }

  @ApiEndpoint(id = "get", description = "Return the cactus with the specified id.")
  public ApiResponse get(@ApiParameter(id = "id", description = "The id of the cactus.") final UUID id) {
    final Optional<Cactus> cactus = this.cactusRepository.findById(id);
    if (cactus.isEmpty()) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS");
    return new ApiResponse(HttpResponseStatus.OK, cactus.get());
  }

  @Transactional
  @ApiEndpoint(id = "add", description = "Add a cactus.")
  public ApiResponse add(@ApiParameter(id = "number", description = "The number of the cactus.") final String number,
                         @ApiParameter(id = "subId", description = "The id of the sub type.", required = false) final UUID subId,
                         @ApiParameter(id = "subType", description = "The type of the sub type.", required = false) final String subType) {

    if (this.cactusRepository.findByNumber(number).isPresent()) {
      return new ApiResponse(HttpResponseStatus.BAD_REQUEST, "NUMBER_ALREADY_EXIST");
    }

    final Cactus cactus = new Cactus(number);

    try {
      this.setSubType(cactus, subId, subType);
    } catch (CactiException e) {
      return new ApiResponse(HttpResponseStatus.NOT_FOUND, e.getMessage());
    }

    return new ApiResponse(HttpResponseStatus.OK, this.cactusRepository.save(cactus).serializeSmall());
  }

  @Transactional
  @ApiEndpoint(id = "updateNumber", description = "Updates only the number of a cactus.")
  public ApiResponse updateNumber(@ApiParameter(id = "id", description = "The id of the existing cactus.") final UUID id,
                                  @ApiParameter(id = "number", description = "The new/old number of the cactus.") final String number) {

    if (this.cactusRepository.findByNumber(number).isPresent()) {
      return new ApiResponse(HttpResponseStatus.BAD_REQUEST, "NUMBER_ALREADY_EXIST");
    }

    final Cactus cactus = this.cactusRepository.findById(id).orElse(null);
    if (cactus == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS");

    cactus.setNumber(number);
    return new ApiResponse(HttpResponseStatus.OK, this.cactusRepository.save(cactus));
  }

  @Transactional
  @ApiEndpoint(id = "update", description = "Update a cactus.")
  public ApiResponse update(@ApiParameter(id = "id", description = "The id of the existing cactus.") final UUID id,
                            @ApiParameter(id = "number", description = "The new/old number of the cactus.") final String number,

                            @ApiParameter(id = "subId", description = "The id of the sub type.", required = false) final UUID subId,
                            @ApiParameter(id = "subType", description = "The type of the sub type.", required = false) final String subType,

                            @ApiParameter(id = "fieldNumber", description = "todo", required = false) final String fieldNumber,
                            @ApiParameter(id = "synonyms", description = "todo", required = false) final String synonyms,

                            @ApiParameter(id = "acquisitionTimestamp", description = "todo", required = false) final OffsetDateTime acquisitionTimestamp,
                            @ApiParameter(id = "acquisitionAge", description = "todo", required = false) final Duration acquisitionAge,
                            @ApiParameter(id = "acquisitionPlace", description = "todo", required = false) final String acquisitionPlace,
                            @ApiParameter(id = "acquisitionPlantType", description = "todo", required = false) final String acquisitionPlantType,

                            @ApiParameter(id = "stateNoLongerInPossessionTimestamp",
                              description = "todo", required = false) final OffsetDateTime stateNoLongerInPossessionTimestamp,
                            @ApiParameter(id = "stateNoLongerInPossessionReason", description = "todo", required = false) final String stateNoLongerInPossessionReason,
                            @ApiParameter(id = "stateVitality", description = "todo", required = false) final String stateVitality,

                            @ApiParameter(id = "flowerColor", description = "todo", required = false) final String flowerColor,

                            @ApiParameter(id = "careGroupId", description = "todo", required = false) final String careGroupId,
                            @ApiParameter(id = "careGroupHome", description = "todo", required = false) final String careGroupHome,
                            @ApiParameter(id = "careGroupSoil", description = "todo", required = false) final String careGroupSoil,

                            @ApiParameter(id = "careGroupGrowTimeLight", description = "todo", required = false) final String careGroupGrowTimeLight,
                            @ApiParameter(id = "careGroupGrowTimeAir", description = "todo", required = false) final String careGroupGrowTimeAir,
                            @ApiParameter(id = "careGroupGrowTimeTemperature", description = "todo", required = false) final String careGroupGrowTimeTemperature,
                            @ApiParameter(id = "careGroupGrowTimeHumidity", description = "todo", required = false) final String careGroupGrowTimeHumidity,
                            @ApiParameter(id = "careGroupGrowTimeOther", description = "todo", required = false) final String careGroupGrowTimeOther,

                            @ApiParameter(id = "careGroupRestTimeLight", description = "todo", required = false) final String careGroupRestTimeLight,
                            @ApiParameter(id = "careGroupRestTimeAir", description = "todo", required = false) final String careGroupRestTimeAir,
                            @ApiParameter(id = "careGroupRestTimeTemperature", description = "todo", required = false) final String careGroupRestTimeTemperature,
                            @ApiParameter(id = "careGroupRestTimeHumidity", description = "todo", required = false) final String careGroupRestTimeHumidity,
                            @ApiParameter(id = "careGroupRestTimeOther", description = "todo", required = false) final String careGroupRestTimeOther) {

    final Cactus cactus = this.cactusRepository.findById(id).orElse(null);
    if (cactus == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS");

    final Optional<Cactus> cactusByNumber = this.cactusRepository.findByNumber(number);
    if (cactusByNumber.isEmpty() || !cactusByNumber.get().getId().equals(id)) {
      return new ApiResponse(HttpResponseStatus.BAD_REQUEST, "NUMBER_ALREADY_EXIST");
    }

    cactus.setNumber(number);

    try {
      this.setSubType(cactus, subId, subType);
    } catch (CactiException e) {
      return new ApiResponse(HttpResponseStatus.NOT_FOUND, e.getMessage());
    }

    cactus.setFieldNumber(fieldNumber);
    cactus.setSynonyms(synonyms);

    /* --> acquisition */
    cactus.setAcquisition(acquisitionTimestamp == null
      && acquisitionAge == null
      && acquisitionPlace == null
      && acquisitionPlantType == null
      ? null
      : new Cactus.Acquisition(acquisitionTimestamp,
      acquisitionAge,
      acquisitionPlace,
      acquisitionPlantType));
    /* <-- acquisition */

    /* --> state */
    cactus.setState(stateNoLongerInPossessionTimestamp == null
      && stateNoLongerInPossessionReason == null
      && stateVitality == null
      ? null
      : new Cactus.State(stateNoLongerInPossessionTimestamp,
      stateNoLongerInPossessionReason,
      stateVitality));
    /* <-- state */

    cactus.setFlowerColor(flowerColor);

    /* --> care group */
    CareGroup careGroup = null;
    if (careGroupId != null) {
      careGroup = this.careGroupRepository.findById(careGroupId).orElse(null);
      if (careGroup == null) return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CARE_GROUP");
    }
    cactus.setCareGroup(careGroup);

    final boolean careGroupExist = careGroup != null;
    cactus.setCareGroupHome(careGroupExist && Objects.equals(careGroup.getHome(), careGroupHome) ? null : careGroupHome);
    cactus.setCareGroupSoil(careGroupExist && Objects.equals(careGroup.getSoil(), careGroupSoil) ? null : careGroupSoil);

    /* --> grow time */
    cactus.setCareGroupGrowTimeLight(careGroupExist && Objects.equals(careGroup.getGrowTimeLight(), careGroupGrowTimeLight) ? null : careGroupGrowTimeLight);
    cactus.setCareGroupGrowTimeAir(careGroupExist && Objects.equals(careGroup.getGrowTimeAir(), careGroupGrowTimeAir) ? null : careGroupGrowTimeAir);
    cactus.setCareGroupGrowTimeTemperature(careGroupExist && Objects.equals(careGroup.getGrowTimeTemperature(), careGroupGrowTimeTemperature)
      ? null : careGroupGrowTimeTemperature);
    cactus.setCareGroupGrowTimeHumidity(careGroupExist && Objects.equals(careGroup.getGrowTimeHumidity(), careGroupGrowTimeHumidity) ? null : careGroupGrowTimeHumidity);
    cactus.setCareGroupGrowTimeOther(careGroupExist && Objects.equals(careGroup.getGrowTimeOther(), careGroupGrowTimeOther) ? null : careGroupGrowTimeOther);
    /* <-- grow time */

    /* --> rest time */
    cactus.setCareGroupRestTimeLight(careGroupExist && Objects.equals(careGroup.getRestTimeLight(), careGroupRestTimeLight) ? null : careGroupRestTimeLight);
    cactus.setCareGroupRestTimeAir(careGroupExist && Objects.equals(careGroup.getRestTimeAir(), careGroupRestTimeAir) ? null : careGroupRestTimeAir);
    cactus.setCareGroupRestTimeTemperature(careGroupExist && Objects.equals(careGroup.getRestTimeTemperature(), careGroupRestTimeTemperature)
      ? null : careGroupRestTimeTemperature);
    cactus.setCareGroupRestTimeHumidity(careGroupExist && Objects.equals(careGroup.getRestTimeHumidity(), careGroupRestTimeHumidity) ? null : careGroupRestTimeHumidity);
    cactus.setCareGroupRestTimeOther(careGroupExist && Objects.equals(careGroup.getRestTimeOther(), careGroupRestTimeOther) ? null : careGroupRestTimeOther);
    /* <-- rest time */

    /* <-- care group */

    return new ApiResponse(HttpResponseStatus.OK, this.cactusRepository.save(cactus));
  }

  @Transactional
  @ApiEndpoint(id = "delete", description = "Delete a cactus.")
  public ApiResponse delete(@ApiParameter(id = "id", description = "The id of the cactus, witch should be deleted.") final UUID id) {
    final Optional<Cactus> cactus = this.cactusRepository.findById(id);
    if (cactus.isEmpty()) {
      return new ApiResponse(HttpResponseStatus.NOT_FOUND, "CACTUS");
    }

    this.cactusRepository.delete(cactus.get());
    return new ApiResponse(HttpResponseStatus.OK);
  }

  private void setSubType(final Cactus cactus, final UUID subId, final String subType) throws CactiException {
    if (subId == null || subType == null) {
      cactus.setForm(null);
      cactus.setSpecie(null);
      cactus.setGenus(null);
      return;
    }

    switch (subType) {
      case "GENUS" -> {
        final Genus genus = this.genusRepository.findById(subId).orElse(null);
        if (genus == null) throw new CactiException("GENUS");
        cactus.setForm(null);
        cactus.setSpecie(null);
        cactus.setGenus(genus);
      }
      case "SPECIE" -> {
        final Specie specie = this.specieRepository.findById(subId).orElse(null);
        if (specie == null) throw new CactiException("SPECIE");
        cactus.setForm(null);
        cactus.setSpecie(specie);
        cactus.setGenus(specie.getGenus());
      }
      case "FORM" -> {
        final Form form = this.formRepository.findById(subId).orElse(null);
        if (form == null) throw new CactiException("FORM");
        cactus.setForm(form);
        cactus.setSpecie(form.getSpecie());
        cactus.setGenus(form.getSpecie().getGenus());
      }
      default -> throw new CactiException("SUBTYPE");
    }
  }
}
