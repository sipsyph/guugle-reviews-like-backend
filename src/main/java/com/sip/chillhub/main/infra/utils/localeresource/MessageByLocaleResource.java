package com.sip.chillhub.main.infra.utils.localeresource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;


public class MessageByLocaleResource {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageByLocaleResource.class);

	private MessageSource messageSource;
	
	public MessageByLocaleResource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	private String getMessageUsingDefaultLocale(String key, String... args) {
		
		try {
			String msg = messageSource.getMessage(key, args, Locale.getDefault());
			
			return msg;
		} catch(NoSuchMessageException e) {
			LOG.error("NoSuchMessageException encountered", e);
			return null;
		}
	}
	
	public String getMessage(String key) {
		
		key = key.replace("{", "");
		key = key.replace("}", "");
		
		Locale locale = LocaleContextHolder.getLocale();
		String message = null; 
		
		try {
			message = messageSource.getMessage(key, null, locale);
			return message;
		} catch(NoSuchMessageException e) {
			return getMessageUsingDefaultLocale(key, (String[]) null);
		}
	}
	
	public String getMessageWithKeyArgs(String key, String...args) {
		
		key = key.replace("{", "");
		key = key.replace("}", "");
		
		Locale locale = LocaleContextHolder.getLocale();
		String message = null; 
		
		
		List<String> argsList = new ArrayList<String>(
				Arrays.stream(args)
					.map(s -> s.replace("}", ""))
					.map(s -> s.replace("{", ""))
					.collect(Collectors.toList()));

		List<String> resolvedMessages = new ArrayList<String>();
		
		for(int i = 0; i < argsList.size(); i++) {
			String msg = "";
			try {
				msg = messageSource.getMessage(argsList.get(i), null, locale);
				resolvedMessages.add(msg);
			} catch(NoSuchMessageException e) {
				return getMessageUsingDefaultLocale(argsList.get(i), (String[]) null);
			}
		}

		String[] resolvedArr = new String[resolvedMessages.size()];		
		resolvedArr = resolvedMessages.toArray(resolvedArr);
		
		try {
			message = messageSource.getMessage(key, resolvedArr, locale);
			return message;
		} catch(NoSuchMessageException e) {
			return getMessageUsingDefaultLocale(key, resolvedArr);
		}
		
	}
	
	public String getMessageWithValueArgs(String key, String...args) {
		
		key = key.replace("{", "");
		key = key.replace("}", "");
		
		Locale locale = LocaleContextHolder.getLocale();
		String message = null; 
		
		try {
			message = messageSource.getMessage(key, args, locale);
			return message;
		} catch(NoSuchMessageException e) {
			return getMessageUsingDefaultLocale(key, args);
		}
		
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
