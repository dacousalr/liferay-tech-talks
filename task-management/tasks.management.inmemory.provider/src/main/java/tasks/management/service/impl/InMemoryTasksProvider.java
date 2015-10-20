/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tasks.management.service.impl;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import tasks.management.api.model.Task;
import tasks.management.api.storage.TaskStorageProvider;
import tasks.management.model.impl.TasksModelImpl;

@Component
public class InMemoryTasksProvider implements TaskStorageProvider {

	@Override
	public List<Task> list() {
		return Arrays.<Task>asList(
			new TasksModelImpl(1L, "Review pull requests", "Review pulls requests coming"),
			new TasksModelImpl(2L, "Migrate to OSGi R6", "Upgrade the latest jar files to the latest R6 specs"));
	}

	@Override
	public String getIdentifier() {
		return "tasks.storage.jdbc.provider";
	}

}