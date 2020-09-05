package net.getnova.backend.cacti.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.getnova.backend.json.JsonTransient;
import net.getnova.backend.sql.model.TableModel;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cacti_care_group")
public class CareGroup extends TableModel {

  @Id
  @Column(name = "id", nullable = false, updatable = false, length = 2)
  private String id;

  @Column(name = "name", nullable = false, updatable = true, length = 128)
  private String name;

  @JsonTransient
  @Column(name = "home", nullable = false, updatable = true, length = 512)
  private String home;

  @JsonTransient
  @Column(name = "soil", nullable = false, updatable = true, length = 512)
  private String soil;

  @JsonTransient
  @Column(name = "grow_time_light", nullable = true, updatable = true, length = 512)
  private String growTimeLight;

  @JsonTransient
  @Column(name = "grow_time_air", nullable = true, updatable = true, length = 512)
  private String growTimeAir;

  @JsonTransient
  @Column(name = "grow_time_temperature", nullable = true, updatable = true, length = 512)
  private String growTimeTemperature;

  @JsonTransient
  @Column(name = "grow_time_humidity", nullable = true, updatable = true, length = 512)
  private String growTimeHumidity;

  @JsonTransient
  @Column(name = "grow_time_other", nullable = true, updatable = true, length = 512)
  private String growTimeOther;

  @JsonTransient
  @Column(name = "rest_time_light", nullable = true, updatable = true, length = 512)
  private String restTimeLight;

  @JsonTransient
  @Column(name = "rest_time_air", nullable = true, updatable = true, length = 512)
  private String restTimeAir;

  @JsonTransient
  @Column(name = "rest_time_temperature", nullable = true, updatable = true, length = 512)
  private String restTimeTemperature;

  @JsonTransient
  @Column(name = "rest_time_humidity", nullable = true, updatable = true, length = 512)
  private String restTimeHumidity;

  @JsonTransient
  @Column(name = "rest_time_other", nullable = true, updatable = true, length = 512)
  private String restTimeOther;
}
