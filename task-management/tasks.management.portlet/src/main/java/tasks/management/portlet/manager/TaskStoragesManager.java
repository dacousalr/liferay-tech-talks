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
package tasks.management.portlet.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import tasks.management.api.model.Task;
import tasks.management.api.storage.TaskStorageProvider;

@Component(immediate = true, service = TaskStoragesManager.class)
public class TaskStoragesManager {

	public List<Task> getTasks() {

		List<Task> tasks = new ArrayList<>();

		for (TaskStorageProvider taskStorageProvider : _taskStoragesProviders) {
			try {
				tasks.addAll(taskStorageProvider.list());
			}
			catch (Throwable t) {
				continue;
			}
		}

		return tasks;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeTaskStorageProvider"
	)
	protected void addTaskStorageProvider(TaskStorageProvider taskStorageProvider) {
		_taskStoragesProviders.add(taskStorageProvider);
	}

	protected void removeTaskStorageProvider(TaskStorageProvider taskStorageProvider) {
		_taskStoragesProviders.remove(taskStorageProvider);
	}
	
	private List<TaskStorageProvider> _taskStoragesProviders = new CopyOnWriteArrayList<>();

}