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
package tasks.management.evernote.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.TException;

import aQute.bnd.annotation.metatype.Configurable;
import tasks.management.api.model.Task;
import tasks.management.api.storage.TaskStorageProvider;
import tasks.management.evernote.provider.config.EvernoteConfiguration;
import tasks.management.evernote.provider.model.impl.TasksModelImpl;

@Component(
	configurationPid = "tasks.management.evernote.provider.config.EvernoteConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = TaskStorageProvider.class,
	immediate = true
)
public class EvernoteTasksProvider implements TaskStorageProvider {

	@Override
	public String getIdentifier() {
		return "tasks.storage.evernote.provider";
	}

	@Override
	public List<Task> list() {
		List<Notebook> notebooks = new ArrayList<>();
		List<Task> tasks = new ArrayList<>();
		List<Note> notes = new ArrayList<>();

		try {
			notebooks = _noteStore.listNotebooks();

			for (Notebook notebook : notebooks) {
				NoteFilter noteFilter = new NoteFilter();

				noteFilter.setNotebookGuid(notebook.getGuid());

				NoteList noteList = _noteStore.findNotes(noteFilter, 0, 100);

				List<Note> notes2 = noteList.getNotes();

				if (notes2 != null) {
					notes.addAll(notes2);
				}
			}
		}
		catch (EDAMUserException | EDAMSystemException | TException | EDAMNotFoundException e) {
			// Log the error
			return tasks;
		}

		for (Note note : notes) {
			tasks.add(
				new TasksModelImpl(
					note.getCreated(),
					note.getTitle(),
					note.getContent()));
		}

		return tasks;
		
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties)
		throws EDAMUserException, EDAMSystemException, TException {

		_evernoteConfiguration = Configurable.createConfigurable(
				EvernoteConfiguration.class, properties);

		EvernoteAuth evernoteAuth = new EvernoteAuth(
			EvernoteService.PRODUCTION, _evernoteConfiguration.token());

		ClientFactory factory = new ClientFactory(evernoteAuth);

		_userStore = factory.createUserStoreClient();

		_noteStore = factory.createNoteStoreClient();
		
	}

	private EvernoteConfiguration _evernoteConfiguration;
	private UserStoreClient _userStore;
	private NoteStoreClient _noteStore;

}
