package net.getnova.backend.cacti.models;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.getnova.backend.json.JsonBuilder;
import net.getnova.backend.json.JsonSerializable;
import net.getnova.backend.sql.model.TableModelAutoId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Duration;
import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cacti_cactus")
public final class Cactus extends TableModelAutoId implements JsonSerializable {

    @Column(name = "number", nullable = false, updatable = true, length = 128)
    private String number;

    @ManyToOne
    @JoinColumn(name = "genus_id", nullable = true, updatable = false)
    private Genus genus;

    @ManyToOne
    @JoinColumn(name = "specie_id", nullable = true, updatable = false)
    private Specie specie;

    @ManyToOne
    @JoinColumn(name = "form_id", nullable = true, updatable = false)
    private Form form;

    @Column(name = "field_number", nullable = true, updatable = true, length = 128)
    private String fieldNumber;

    @Column(name = "synonyms", nullable = true, updatable = true, length = 1024)
    private String synonyms;

    @Column(name = "acquisition_timestamp", nullable = true, updatable = true)
    private OffsetDateTime acquisitionTimestamp;

    @Column(name = "acquisition_age", nullable = true, updatable = true)
    private Duration acquisitionAge;

    @Column(name = "acquisition_place", nullable = true, updatable = true, length = 512)
    private String acquisitionPlace;

    @Column(name = "acquisition_plant_type", nullable = true, updatable = true, length = 512)
    private String acquisitionPlantType;

    @Column(name = "state_no_longer_in_possession_timestamp", nullable = true, updatable = true)
    private OffsetDateTime stateNoLongerInPossessionTimestamp;

    @Column(name = "state_no_longer_in_possession_reason", nullable = true, updatable = true)
    private String stateNoLongerInPossessionReason;

    @Column(name = "state_vitality", nullable = true, updatable = true, length = 128)
    private String stateVitality;

    @Column(name = "flower_color", nullable = true, updatable = true, length = 128)
    private String flowerColor;

    @ManyToOne
    @JoinColumn(name = "care_group_id", nullable = true, updatable = true)
    private CareGroup careGroup;

    @Column(name = "care_group_home", nullable = true, updatable = true, length = 512)
    private String careGroupHome;

    @Column(name = "care_group_soil", nullable = true, updatable = true, length = 512)
    private String careGroupSoil;

    @Column(name = "care_group_grow_time_light", nullable = true, updatable = true, length = 512)
    private String careGroupGrowTimeLight;

    @Column(name = "care_group_grow_time_air", nullable = true, updatable = true, length = 512)
    private String careGroupGrowTimeAir;

    @Column(name = "care_group_grow_time_temperature", nullable = true, updatable = true, length = 512)
    private String careGroupGrowTimeTemperature;

    @Column(name = "care_group_grow_time_humidity", nullable = true, updatable = true, length = 512)
    private String careGroupGrowTimeHumidity;

    @Column(name = "care_group_grow_time_other", nullable = true, updatable = true, length = 1024)
    private String careGroupGrowTimeOther;

    @Column(name = "care_group_rest_time_light", nullable = true, updatable = true, length = 512)
    private String careGroupRestTimeLight;

    @Column(name = "care_group_rest_time_air", nullable = true, updatable = true, length = 512)
    private String careGroupRestTimeAir;

    @Column(name = "care_group_rest_time_temperature", nullable = true, updatable = true, length = 512)
    private String careGroupRestTimeTemperature;

    @Column(name = "care_group_rest_time_humidity", nullable = true, updatable = true, length = 512)
    private String careGroupRestTimeHumidity;

    @Column(name = "care_group_rest_time_other", nullable = true, updatable = true, length = 1024)
    private String careGroupRestTimeOther;

    public Cactus(final String number) {
        this.number = number;
    }

    public JsonElement serialize(final boolean small) {
        if (!small) return this.serialize();
        else return JsonBuilder.create("id", this.getId())
                .add("genusId", this.getGenus().getId())
                .add("specieId", this.getSpecie().getId())
                .add("formId", this.getForm().getId())
                .build();
    }

    @Override
    public JsonElement serialize() {
        final CareGroup careGroup = this.getCareGroup();
        final boolean careGroupExist = careGroup == null;

        return JsonBuilder.create("id", this.getId())
                .add("number", this.getNumber())

                .add("genusId", this.getGenus().getId())
                .add("specieId", this.getSpecie().getId())
                .add("formId", this.getForm().getId())

                .add("fieldNumber", this.getFieldNumber())
                .add("synonyms", this.getSynonyms())

                .add("acquisition", JsonBuilder.create("timestamp", this.getAcquisitionTimestamp())
                        .add("age", this.getAcquisitionAge())
                        .add("place", this.getAcquisitionPlace())
                        .add("plantType", this.getAcquisitionPlantType()))

                .add("state", JsonBuilder.create("noLongerInPossessionTimestamp", this.getStateNoLongerInPossessionTimestamp())
                        .add("noLongerInPossessionReason", this.getStateNoLongerInPossessionReason())
                        .add("age", this.getAge())
                        .add("vitality", this.getStateVitality()))

                .add("flowerColor", this.getFlowerColor())

                .add("careGroup", JsonBuilder.create("id", careGroupExist, () -> null, careGroup::getId)
                        .add("home", careGroupExist, () -> null, careGroup::getHome)
                        .add("soil", careGroupExist, () -> null, careGroup::getSoil)

                        .add("growTime", JsonBuilder.create("light", careGroupExist
                                || careGroup.getGrowTimeLight() == null, this::getCareGroupGrowTimeLight, careGroup::getGrowTimeLight)
                                .add("air", careGroupExist
                                        || careGroup.getGrowTimeAir() == null, this::getCareGroupGrowTimeAir, careGroup::getGrowTimeAir)
                                .add("temperature", careGroupExist
                                        || careGroup.getGrowTimeTemperature() == null, this::getCareGroupGrowTimeTemperature, careGroup::getGrowTimeTemperature)
                                .add("humidity", careGroupExist
                                        || careGroup.getGrowTimeHumidity() == null, this::getCareGroupGrowTimeHumidity, careGroup::getGrowTimeHumidity)
                                .add("other", careGroupExist
                                        || careGroup.getGrowTimeOther() == null, this::getCareGroupGrowTimeOther, careGroup::getGrowTimeOther))

                        .add("restTime", JsonBuilder.create("light", careGroupExist
                                || careGroup.getRestTimeLight() == null, this::getCareGroupRestTimeLight, careGroup::getRestTimeLight)
                                .add("air", careGroupExist
                                        || careGroup.getRestTimeAir() == null, this::getCareGroupRestTimeAir, careGroup::getRestTimeAir)
                                .add("temperature", careGroupExist
                                        || careGroup.getRestTimeTemperature() == null, this::getCareGroupRestTimeTemperature, careGroup::getRestTimeTemperature)
                                .add("humidity", careGroupExist
                                        || careGroup.getRestTimeHumidity() == null, this::getCareGroupRestTimeHumidity, careGroup::getRestTimeHumidity)
                                .add("other", careGroupExist
                                        || careGroup.getRestTimeOther() == null, this::getCareGroupRestTimeOther, careGroup::getRestTimeOther)))

                .build();
    }

    public Duration getAge() {
        if (this.getAcquisitionTimestamp() != null && this.getAcquisitionAge() != null) return Duration.between(
                this.getAcquisitionTimestamp().minus(this.getAcquisitionAge()),
                this.getStateNoLongerInPossessionTimestamp() == null ? OffsetDateTime.now() : this.getStateNoLongerInPossessionTimestamp()
        );
        return null;
    }
}
