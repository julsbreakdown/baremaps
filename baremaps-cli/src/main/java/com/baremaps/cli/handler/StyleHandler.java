/*
 * Copyright (C) 2020 The Baremaps Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baremaps.cli.handler;

import com.baremaps.cli.blueprint.BlueprintBuilder;
import com.baremaps.tiles.config.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.AbstractHttpService;
import com.linecorp.armeria.server.ServiceRequestContext;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StyleHandler extends AbstractHttpService {

  private static Logger logger = LogManager.getLogger();

  private final Config config;

  public StyleHandler(Config config) {
    this.config = config;
  }

  @Override
  protected HttpResponse doGet(ServiceRequestContext ctx, HttpRequest req) throws JsonProcessingException {
    BlueprintBuilder builder = new BlueprintBuilder(config);
    Map<String, Object> style = builder.build();
    ObjectMapper mapper = new ObjectMapper();
    String output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(style);
    return HttpResponse.of(output);
  }

}
