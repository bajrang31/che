/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api.workspace.server.model.impl.devfile;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.eclipse.che.api.core.model.workspace.devfile.Component;
import org.eclipse.che.api.core.model.workspace.devfile.Endpoint;
import org.eclipse.che.api.core.model.workspace.devfile.Entrypoint;
import org.eclipse.che.api.core.model.workspace.devfile.Env;
import org.eclipse.che.api.core.model.workspace.devfile.Volume;

/** @author Sergii Leshchenko */
@Entity(name = "DevfileComponent")
@Table(name = "devfile_component")
public class ComponentImpl implements Component {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "component_id")
  private String component_id;

  @Column(name = "reference")
  private String reference;

  @Column(name = "reference_content")
  private String referenceContent;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "devfile_entrypoints_id")
  private List<EntrypointImpl> entrypoints;

  @Column(name = "image")
  private String image;

  @Column(name = "memory_limit")
  private String memoryLimit;

  @Column(name = "mount_sources")
  private boolean mountSources;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "component_command",
      joinColumns = @JoinColumn(name = "component_id"))
  @Column(name = "commands")
  private List<String> command;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "component_args",
      joinColumns = @JoinColumn(name = "component_id"))
  @Column(name = "args")
  private List<String> args;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "devfile_volumes_id")
  private List<VolumeImpl> volumes;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "devfile_env_id")
  private List<EnvImpl> env;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "devfile_endpoints_id")
  private List<EndpointImpl> endpoints;

  public ComponentImpl() {}

  public ComponentImpl(
      String name,
      String type,
      String component_id,
      String reference,
      String referenceContent,
      List<? extends Entrypoint> entrypoints,
      String image,
      String memoryLimit,
      boolean mountSources,
      List<String> command,
      List<String> args,
      List<? extends Volume> volumes,
      List<? extends Env> env,
      List<? extends Endpoint> endpoints) {
    this.name = name;
    this.type = type;
    this.component_id = component_id;
    this.reference = reference;
    this.referenceContent = referenceContent;
    if (entrypoints != null) {
      this.entrypoints =
          entrypoints.stream().map(EntrypointImpl::new).collect(toCollection(ArrayList::new));
    }
    this.image = image;
    this.memoryLimit = memoryLimit;
    this.mountSources = mountSources;
    this.command = command;
    this.args = args;
    if (volumes != null) {
      this.volumes = volumes.stream().map(VolumeImpl::new).collect(toCollection(ArrayList::new));
    }
    if (env != null) {
      this.env = env.stream().map(EnvImpl::new).collect(toCollection(ArrayList::new));
    }
    if (endpoints != null) {
      this.endpoints =
          endpoints.stream().map(EndpointImpl::new).collect(toCollection(ArrayList::new));
    }
  }

  public ComponentImpl(Component component) {
    this(
        component.getName(),
        component.getType(),
        component.getId(),
        component.getReference(),
        component.getReferenceContent(),
        component.getEntrypoints(),
        component.getImage(),
        component.getMemoryLimit(),
        component.getMountSources(),
        component.getCommand(),
        component.getArgs(),
        component.getVolumes(),
        component.getEnv(),
        component.getEndpoints());
  }

  public ComponentImpl(String name, String type, String component_id) {}

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String getId() {
    return component_id;
  }

  public void setId(String id) {
    this.component_id = id;
  }

  @Override
  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  @Override
  public String getReferenceContent() {
    return referenceContent;
  }

  public void setReferenceContent(String referenceContent) {
    this.referenceContent = referenceContent;
  }

  @Override
  public List<EntrypointImpl> getEntrypoints() {
    if (entrypoints == null) {
      entrypoints = new ArrayList<>();
    }
    return entrypoints;
  }

  public void setEntrypoints(List<EntrypointImpl> entrypoints) {
    this.entrypoints = entrypoints;
  }

  @Override
  public Map<String, String> getSelector() {
    return null;
  }

  @Override
  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override
  public String getMemoryLimit() {
    return memoryLimit;
  }

  public void setMemoryLimit(String memoryLimit) {
    this.memoryLimit = memoryLimit;
  }

  @Override
  public boolean getMountSources() {
    return mountSources;
  }

  public void setMountSources(boolean mountSources) {
    this.mountSources = mountSources;
  }

  @Override
  public List<String> getCommand() {
    if (command == null) {
      command = new ArrayList<>();
    }
    return command;
  }

  public void setCommand(List<String> command) {
    this.command = command;
  }

  @Override
  public List<String> getArgs() {
    if (args == null) {
      args = new ArrayList<>();
    }
    return args;
  }

  public void setArgs(List<String> args) {
    this.args = args;
  }

  @Override
  public List<VolumeImpl> getVolumes() {
    if (volumes == null) {
      volumes = new ArrayList<>();
    }
    return volumes;
  }

  public void setVolumes(List<VolumeImpl> volumes) {
    this.volumes = volumes;
  }

  @Override
  public List<EnvImpl> getEnv() {
    if (env == null) {
      env = new ArrayList<>();
    }
    return env;
  }

  public void setEnv(List<EnvImpl> env) {
    this.env = env;
  }

  @Override
  public List<EndpointImpl> getEndpoints() {
    if (endpoints == null) {
      endpoints = new ArrayList<>();
    }
    return endpoints;
  }

  public void setEndpoints(List<EndpointImpl> endpoints) {
    this.endpoints = endpoints;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ComponentImpl)) {
      return false;
    }
    ComponentImpl component = (ComponentImpl) o;
    return getMountSources() == component.getMountSources()
        && Objects.equals(id, component.id)
        && Objects.equals(name, component.name)
        && Objects.equals(type, component.type)
        && Objects.equals(getId(), component.getId())
        && Objects.equals(getReference(), component.getReference())
        && Objects.equals(getReferenceContent(), component.getReferenceContent())
        && Objects.equals(getEntrypoints(), component.getEntrypoints())
        && Objects.equals(getImage(), component.getImage())
        && Objects.equals(getMemoryLimit(), component.getMemoryLimit())
        && Objects.equals(getCommand(), component.getCommand())
        && Objects.equals(getArgs(), component.getArgs())
        && Objects.equals(getVolumes(), component.getVolumes())
        && Objects.equals(getEnv(), component.getEnv())
        && Objects.equals(getEndpoints(), component.getEndpoints());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getName(),
        getType(),
        getId(),
        getReference(),
        getReferenceContent(),
        getEntrypoints(),
        getImage(),
        getMemoryLimit(),
        getMountSources(),
        getCommand(),
        getArgs(),
        getVolumes(),
        getEnv(),
        getEndpoints());
  }

  @Override
  public String toString() {
    return "ComponentImpl{"
        + "name='"
        + name
        + '\''
        + ", type='"
        + type
        + '\''
        + ", id='"
        + component_id
        + '\''
        + ", reference='"
        + reference
        + '\''
        + ", referenceContent='"
        + referenceContent
        + '\''
        + ", entrypoints="
        + entrypoints
        + ", image='"
        + image
        + '\''
        + ", memoryLimit='"
        + memoryLimit
        + '\''
        + ", mountSources="
        + mountSources
        + ", command="
        + command
        + ", args="
        + args
        + ", volumes="
        + volumes
        + ", env="
        + env
        + ", endpoints="
        + endpoints
        + '}';
  }
}