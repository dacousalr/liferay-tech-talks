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
package tasks.management.model.impl;


import tasks.management.api.model.Task;

public class TasksModelImpl implements Task {
	
	public TasksModelImpl(long id, String title, String description) {
		_id = id;
		_title = title;
		_description = description;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public String getTitle() {
		return _title;
	}
	@Override
	public String getDescription() {
		return _description;
	}

	private long _id;
	private String _title;
	private String _description;

}