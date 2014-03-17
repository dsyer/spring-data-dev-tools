/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.release.cli;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.release.maven.MavenOperations;
import org.springframework.data.release.maven.Pom;
import org.springframework.data.release.model.Module;
import org.springframework.data.release.model.Project;
import org.springframework.data.release.model.ReleaseTrains;
import org.springframework.data.release.model.Train;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

/**
 * @author Oliver Gierke
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReleaseCommands implements CommandMarker {

	private final MavenOperations mavenOperations;

	@CliCommand("release predict")
	public String predictTrainAndIteration() throws IOException {

		Project commons = ReleaseTrains.COMMONS;
		Pom pom = mavenOperations.getMavenProject(commons);

		for (Train train : ReleaseTrains.TRAINS) {

			Module module = train.getModule(commons);

			if (!pom.getVersion().startsWith(module.getVersion().toMajorMinorBugfix())) {
				continue;
			}

			return train.getName();
		}

		return null;
	}
}