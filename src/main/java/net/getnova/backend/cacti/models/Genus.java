package net.getnova.backend.cacti.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.getnova.backend.sql.model.TableModelAutoId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cacti_genus")
public class Genus extends TableModelAutoId {

  @Column(name = "name", nullable = false, updatable = true, length = 128)
  private String name;
}
