package tasks.management.evernote.provider.config;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = "tasks.management.evernote.provider.config.EvernoteConfiguration")
public interface EvernoteConfiguration {

	@Meta.AD(required = true)
	String token ();
}
